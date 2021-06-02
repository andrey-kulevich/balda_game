package model;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;

/** Dictionary of words that can be used in the game */
public class Dictionary {

    /** words with short definitions */
    private final HashMap<String,String> _words = new HashMap<>();
    /** words added by user in current game */
    private final HashMap<String,String> _addedWords = new HashMap<>();
    /** path to modifiable file, where added words will be saved */
    private String _modifiableDictionary = null;
    /** alphabet of allowed letters */
    private ArrayList<Character> _alphabet = new ArrayList<>(Arrays.asList(
            'А', 'Б', 'В', 'Г', 'Д', 'Е', 'Ё',
            'Ж', 'З', 'И', 'Й', 'К', 'Л', 'М',
            'Н', 'О', 'П', 'Р', 'С', 'Т', 'У',
            'Ф', 'Х', 'Ц', 'Ч', 'Ш', 'Щ', 'Ъ',
            'Ы', 'Ь', 'Э', 'Ю', 'Я'));

    /** Add words to dictionary from txt file
     *
     * @param filename path to txt file with list of words and definitions in format <b>word:definition</b>
     */
    public void load(String filename) throws FileNotFoundException {
        if (!filename.endsWith(".txt")) throw new IllegalArgumentException("Invalid file extension");
        File file = new File(filename);
        Scanner reader = new Scanner(file);
        while (reader.hasNextLine()) {
            String data = reader.nextLine();
            String[] splitted = data.split(":", 2);
            _words.put(splitted[0].replaceAll("\\s+",""), splitted.length > 1 ? splitted[1] : " ");
        }
        reader.close();
    }

    /** Set path to modifiable file, where added words will be saved
     *
     * @param filename path to file
     */
    public void setModifiableDictionary(String filename) {
        _modifiableDictionary = Objects.requireNonNull(filename);
    }

    /** Set alphabet of allowed letters
     *
     * @param alphabet list of letters
     */
    public void setAlphabetRegex(ArrayList<Character> alphabet) { _alphabet = Objects.requireNonNull(alphabet); }

    /** Check correctness of letters
     *
     * @param letter letter
     * @return correctness
     */
    public boolean isLetterValid(Character letter) { return _alphabet.contains(Character.toUpperCase(letter)); }

    /** Get alphabet
     *
     * @return list of letters
     */
    public ArrayList<Character> getAlphabet() { return new ArrayList<>(_alphabet); }

    /** Get short definition of the word
     *
     * @param word word
     * @return definition
     */
    public String getDefinition(String word) {
        String definition = _words.get(word);
        if (definition == null) return _addedWords.get(word);
        else return definition;
    }

    /** Check word in dictionary
     *
     * @param word word
     * @return is word in dictionary or not
     */
    public boolean hasWord(String word) { return _words.containsKey(word) || _addedWords.containsKey(word); }

    /** Get random word with specified length
     *
     * @param length length
     * @return word (null if word with such length not found)
     */
    public String getRandomWord(int length) {
        ArrayList<String> matches = new ArrayList<>();
        _words.forEach((key, value) -> {
            if (key.length() == length) matches.add(key);
        });
        if (matches.size() == 0) return null;
        return matches.get(new Random().nextInt(matches.size()));
    }

    /** Add word to dictionary
     *
     * @param word word
     * @param definition definition of word
     * @return success
     */
    public boolean addWord(String word, String definition) {
        if (!_words.containsKey(word))
            return _addedWords.put(Objects.requireNonNull(word), Objects.requireNonNull(definition)) == null;
        else return false;
    }

    /** save added words to specified txt file */
    public void saveAddedWords() throws IOException {
        if (_modifiableDictionary == null) throw new RuntimeException("Modifiable dictionary is not defined");
        if (_addedWords.size() > 0) {
            FileWriter writer = new FileWriter(_modifiableDictionary, true);
            _addedWords.forEach((key, value) -> {
                try { writer.write("\n" + key + " : " + value); }
                catch (IOException e) { e.printStackTrace(); }
            });
            writer.close();
        }
    }
}
