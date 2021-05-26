package model;

import java.util.ArrayList;

/** Player with different intellect levels, that makes moves automatically */
public class ComputerPlayer extends Player {

    /** Intellect level */
    public enum Intellect { EASY, MEDIUM, HARD }

    /** intellect level */
    private final int _sequencesDepth;

    public ComputerPlayer(String name, Intellect intellect) {
        super(name);
        _sequencesDepth = intellect.ordinal() + 3;
    }

    @Override
    public void setActive(boolean isActive) {
        _isActive = isActive;
        if (isActive) doMove();
    }

    /** Do automatic move */
    public void doMove() {
        ArrayList<ArrayList<Cell>> sequences = new ArrayList<>(); //

        // search sequences
        for (int i = 0; i < _game.field().size(); i++) {
            for (int j = 0; j < _game.field().size(); j++) {

            }
        }
    }
}