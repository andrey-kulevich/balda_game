import model.GameField;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GameFieldTest {

    @Test
    void startWordHasLessThan5Letters() {
        boolean isError = false;
        try {
            GameField field = new GameField("hell");
        } catch (IllegalArgumentException e) {
            isError = true;
        }
        Assertions.assertTrue(isError);
    }

    @Test
    void startWordContainsNoneCyrillicSymbols() {
        boolean isError = false;
        try {
            GameField field = new GameField("helббг");
        } catch (IllegalArgumentException e) {
            isError = true;
        }
        Assertions.assertTrue(isError);
    }

    @Test
    void selectCellToWriteLetter() {
        GameField field = new GameField("hello");
        field.selectCell(2, 1);
        //Assertions.assertTrue(field.ge);
    }

    // select cell to write letter

    // select cell to create word

    // cannot select to write letter, because cell has no neighbours with letters

    // cannot select to write letter, because cell is not empty

    // cannot select to create word, because new letter is not set

    // cannot select to create word, because cell has no neighbour with last selection index (invalid order of letters)

    // clear selections

    // get selected word, no selected cells

}
