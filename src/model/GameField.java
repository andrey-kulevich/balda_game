package model;

/** Matrix of cells with specified methods to control them */
public class GameField {

    /** matrix of cells */
    private final Cell[][] _cells;
    /** last selected cell */
    private int _currentSelectionIndex = -1;

    /** Constructor
     *
     * @param startWord start word on the field
     */
    public GameField(String startWord) {
        if (!startWord.matches("[а-яА-Я]{5,}"))
            throw new IllegalArgumentException("Start word must have longer than 5 " +
                    "letters and contain only cyrillic letters");

        _cells = new Cell[startWord.length()][startWord.length()];

        for (int i = 0; i < startWord.length(); i++)
            _cells[startWord.length() / 2][i].setLetter(startWord.charAt(i));
    }

    /** Select certain cell
     *
     * @param row row index
     * @param col column index
     * @return success of selection
     */
    public boolean selectCell(int row, int col) {
        if (_currentSelectionIndex == -1 && this.canSelectToWrite(row, col)) {
            _cells[row][col].setSelectionState(Cell.SelectionState.SELECTED_TO_WRITE_LETTER);
            return true;
        } else if ((_currentSelectionIndex == -1 && this.getCellSelectedToWrite() != null) ||
                    (_currentSelectionIndex != -1 && this.canSelectToCreateWord(row, col))) {
            _currentSelectionIndex++;
            _cells[row][col].setSelectionState(Cell.SelectionState.SELECTED_TO_CREATE_WORD);
            _cells[row][col].setSelectionIndex(_currentSelectionIndex);
            return true;
        }
        return false;
    }

    /** Write letter to specified cell
     *
     * @param letter Unicode letter
     * @return success
     */
    public boolean writeToSelectedCell(Character letter) {
        Cell cell = this.getCellSelectedToWrite();
        if (cell == null) return false;
        return cell.setLetter(letter);
    }

    /** Get cell selected to write letter
     *
     * @return cell (null if cell does not found)
     */
    private Cell getCellSelectedToWrite() {
        for (Cell[] row : _cells) {
            for (Cell cell : row) {
                if (cell.selectionState() == Cell.SelectionState.SELECTED_TO_WRITE_LETTER)
                    return cell;
            }
        }
        return null;
    }

    /** Check ability to select specified cell for writing letter
     *
     * @param row row index
     * @param col column index
     * @return success
     */
    private boolean canSelectToWrite(int row, int col) {
        return !_cells[row][col].hasLetter() &&
                ((row + 1 < _cells.length - 1 && _cells[row + 1][col].hasLetter()) ||
                (row - 1 >= 0 && _cells[row - 1][col].hasLetter()) ||
                (col + 1 < _cells.length - 1 && _cells[row][col + 1].hasLetter()) ||
                (col - 1 >= 0 && _cells[row][col - 1].hasLetter()));
    }

    /** Check ability to select specified cell for creating word
     *
     * @param row row index
     * @param col column index
     * @return success
     */
    private boolean canSelectToCreateWord(int row, int col) {
        return _cells[row][col].hasLetter() &&
                ((row + 1 < _cells.length - 1 && _cells[row + 1][col].selectionIndex() == _currentSelectionIndex) ||
                (row - 1 >= 0 && _cells[row - 1][col].selectionIndex() == _currentSelectionIndex) ||
                (col + 1 < _cells.length - 1 && _cells[row][col + 1].selectionIndex() == _currentSelectionIndex) ||
                (col - 1 >= 0 && _cells[row][col - 1].selectionIndex() == _currentSelectionIndex));
    }

    /** Get selected word from field
     *
     * @return word (empty string, if word does not selected)
     */
    public String getSelectedWord() {
        if (this.getCellSelectedToWrite() == null ||
                this.getCellSelectedToWrite().selectionIndex() == -1) return null;
        int letterIndex = 0;
        StringBuilder word = new StringBuilder();
        Character ch = this.getLetterBySelectionIndex(letterIndex);
        while (ch != ' ') {
            word.append(ch);
            letterIndex++;
            ch = this.getLetterBySelectionIndex(letterIndex);
        }
        return word.toString();
    }

    /** Get letter from field by selection index
     *
     * @param index selection index
     * @return letter (whitespace, if cell with such index does not found)
     */
    private Character getLetterBySelectionIndex(int index) {
        for (Cell[] row : _cells) {
            for (Cell cell : row) {
                if (cell.selectionIndex() == index) return cell.letter();
            }
        }
        return ' ';
    }

    /** Clear all selections and current wrote letter */
    public void clearSelections() {
        _currentSelectionIndex = -1;
        for (Cell[] row : _cells) {
            for (Cell cell : row) {
                cell.clearSelection();
            }
        }
    }
}
