package model;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

/** Mutable dictionary with ability to add words and save all words in txt file */
public class CustomDictionary extends Dictionary {

    private final HashMap<String,String> _addedWords = new HashMap<>();
    private final String _filename;

    /** Constructor
     *
     * @param filename path to txt file with list of words and definitions in format <b>word:definition</b>
     */
    public CustomDictionary(String filename) throws FileNotFoundException {
        super(filename);
        _filename = filename;
    }

    /** Add word to dictionary
     *
     * @param word word
     * @param definition short definition
     */
    public void addWord(String word, String definition) {
        _addedWords.put(Objects.requireNonNull(word), definition);
    }

    /** save added words to specified txt file */
    public void save() throws IOException {
        FileWriter writer = new FileWriter(_filename);
        _addedWords.forEach((key, value) -> {
            try {
                writer.write(key + " : " + value);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        writer.close();
    }
}
