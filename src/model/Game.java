package model;

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
    private final Dictionary _dictionary;
    /** first player */
    private final Player _firstPlayer;
    /** second player */
    private final Player _secondPlayer;

    /** Constructor
     *
     * @param fieldSize size of game field
     * @param dictionary all dictionaries that will be used in current game
     * @param first first player
     * @param second second player
     */
    public Game(int fieldSize, Dictionary dictionary, Player first, Player second) {
        _dictionary = Objects.requireNonNull(dictionary);
        _firstPlayer = Objects.requireNonNull(first);
        _firstPlayer.setGame(this);
        _firstPlayer.setActive(true);
        _secondPlayer = Objects.requireNonNull(second);
        _secondPlayer.setGame(this);
        _field = new GameField(_dictionary.getRandomWord(fieldSize));
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
    public Dictionary dictionary() { return _dictionary; }

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
        if (_firstPlayer.getWords().contains(word) || _secondPlayer.getWords().contains(word))
            return WordCheckStatus.WORD_HAD_DISCOVERED_EARLIER;

        if (_dictionary.hasWord(word)) {
            this.activePlayer().addWord(word);
            _field.prepareToNextMove();
            this.nextPlayer();
            return WordCheckStatus.SUCCESS;
        }

        return WordCheckStatus.WORD_DOES_NOT_EXIST;
    }

    /** Skip current move and switch to next player */
    public void skipMove() {
        _field.clearSelections();
        nextPlayer();
    }
}
