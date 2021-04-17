import model.Dictionary;
import model.Game;
import model.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

public class GameTest {

    @Test
    void nullDictionaries() {
        Assertions.assertThrows(NullPointerException.class,
                () -> new Game(6, null, new Player("bla"), new Player("blo")));
    }

    @Test
    void nullPlayer() {
        Assertions.assertThrows(NullPointerException.class,
                () -> new Game(6, new Dictionary(), null, null));
    }

    @Test
    void skipMove() {
        Dictionary dict = new Dictionary();
        try {
            dict.load("./dictionaries/customDictionary.txt");
        } catch (FileNotFoundException ignored) {}

        Game game = new Game(6, dict, new Player("bla"), new Player("blo"));
        game.skipMove();
        Assertions.assertEquals("blo", game.activePlayer().name());
    }

    @Test
    void confirmMoveCurrentLetterIsNotSelected() {
        Dictionary dict = new Dictionary();
        try {
            dict.load("./dictionaries/customDictionary.txt");
        } catch (FileNotFoundException ignored) {}

        Game game = new Game(6, dict, new Player("bla"), new Player("blo"));

        Assertions.assertEquals(Game.WordCheckStatus.CURRENT_LETTER_DOES_NOT_SELECTED, game.confirmMove());
    }

    @Test
    void confirmMoveWordHadDiscoveredEarlier() {
        Dictionary dict = new Dictionary();
        try {
            dict.load("./dictionaries/customDictionary.txt");
            dict.load("./dictionaries/russianNounsWithDefinition.txt");
        } catch (FileNotFoundException ignored) {}

        Game game = new Game(5, dict, new Player("bla"), new Player("blo"));
        while (game.field().getCell(2, 0).letter() != 'Д') {
            game = new Game(5, dict, new Player("bla"), new Player("blo"));
        }

        game.firstPlayer().addWord("яд");

        game.field().selectCell(1, 0);
        game.field().writeToSelectedCell('я');

        game.field().selectCell(1, 0);
        game.field().selectCell(2, 0);

        Assertions.assertEquals(Game.WordCheckStatus.WORD_HAD_DISCOVERED_EARLIER, game.confirmMove());
    }

    @Test
    void confirmMoveSuchWordDoesNotExist() {
        Dictionary dict = new Dictionary();
        try {
            dict.load("./dictionaries/customDictionary.txt");
            dict.load("./dictionaries/russianNounsWithDefinition.txt");
        } catch (FileNotFoundException ignored) {}

        Game game = new Game(5, dict, new Player("bla"), new Player("blo"));
        while (game.field().getCell(2, 0).letter() != 'Д') {
            game = new Game(5, dict, new Player("bla"), new Player("blo"));
        }

        game.field().selectCell(1, 0);
        game.field().writeToSelectedCell('ж');

        game.field().selectCell(1, 0);
        game.field().selectCell(2, 0);

        Assertions.assertEquals(Game.WordCheckStatus.WORD_DOES_NOT_EXIST, game.confirmMove());
    }

    @Test
    void moveSuccessfullyConfirmed() {
        Dictionary dict = new Dictionary();
        try {
            dict.load("./dictionaries/customDictionary.txt");
            dict.load("./dictionaries/russianNounsWithDefinition.txt");
        } catch (FileNotFoundException ignored) {}

        Game game = new Game(5, dict, new Player("bla"), new Player("blo"));
        while (game.field().getCell(2, 0).letter() != 'Д') {
            game = new Game(5, dict, new Player("bla"), new Player("blo"));
        }

        game.field().selectCell(1, 0);
        game.field().writeToSelectedCell('я');

        game.field().selectCell(1, 0);
        game.field().selectCell(2, 0);

        Assertions.assertEquals(Game.WordCheckStatus.SUCCESS, game.confirmMove());
    }
}
