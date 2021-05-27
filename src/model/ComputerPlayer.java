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

        // supporting class that represents the model cell with exact position
        class CellObj {
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

        GameField field = _game.field();
        ArrayList<ArrayList<CellObj>> sequences = new ArrayList<>();

        // search sequences
        for (int i = 0; i < field.size(); i++) {
            for (int j = 0; j < field.size(); j++) {
                ArrayList<ArrayList<CellObj>> currSequences = new ArrayList<>();
                for (int k = 0; k < _sequencesDepth; k++) {
                    ArrayList<ArrayList<CellObj>> tempSequences = new ArrayList<>();
                    for (ArrayList<CellObj> currSequence : currSequences) {
                        CellObj cellObj = currSequence.get(currSequence.size() - 1);

                        // get all neighbours for this cell and append new sequences with that cells
                        ArrayList<CellObj> withBottomNeighbour = new ArrayList<>(currSequence);
                        ArrayList<CellObj> withTopNeighbour = new ArrayList<>(currSequence);
                        ArrayList<CellObj> withRightNeighbour = new ArrayList<>(currSequence);
                        ArrayList<CellObj> withLeftNeighbour = new ArrayList<>(currSequence);

                        if (cellObj.row + 1 < field.size() - 1) {
                            withBottomNeighbour.add(new CellObj(field.getCell(
                                    cellObj.row + 1, cellObj.col).letter(), cellObj.row + 1, cellObj.col));
                            tempSequences.add(withBottomNeighbour);
                        }
                        if (cellObj.row - 1 >= 0) {
                            withTopNeighbour.add(new CellObj(field.getCell(
                                    cellObj.row - 1, cellObj.col).letter(), cellObj.row - 1, cellObj.col));
                            tempSequences.add(withTopNeighbour);
                        }
                        if (cellObj.col + 1 < field.size() - 1) {
                            withRightNeighbour.add(new CellObj(field.getCell(
                                    cellObj.row, cellObj.col + 1).letter(), cellObj.row, cellObj.col + 1));
                            tempSequences.add(withRightNeighbour);
                        }
                        if (cellObj.col - 1 >= 0) {
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

        // clear sequences with repetitions and more than two or zero empty cells
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

        // get the word from each sequence and for each letter in alphabet
        ArrayList<ArrayList<CellObj>> resultSequences = new ArrayList<>();
        ArrayList<Character> alphabet = _game.dictionary().getAlphabet();

        for (ArrayList<CellObj> sequence : sequences) {
            ArrayList<CellObj> resultSequence = new ArrayList<>(sequence);
            Optional<CellObj> targetCell = resultSequence.stream().filter(e -> e.letter == ' ').findFirst();
            if (targetCell.isPresent()) {
                int spaceIndex = resultSequence.indexOf(targetCell.get());
                for (Character letter : alphabet) {
                    resultSequence.get(spaceIndex).letter = letter;
                    StringBuilder word = new StringBuilder();
                    for (CellObj cellObj : resultSequence) word.append(cellObj.letter);
                    if (_game.dictionary().hasWord(word.toString())) {
                        resultSequences.add(resultSequence);
                        break;
                    } else if (_game.dictionary().hasWord(word.reverse().toString())) {
                        Collections.reverse(resultSequence);
                        resultSequences.add(resultSequence);
                        break;
                    }
                }
            }
        }

        // find largest sequence and do move
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
}