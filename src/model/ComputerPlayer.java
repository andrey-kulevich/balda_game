package model;

import java.util.*;

/** Player with different intellect levels, that makes moves automatically */
public class ComputerPlayer extends Player {

    /** Intellect level */
    public enum Intellect { EASY, MEDIUM, HARD }

    /** interpretation of intellect level as max length of words to search */
    private final int _sequencesDepth;

    /** Create computer player with selected intellect level
     *
     * @param name name of player
     * @param intellect intellect level
     */
    public ComputerPlayer(String name, Intellect intellect) {
        super(name);
        _sequencesDepth = intellect.ordinal() + 3;
    }

    @Override
    public void setActive(boolean isActive) {
        _isActive = isActive;
        if (isActive) doMove();
    }

    /** Do automatic move */
    private void doMove() {

        GameField field = _game.field();
        ArrayList<ArrayList<CellObj>> sequences = new ArrayList<>();

        this.searchSequences(sequences);
        this.filterSequences(sequences);

        ArrayList<ArrayList<CellObj>> resultSequences = new ArrayList<>();
        this.findWordsInDictionary(sequences, resultSequences);

        // find the largest sequence and do move
        Optional<ArrayList<CellObj>> max =  resultSequences.stream().max(Comparator.comparing(List::size));
        if (max.isPresent()) {
            ArrayList<CellObj> result =  max.get();
            for (CellObj cellObj : result) {
                if (!field.getCell(cellObj.row, cellObj.col).hasLetter()) {
                    selectCell(cellObj.row, cellObj.col);
                    writeToSelectedCell(cellObj.letter);
                    break;
                }
            }
            for (CellObj cellObj : result) { selectCell(cellObj.row, cellObj.col); }
        } else {
            skipMove();
        }
    }

    /** Search sequences on the field with specified depth
     *
     * @param sequences list of all possible sequences
     */
    private void searchSequences(ArrayList<ArrayList<CellObj>> sequences) {
        GameField field = _game.field();
        for (int i = 0; i < field.size(); i++) {
            for (int j = 0; j < field.size(); j++) {
                ArrayList<ArrayList<CellObj>> currSequences = new ArrayList<>();
                for (int k = 0; k < _sequencesDepth; k++) {
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
    private void filterSequences(ArrayList<ArrayList<CellObj>> sequences) {
        for (Iterator<ArrayList<CellObj>> iterator = sequences.iterator(); iterator.hasNext(); ) {
            int emptyCellsCount = 0;
            boolean isRepetition = false;
            ArrayList<CellObj> currSequence = iterator.next();
            for (CellObj cell : currSequence) {
                if (cell.letter == ' ') emptyCellsCount++;
                for (CellObj other : currSequence) {
                    if (cell == other) {
                        isRepetition = true;
                        break;
                    }
                }
            }
            if (emptyCellsCount > 1 || emptyCellsCount == 0 || isRepetition) iterator.remove();
        }
    }

    /** Find the word from each sequence and for each letter in alphabet
     *
     * @param inSequences original sequences
     * @param outSequences sequences with added letter and found word
     */
    private void findWordsInDictionary(ArrayList<ArrayList<CellObj>> inSequences, ArrayList<ArrayList<CellObj>> outSequences) {
        ArrayList<Character> alphabet = _game.dictionary().getAlphabet();

        for (ArrayList<CellObj> sequence : inSequences) {
            ArrayList<CellObj> resultSequence = new ArrayList<>(sequence);
            Optional<CellObj> targetCell = resultSequence.stream().filter(e -> e.letter == ' ').findFirst();
            if (targetCell.isPresent()) {
                int spaceIndex = resultSequence.indexOf(targetCell.get());
                for (Character letter : alphabet) {
                    resultSequence.get(spaceIndex).letter = letter;
                    StringBuilder word = new StringBuilder();
                    for (CellObj cellObj : resultSequence) word.append(cellObj.letter);
                    if (_game.dictionary().hasWord(word.toString())) {
                        outSequences.add(resultSequence);
                        break;
                    } else if (_game.dictionary().hasWord(word.reverse().toString())) {
                        Collections.reverse(resultSequence);
                        outSequences.add(resultSequence);
                        break;
                    }
                }
            }
        }
    }

    /** Supporting class that represents the model cell with exact position */
    private static class CellObj {
        Character letter;
        final int row;
        final int col;

        CellObj(Character letter, int row, int col) {
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