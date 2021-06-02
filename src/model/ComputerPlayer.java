package model;

import java.util.*;

/** Player with different intellect levels, that makes moves automatically */
public class ComputerPlayer extends Player {

    /** Intellect level */
    public enum Intellect { EASY, MEDIUM, HARD }

    /** interpretation of intellect level as max length of words to search */
    protected final Intellect _intellect;

    /** Create computer player with selected intellect level
     *
     * @param name name of player
     * @param intellect intellect level
     */
    public ComputerPlayer(String name, Intellect intellect) {
        super(name);
        _intellect = intellect;
    }

    @Override
    public void setActive(boolean isActive) {
        _isActive = isActive;
        if (isActive) doMove();
    }

    /** Do automatic move */
    protected void doMove() {
        ArrayList<ArrayList<CellObj>> sequences = new ArrayList<>();

        this.searchSequences(sequences, _intellect.ordinal() + 4);
        filterSequences(sequences);

        ArrayList<ArrayList<CellObj>> resultSequences = new ArrayList<>();
        this.findWordsInDictionary(sequences, resultSequences);

        if (!resultSequences.isEmpty()) {
            this.selectSequenceOnField(getLongestSequence(resultSequences));
            confirmMove();
        } else {
            skipMove();
        }
    }

    /** Search sequences on the field with specified depth
     *
     * @param sequences list of all possible sequences
     */
    protected void searchSequences(ArrayList<ArrayList<CellObj>> sequences, int maxLength) {
        GameField field = _game.field();

        if (maxLength > field.size()*field.size())
            throw new IllegalArgumentException("Max length cannot be longer than amount of cells on field");

        for (int i = 0; i < field.size(); i++) {
            for (int j = 0; j < field.size(); j++) {
                ArrayList<ArrayList<CellObj>> currSequences = new ArrayList<>();
                ArrayList<CellObj> firstCell = new ArrayList<>();
                firstCell.add(new CellObj(field.getCell(i, j).letter(), i, j));
                currSequences.add(firstCell);
                for (int k = 0; k < maxLength - 1; k++) {
                    ArrayList<ArrayList<CellObj>> tempSequences = new ArrayList<>();
                    for (ArrayList<CellObj> currSequence : currSequences) {
                        CellObj cellObj = currSequence.get(currSequence.size() - 1);

                        // get all neighbours for this cell and append new sequences with that cells
                        if (cellObj.row + 1 < field.size() - 1) {
                            ArrayList<CellObj> withBottomNeighbour = new ArrayList<>(currSequence);
                            withBottomNeighbour.add(new CellObj(field.getCell(
                                    cellObj.row + 1, cellObj.col).letter(), cellObj.row + 1, cellObj.col));
                            tempSequences.add(withBottomNeighbour);
                        }
                        if (cellObj.row - 1 >= 0) {
                            ArrayList<CellObj> withTopNeighbour = new ArrayList<>(currSequence);
                            withTopNeighbour.add(new CellObj(field.getCell(
                                    cellObj.row - 1, cellObj.col).letter(), cellObj.row - 1, cellObj.col));
                            tempSequences.add(withTopNeighbour);
                        }
                        if (cellObj.col + 1 < field.size() - 1) {
                            ArrayList<CellObj> withRightNeighbour = new ArrayList<>(currSequence);
                            withRightNeighbour.add(new CellObj(field.getCell(
                                    cellObj.row, cellObj.col + 1).letter(), cellObj.row, cellObj.col + 1));
                            tempSequences.add(withRightNeighbour);
                        }
                        if (cellObj.col - 1 >= 0) {
                            ArrayList<CellObj> withLeftNeighbour = new ArrayList<>(currSequence);
                            withLeftNeighbour.add(new CellObj(field.getCell(
                                    cellObj.row, cellObj.col - 1).letter(), cellObj.row, cellObj.col - 1));
                            tempSequences.add(withLeftNeighbour);
                        }
                    }
                    currSequences.addAll(tempSequences);
                }
                sequences.addAll(currSequences);
            }
        }
    }

    /** Clear sequences with repetitions and more than two or zero empty cells
     *
     * @param sequences sequences to filter
     */
    protected void filterSequences(ArrayList<ArrayList<CellObj>> sequences) {
        for (Iterator<ArrayList<CellObj>> iterator = sequences.iterator(); iterator.hasNext(); ) {
            int emptyCellsCount = 0;
            boolean isRepetition = false;
            ArrayList<CellObj> currSequence = iterator.next();
            ArrayList<CellObj> withoutDuplicates = new ArrayList<>();
            for (CellObj cell : currSequence) {
                if (cell.letter == ' ') emptyCellsCount++;
                if (!withoutDuplicates.contains(cell)) withoutDuplicates.add(cell);
            }
            if (currSequence.size() != withoutDuplicates.size()) isRepetition = true;
            if (currSequence.size() < 2 || emptyCellsCount > 1 || emptyCellsCount == 0 || isRepetition) iterator.remove();
        }
    }

    /** Find the word from each sequence and for each letter in alphabet
     *
     * @param inSequences original sequences
     * @param outSequences sequences with added letter and found word
     */
    protected void findWordsInDictionary(ArrayList<ArrayList<CellObj>> inSequences, ArrayList<ArrayList<CellObj>> outSequences) {
        ArrayList<Character> alphabet = _game.dictionary().getAlphabet();

        for (ArrayList<CellObj> sequence : inSequences) {
            ArrayList<CellObj> resultSequence = new ArrayList<>(sequence);
            Optional<CellObj> targetCell = resultSequence.stream().filter(e -> e.letter == ' ').findFirst();
            if (targetCell.isPresent()) {
                int spaceIndex = resultSequence.indexOf(targetCell.get());
                for (Character letter : alphabet) {
                    resultSequence.get(spaceIndex).letter = letter;
                    StringBuilder wordBuilder = new StringBuilder();
                    for (CellObj cellObj : resultSequence) wordBuilder.append(Character.toLowerCase(cellObj.letter));
                    String word = wordBuilder.toString();
                    if (_game.dictionary().hasWord(word) &&
                            !_game.field().getStartWord().equals(word) &&
                            !_game.firstPlayer().getWords().contains(word) &&
                            !getWords().contains(word)) {
                        outSequences.add(resultSequence);
                        break;
                    }
                }
            }
        }
    }

    /** Get longest sequence from list
     *
     * @param sequences list of sequences
     * @return longest sequence
     */
    protected ArrayList<CellObj> getLongestSequence(ArrayList<ArrayList<CellObj>> sequences) {
        if (sequences.isEmpty()) throw new IllegalArgumentException("No sequences in list");
        return sequences.stream().max(Comparator.comparing(List::size)).get();
    }

    /** Get random sequence from list
     *
     * @param sequences list of sequences
     * @return random sequence
     */
    protected ArrayList<CellObj> getRandomSequence(ArrayList<ArrayList<CellObj>> sequences) {
        return sequences.get(new Random().nextInt(sequences.size()));
    }

    /** Write letter to specified cell and select word
     *
     * @param sequence word to select
     */
    protected void selectSequenceOnField(ArrayList<CellObj> sequence) {
        for (CellObj cellObj : sequence) {
            if (!_game.field().getCell(cellObj.row, cellObj.col).hasLetter()) {
                if (!selectCell(cellObj.row, cellObj.col))
                    throw new RuntimeException("Unable to select sequence on field");
                writeToSelectedCell(cellObj.letter);
                break;
            }
        }
        for (CellObj cellObj : sequence) {
            if (!selectCell(cellObj.row, cellObj.col))
                throw new RuntimeException("Unable to select sequence on field");
        }
    }

    /** Supporting struct that represents the model cell with exact position */
    public static class CellObj {
        public Character letter;
        public final int row;
        public final int col;

        public CellObj(Character letter, int row, int col) {
            this.letter = letter;
            this.row = row;
            this.col = col;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            CellObj cellObj = (CellObj) o;
            return row == cellObj.row && col == cellObj.col && Objects.equals(letter, cellObj.letter);
        }
    }
}