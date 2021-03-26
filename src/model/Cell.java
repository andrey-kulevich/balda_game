package model;

/** Part of game field, that holds letter, selection state and selection order */
public class Cell {

    /** Possible states of cell */
    public enum SelectionState{ NOT_SELECTED, SELECTED_TO_WRITE_LETTER, SELECTED_TO_CREATE_WORD }

    /** state of cell */
    private SelectionState _selectionState = SelectionState.NOT_SELECTED;
    /** letter inside cell */
    private Character _letter = ' ';
    /** selection order */
    private int _selectionIndex = -1;

    /** Put a letter in the cell
     *
     * @param letter Unicode letter
     */
    public boolean setLetter(Character letter) {
        if (letter.toString().matches("[а-яА-Я]")) {
            _letter = letter;
            return true;
        }
        return false;
    }

    /** Get letter inside cell
     *
     * @return letter
     */
    public Character letter() { return _letter; }

    /** Check content of the cell
     *
     * @return is letter in the cell
     */
    public boolean hasLetter() { return Character.isLetter(_letter); }

    /** Get selection state of the cell
     *
     * @return state
     */
    public SelectionState selectionState() { return _selectionState; }

    /** Set selection state of the cell
     *
     * @param state state
     */
    public void setSelectionState(SelectionState state) { _selectionState = state; }

    /** Get selection order of the cell
     *
     * @return index
     */
    public int selectionIndex() { return _selectionIndex; }

    /** Set selection order of the cell
     *
     * @param index index
     */
    public void setSelectionIndex(int index) {
        if (!this.hasLetter()) throw new RuntimeException("Cannot set index when cell is empty");
        if (index < 0) throw new IllegalArgumentException("Index cannot be less than 0");
        _selectionIndex = index;
    }

    /** Clear selection and remove letter if it is a new letter */
    public void clearSelection() {
        if (_selectionState == SelectionState.SELECTED_TO_WRITE_LETTER) _letter = ' ';
        _selectionIndex = -1;
        _selectionState = SelectionState.NOT_SELECTED;
    }
}
