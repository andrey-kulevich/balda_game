package model;

import java.util.ArrayList;
import java.util.Objects;

/** Actor in game, does main actions in game loop */
public class Player {

    /** name of the player */
    private final String _name;
    /** word that had discovered by player earlier */
    private final ArrayList<String> _composedWords = new ArrayList<>();
    /** current state of player */
    private boolean _isActive = false;
    /** game */
    private Game _game;

    /** Constructor
     *
     * @param name name of the player
     */
    public Player(String name) { _name = Objects.requireNonNull(name); }

    /** Set game
     *
     * @param game game
     */
    public void setGame(Game game) { _game = Objects.requireNonNull(game); }

    /** Get current state of the player
     *
     * @return state
     */
    public boolean isActive() { return _isActive; }

    /** Get current state of the player
     *
     * @param isActive state
     */
    public void setActive(boolean isActive) { _isActive = isActive; }

    /** Get name of the player
     *
     * @return name
     */
    public String name() { return _name; }

    /** Get list of the words that had discovered by the player earlier
     *
     * @return list of words
     */
    public ArrayList<String> getWords() { return new ArrayList<>(_composedWords); }

    /** Add new word discovered by the player
     *
     * @param word word
     */
    public void addWord(String word) { _composedWords.add(Objects.requireNonNull(word)); }

    /** Add word to dictionary
     *
     * @param word word
     * @param definition definition of word
     * @return success
     */
    public boolean addWordToDictionary(String word, String definition) {
        return _game.dictionary().addWord(word, definition);
    }

    /** Select specified cell on game field
     *
     * @param row row index
     * @param col column index
     * @return success
     */
    public boolean selectCell(int row, int col) { return _game.field().selectCell(row, col); }

    /** Write letter to specified cell
     *
     * @param letter Unicode letter
     * @return success
     */
    public boolean writeToSelectedCell(Character letter) { return _game.field().writeToSelectedCell(letter); }

    /** Undo all actions committed in current move */
    public void undoCurrentActions() { _game.field().clearSelections(); }

    public void skipMove() {
        _game.skipMove();

    }

    public Game.WordCheckStatus confirmMove() {
        return _game.confirmMove();

    }
}
