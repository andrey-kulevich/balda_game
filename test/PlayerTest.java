import model.Cell;
import model.Dictionary;
import model.Game;
import model.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

public class PlayerTest {

    @Test
    void createPlayerNullName() {
        Assertions.assertThrows(NullPointerException.class,
                () -> new Player(null));
    }

    @Test
    void createPlayerNullGame() {
        Player player = new Player("aaaaa");
        Assertions.assertThrows(NullPointerException.class, () -> player.setGame(null));
    }

    @Test
    void addNullWordToPlayer() {
        Player player = new Player("aaaaa");
        Assertions.assertThrows(NullPointerException.class, () -> player.addWord(null));
    }

    @Test
    void addWordToDictionary() {
        Dictionary dict = new Dictionary();
        try {
            dict.load("./dictionaries/customDictionary.txt");
            dict.setModifiableDictionary("./dictionaries/customDictionary.txt");
            dict.load("./dictionaries/russianNounsWithDefinition.txt");
        } catch (FileNotFoundException ignored) {}
        Game game = new Game(7, dict, new Player("bla"), new Player("blo"));
        Player player = new Player("aaaaa");
        player.setGame(game);
        Assertions.assertTrue(player.addWordToDictionary("аааа", "ыыыы"));
    }

    @Test
    void selectCell() {
        Dictionary dict = new Dictionary();
        try {
            dict.load("./dictionaries/russianNounsWithDefinition.txt");
        } catch (FileNotFoundException ignored) {}
        Game game = new Game(7, dict, new Player("bla"), new Player("blo"));
        Player player = new Player("aaaaa");
        player.setGame(game);
        Assertions.assertTrue(player.selectCell(2, 1));
    }

    @Test
    void writeToCell() {
        Dictionary dict = new Dictionary();
        try {
            dict.load("./dictionaries/russianNounsWithDefinition.txt");
        } catch (FileNotFoundException ignored) {}
        Game game = new Game(7, dict, new Player("bla"), new Player("blo"));
        Player player = new Player("aaaaa");
        player.setGame(game);
        player.selectCell(2, 1);
        Assertions.assertTrue(player.writeToSelectedCell('ы'));
    }

    @Test
    void undoAllActionsCommittedInCurrentMove() {
        Dictionary dict = new Dictionary();
        try {
            dict.load("./dictionaries/russianNounsWithDefinition.txt");
        } catch (FileNotFoundException ignored) {}
        Game game = new Game(7, dict, new Player("bla"), new Player("blo"));
        Player player = new Player("aaaaa");
        player.setGame(game);
        player.selectCell(2, 1);
        player.writeToSelectedCell('ы');
        player.undoCurrentActions();
        Assertions.assertEquals(' ', game.field().getCell(2, 1).letter());
        Assertions.assertEquals(Cell.SelectionState.NOT_SELECTED, game.field().getCell(2, 1).selectionState());
    }
}
