package model;

import java.util.ArrayList;
import java.util.Objects;

/** Class that controls dictionaries, game field, both users and provides main game loop */
public class Game {

    /** Return state of confirmMove method */
    public enum WordCheckStatus {
        SUCCESS,
        CURRENT_LETTER_DOES_NOT_SELECTED,
        WORD_HAD_DISCOVERED_EARLIER,
        WORD_DOES_NOT_EXIST
    }

    /** game field with cells */
    private final GameField _field;
    /** all dictionaries, added to current game */
    private final ArrayList<Dictionary> _dictionaries;
    /** first player */
    private final Player _firstPlayer;
    /** second player */
    private final Player _secondPlayer;

    /** Constructor
     *
     * @param fieldSize size of game field
     * @param dictionaries all dictionaries that will be used in current game
     * @param first first player
     * @param second second player
     */
    public Game(int fieldSize, ArrayList<Dictionary> dictionaries, Player first, Player second) {
        _dictionaries = Objects.requireNonNull(dictionaries);
        _firstPlayer = Objects.requireNonNull(first);
        _firstPlayer.setGame(this);
        _firstPlayer.setActive(true);
        _secondPlayer = Objects.requireNonNull(second);
        _secondPlayer.setGame(this);
        _field = new GameField(_dictionaries.get(0).getRandomWord(fieldSize));
    }

    /** Get game field
     *
     * @return game field
     */
    public GameField field() { return _field; }

    /** Get all dictionaries
     *
     * @return list of dictionaries
     */
    public ArrayList<Dictionary> dictionaries() { return _dictionaries; }

    /** Get first player
     *
     * @return first player
     */
    public Player firstPlayer() { return _firstPlayer; }

    /** Get second player
     *
     * @return second player
     */
    public Player secondPlayer() { return _secondPlayer; }

    /** Get active player
     *
     * @return active player
     */
    public Player activePlayer() { return _firstPlayer.isActive() ? _firstPlayer : _secondPlayer; }

    /** Switch active player */
    private void nextPlayer() {
        if (_firstPlayer.isActive()) {
            _firstPlayer.setActive(false);
            _secondPlayer.setActive(true);
        } else {
            _firstPlayer.setActive(true);
            _secondPlayer.setActive(false);
        }
    }

    /** Check correctness of the selected word
     *
     * @return status
     */
    public WordCheckStatus confirmMove() {
        String word = _field.getSelectedWord();
        if (word == null) return WordCheckStatus.CURRENT_LETTER_DOES_NOT_SELECTED;

        ArrayList<String> discoveredWords = new ArrayList<>(_firstPlayer.getWords());
        discoveredWords.addAll(_secondPlayer.getWords());
        if (discoveredWords.contains(word)) return WordCheckStatus.WORD_HAD_DISCOVERED_EARLIER;

        boolean isInDictionaries;
        for (Dictionary dict : _dictionaries) {
            isInDictionaries = dict.hasWord(word);
            if (isInDictionaries) {
                this.activePlayer().addWord(word);
                this.nextPlayer();
                return WordCheckStatus.SUCCESS;
            }
        }
        return WordCheckStatus.WORD_DOES_NOT_EXIST;
    }

    /** Skip current move and switch to next player */
    public void skipMove() {
        _field.clearSelections();
        nextPlayer();
    }
}
