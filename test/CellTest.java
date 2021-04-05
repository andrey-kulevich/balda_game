import model.Cell;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CellTest {

    Cell _cell;

    void cleanUp() { _cell = new Cell(); }

    @Test
    void selectCellToWriteLetter() {
        cleanUp();
        _cell.setSelectionState(Cell.SelectionState.SELECTED_TO_WRITE_LETTER);
        Assertions.assertEquals(Cell.SelectionState.SELECTED_TO_WRITE_LETTER, _cell.selectionState());
    }

    @Test
    void selectCellToCreateWord() {
        cleanUp();
        _cell.setSelectionState(Cell.SelectionState.SELECTED_TO_CREATE_WORD);
        Assertions.assertEquals(Cell.SelectionState.SELECTED_TO_CREATE_WORD, _cell.selectionState());
    }

    @Test
    void clearSelectionCellWithoutLetter() {
        cleanUp();
        _cell.clearSelection();
        Assertions.assertEquals(Cell.SelectionState.NOT_SELECTED, _cell.selectionState());
        Assertions.assertEquals(-1, _cell.selectionIndex());
    }

    @Test
    void clearSelectionCellWithOldLetter() {
        cleanUp();
        _cell.setSelectionState(Cell.SelectionState.SELECTED_TO_WRITE_LETTER);
        _cell.setLetter('б');
        _cell.setSelectionState(Cell.SelectionState.NOT_SELECTED);
        _cell.clearSelection();
        Assertions.assertEquals(Cell.SelectionState.NOT_SELECTED, _cell.selectionState());
        Assertions.assertEquals(-1, _cell.selectionIndex());
        Assertions.assertEquals('Б', _cell.letter());
    }

    @Test
    void clearSelectionCellWithNewLetter() {
        cleanUp();
        _cell.setSelectionState(Cell.SelectionState.SELECTED_TO_WRITE_LETTER);
        _cell.setLetter('б');
        _cell.clearSelection();
        Assertions.assertEquals(Cell.SelectionState.NOT_SELECTED, _cell.selectionState());
        Assertions.assertEquals(-1, _cell.selectionIndex());
        Assertions.assertEquals(' ', _cell.letter());
    }

    @Test
    void writeToCellWhichIsNotSelected() {
        cleanUp();
        Assertions.assertFalse(_cell.setLetter('б'));
    }

    @Test
    void writeToCellWhichAlreadyHasALetter() {
        cleanUp();
        _cell.setSelectionState(Cell.SelectionState.SELECTED_TO_WRITE_LETTER);
        _cell.setLetter('б');
        Assertions.assertFalse(_cell.setLetter('д'));
    }

    @Test
    void writeNonCyrillicLetter() {
        cleanUp();
        _cell.setSelectionState(Cell.SelectionState.SELECTED_TO_WRITE_LETTER);
        Assertions.assertFalse(_cell.setLetter('z'));
    }

    @Test
    void setSelectionIndex() {
        cleanUp();
        _cell.setSelectionState(Cell.SelectionState.SELECTED_TO_WRITE_LETTER);
        _cell.setLetter('б');
        _cell.setSelectionIndex(0);
        Assertions.assertEquals(0, _cell.selectionIndex());
    }

    @Test
    void setSelectionIndexToEmptyCell() {
        cleanUp();
        Assertions.assertThrows(RuntimeException.class, () -> _cell.setSelectionIndex(0));
    }

    @Test
    void setInvalidSelectionIndex() {
        cleanUp();
        _cell.setSelectionState(Cell.SelectionState.SELECTED_TO_WRITE_LETTER);
        _cell.setLetter('б');
        Assertions.assertThrows(IllegalArgumentException.class, () -> _cell.setSelectionIndex(-1));
    }

    @Test
    void getCopyOfCell() {
        cleanUp();
        _cell.setSelectionState(Cell.SelectionState.SELECTED_TO_WRITE_LETTER);
        _cell.setLetter('б');
        _cell.setSelectionIndex(1);
        Cell copy = new Cell(_cell);
        Assertions.assertEquals(Cell.SelectionState.SELECTED_TO_WRITE_LETTER, _cell.selectionState());
        Assertions.assertEquals(1, _cell.selectionIndex());
        Assertions.assertEquals('Б', _cell.letter());
    }
}
