package model;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Objects;

/** Builder of Game object */
public class GameBuilder {

    /** size of the game field */
    private int _fieldSize;
    /** first player */
    private Player _firstPlayer;
    /** second player */
    private Player _secondPlayer;
    /** allowed dictionaries */
    private final ArrayList<Dictionary> _dictionaries = new ArrayList<>();

    /** Set size of the game field
     *
     * @param size size of the game field (cannot be less than 5)
     */
    public void setFieldSize(int size) {
        if (size < 5) throw new IllegalArgumentException("Field size cannot be less than 5");
        _fieldSize = size;
    }

    /** Set first player
     *
     * @param player first player
     */
    public void setFirstPlayer(Player player) { _firstPlayer = Objects.requireNonNull(player); }

    /** Set second player
     *
     * @param player second player
     */
    public void setSecondPlayer(Player player) { _secondPlayer = Objects.requireNonNull(player); }

    /** add dictionary to game */
    public void addDictionary(String filename) throws FileNotFoundException {
        _dictionaries.add(new Dictionary(Objects.requireNonNull(filename)));
    }

    /** Create game with followed parameters
     *
     * @return game
     */
    public Game initGame() {
        return new Game(_fieldSize, _dictionaries, _firstPlayer, _secondPlayer);
    }
}
