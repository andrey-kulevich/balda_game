import model.Dictionary;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

public class DictionaryTest {

    Dictionary _dict;

    void cleanUp() { _dict = new Dictionary(); }

    @Test
    void fileNotFound() {
        cleanUp();
        boolean isError = false;
        try {
            _dict.load("alalalal.txt");
        } catch (FileNotFoundException e) {
            isError = true;
        }
        Assertions.assertTrue(isError);
    }

    @Test
    void invalidFormatOfFile() {
        cleanUp();
        boolean isError = false;
        try {
            _dict.load("../alalalal.json");
        } catch (IllegalArgumentException | FileNotFoundException e) {
            isError = true;
        }
        Assertions.assertTrue(isError);
    }

    @Test
    void getDefinitionOfWord() {
        cleanUp();
        try {
            _dict.load("./dictionaries/customDictionary.txt");
        } catch (FileNotFoundException ignored) { }
        Assertions.assertEquals("блабла", _dict.getDefinition("чикипуки"));
    }

    @Test
    void checkIfSuchWordIsExist() {
        cleanUp();
        try {
            _dict.load("./dictionaries/customDictionary.txt");
        } catch (FileNotFoundException ignored) { }
        Assertions.assertTrue(_dict.hasWord("чикипуки"));
    }

    @Test
    void dictHasNotSuchWord() {
        cleanUp();
        try {
            _dict.load("./dictionaries/customDictionary.txt");
        } catch (FileNotFoundException ignored) { }
        Assertions.assertNull(_dict.getDefinition("покаки"));
        Assertions.assertFalse(_dict.hasWord("покаки"));
    }

    @Test
    void getRandomWord() {
        cleanUp();
        try {
            _dict.load("./dictionaries/customDictionary.txt");
        } catch (FileNotFoundException ignored) { }
        Assertions.assertTrue(_dict.getRandomWord(6) != null
                && _dict.getRandomWord(6).length() == 6);
    }

    @Test
    void getRandomWordWithSuchLengthDoesNotFound() {
        cleanUp();
        try {
            _dict.load("./dictionaries/customDictionary.txt");
        } catch (FileNotFoundException ignored) { }
        Assertions.assertNull(_dict.getRandomWord(2));
    }

    @Test
    void addWordToDict() {
        cleanUp();
        try {
            _dict.load("./dictionaries/customDictionary.txt");
        } catch (FileNotFoundException ignored) { }
        _dict.addWord("хобаа", "привееет");
        Assertions.assertTrue(_dict.hasWord("хобаа"));
    }

    @Test
    void saveDictToFile() {
        cleanUp();
        Dictionary newDict = new Dictionary();
        try {
            _dict.addWord("хобаа", "привееет");
            _dict.setModifiableDictionary("./dictionaries/customDictionary.txt");
            _dict.saveAddedWords();
            newDict.load("./dictionaries/customDictionary.txt");
        } catch (RuntimeException | IOException ignored) { }
        Assertions.assertEquals("привееет", newDict.getDefinition("хобаа"));
    }

    @Test
    void saveDictToFileModifiableDictNotDefined() {
        cleanUp();
        boolean isError = false;
        try {
            _dict.saveAddedWords();
        } catch (RuntimeException | IOException e) {
            isError = true;
        }
        Assertions.assertTrue(isError);
    }
}
