package model;

import java.util.ArrayList;

public class AdvancedComputerPlayer extends ComputerPlayer {

    protected int _currentWordLength = 2;

    public AdvancedComputerPlayer(String name) {
        super(name, ComputerPlayer.Intellect.HARD);
    }

    @Override
    protected void doMove() {
        ArrayList<ArrayList<CellObj>> sequences = new ArrayList<>();
        ArrayList<ArrayList<CellObj>> resultSequences = new ArrayList<>();

        while (resultSequences.isEmpty() && _currentWordLength >= 2) {
            this.searchSequences(sequences, _currentWordLength);
            filterSequences(sequences);
            this.findWordsInDictionary(sequences, resultSequences);

            if (!resultSequences.isEmpty()) break;
            else _currentWordLength--;
        }

        if (!resultSequences.isEmpty()) {
            this.selectSequenceOnField(getLongestSequence(resultSequences));
            confirmMove();
            if (_currentWordLength < 8) _currentWordLength++;
        } else {
            skipMove();
        }
    }
}
