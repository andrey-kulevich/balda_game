import model.Game;
import model.GameBuilder;
import model.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

public class GameBuilderTest {
    GameBuilder _builder;

    void cleanUp() { _builder = new GameBuilder(); }

    @Test
    void normalCreation() {
        cleanUp();
        _builder.setFieldSize(5);
        _builder.setFirstPlayer(new Player("Вася"));
        _builder.setSecondPlayer(new Player("Жора"));
        try {
            _builder.addDictionary("./dictionaries/russianNounsWithDefinition.txt");
        } catch (FileNotFoundException ignored) { }
        Game game = _builder.initGame();
        Assertions.assertTrue(game.firstPlayer().name().equals("Вася")
                && game.secondPlayer().name().equals("Жора")
                && game.field() != null && game.dictionary().hasWord("жизнь"));
    }

    @Test
    void fieldSizeLessThan5() {
        cleanUp();
        boolean isError = false;
        try {
            _builder.setFieldSize(4);
        } catch (IllegalArgumentException e) {
            isError = true;
        }
        Assertions.assertTrue(isError);
    }

    @Test
    void nullPlayer() {
        cleanUp();
        boolean isError = false;
        try {
            _builder.setFirstPlayer(null);
        } catch (NullPointerException e) {
            isError = true;
        }
        Assertions.assertTrue(isError);
    }

    @Test
    void nullFilenameOfDictionary() {
        cleanUp();
        boolean isError = false;
        try {
            _builder.addDictionary(null);
        } catch (NullPointerException | FileNotFoundException e) {
            isError = true;
        }
        Assertions.assertTrue(isError);
    }

    @Test
    void invalidFilenameOfDictionary() {
        cleanUp();
        boolean isError = false;
        try {
            _builder.addDictionary("./dictionaries/blabla.txt");
        } catch (FileNotFoundException e) {
            isError = true;
        }
        Assertions.assertTrue(isError);
    }
}
