package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

/** Dictionary of words that can be used in the game */
public class Dictionary {

    /** words with short definitions */
    private final HashMap<String,String> _words = new HashMap<>();

    /** Constructor
     *
     * @param filename path to txt file with list of words and definitions in format <b>word:definition</b>
     */
    public Dictionary(String filename) throws FileNotFoundException {
        if (filename.endsWith(".txt")) throw new IllegalArgumentException("Invalid file extension");
        File file = new File(filename);
        Scanner reader = new Scanner(file);
        while (reader.hasNextLine()) {
            String data = reader.nextLine();
            String[] splitted = data.split(":", 2);
            _words.put(splitted[0], splitted[1]);
        }
        reader.close();
    }

    /** Get short definition of the word
     *
     * @param word word
     * @return definition
     */
    public String getDefinition(String word) { return _words.get(word); }

    /** Check word in dictionary
     *
     * @param word word
     * @return is word in dictionary or not
     */
    public boolean hasWord(String word) { return _words.containsKey(word); }

    /** Get random word with specified length
     *
     * @param length length
     * @return word
     */
    public String getRandomWord(int length) {
        ArrayList<String> matches = new ArrayList<>();
        _words.forEach((key, value) -> {
            if (key.length() == length) matches.add(key);
        });
        return matches.get(new Random().nextInt(matches.size()));
    }
}
