import model.Cell;
import model.GameField;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GameFieldTest {

    @Test
    void startWordHasLessThan5Letters() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new GameField("ляля"));
    }

    @Test
    void startWordContainsNoneCyrillicSymbols() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new GameField("helббг"));
    }

    @Test
    void selectCellToWriteLetter() {
        GameField field = new GameField("приветь");
        boolean success = field.selectCell(2, 1);
        Assertions.assertTrue(success);
        Assertions.assertEquals(Cell.SelectionState.SELECTED_TO_WRITE_LETTER,
                field.getCell(2, 1).selectionState());
    }

    @Test
    void selectCellToCreateWord() {
        GameField field = new GameField("приветь");
        field.selectCell(2, 1);
        field.writeToSelectedCell('ы');
        boolean success = field.selectCell(3, 1);
        Assertions.assertTrue(success);
        Assertions.assertEquals(Cell.SelectionState.SELECTED_TO_CREATE_WORD,
                field.getCell(3, 1).selectionState());
    }

    @Test
    void cannotSelectToWriteCellHasNoNeighboursWithLetters() {
        GameField field = new GameField("приветь");
        boolean success = field.selectCell(1, 1);
        Assertions.assertFalse(success);
        Assertions.assertEquals(Cell.SelectionState.NOT_SELECTED,
                field.getCell(1, 1).selectionState());
    }

    @Test
    void cannotSelectToWriteCellIsNotEmpty() {
        GameField field = new GameField("приветь");
        boolean success = field.selectCell(3, 1);
        Assertions.assertFalse(success);
        Assertions.assertEquals(Cell.SelectionState.NOT_SELECTED,
                field.getCell(3, 1).selectionState());
    }

    @Test
    void cannotSelectToCreateWordNewLetterIsNotSet() {
        GameField field = new GameField("приветь");
        field.selectCell(2, 1);
        boolean success = field.selectCell(3, 1);
        Assertions.assertFalse(success);
        Assertions.assertEquals(Cell.SelectionState.NOT_SELECTED,
                field.getCell(3, 1).selectionState());
    }

    @Test
    void cannotSelectToCreateWordNoNeighboursWithLastSelectionIndex() {
        GameField field = new GameField("приветь");
        field.selectCell(2, 1);
        field.writeToSelectedCell('ы');
        boolean success1 = field.selectCell(3, 1);
        boolean success2 = field.selectCell(3, 4);
        Assertions.assertTrue(success1);
        Assertions.assertFalse(success2);
        Assertions.assertEquals(Cell.SelectionState.NOT_SELECTED,
                field.getCell(3, 4).selectionState());
    }

    @Test
    void selectCellToWriteWhichHasNewLetter() {
        GameField field = new GameField("приветь");
        field.selectCell(2, 1);
        field.writeToSelectedCell('ы');
        Assertions.assertEquals(-1, field.getCell(2, 1).selectionIndex());
        boolean success = field.selectCell(2, 1);
        Assertions.assertTrue(success);
        Assertions.assertEquals(0, field.getCell(2, 1).selectionIndex());
        Assertions.assertEquals(Cell.SelectionState.SELECTED_TO_WRITE_LETTER,
                field.getCell(2, 1).selectionState());
    }

    @Test
    void clearSelections() {
        GameField field = new GameField("приветь");
        field.selectCell(2, 1);
        field.writeToSelectedCell('ы');
        field.selectCell(2, 1);
        field.selectCell(2, 2);
        field.clearSelections();
        for (int i = 0; i < field.fieldSize(); i++) {
            for (int j = 0; j < field.fieldSize(); j++) {
                Assertions.assertEquals(-1, field.getCell(i, j).selectionIndex());
                Assertions.assertEquals(Cell.SelectionState.NOT_SELECTED,
                        field.getCell(i, j).selectionState());
            }
        }
    }

    @Test
    void getSelectedWord() {
        GameField field = new GameField("приветь");
        field.selectCell(2, 0);
        field.writeToSelectedCell('ы');
        field.selectCell(2, 0);
        field.selectCell(3, 0);
        field.selectCell(3, 1);
        Assertions.assertEquals("ыпр",
                field.getSelectedWord());
    }

    @Test
    void getSelectedWordNoNewLetter() {
        GameField field = new GameField("приветь");
        field.selectCell(2, 0);
        field.writeToSelectedCell('ы');
        field.selectCell(3, 0);
        field.selectCell(3, 1);
        Assertions.assertNull(field.getSelectedWord());
    }

    @Test
    void getSelectedWordNoSelectedCells() {
        GameField field = new GameField("приветь");
        field.selectCell(2, 0);
        field.writeToSelectedCell('ы');
        Assertions.assertNull(field.getSelectedWord());
    }
}
