import model.*;
import model.Dictionary;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.*;

public class ComputerPlayerTest {

    // Supporting class to test protected methods
    public static class ComputerPlayerForTests extends ComputerPlayer {

        public ComputerPlayerForTests(String name, Intellect intellect) {
            super(name, intellect);
        }

        @Override
        public void searchSequences(ArrayList<ArrayList<CellObj>> sequences, int maxLength) {
            super.searchSequences(sequences, maxLength);
        }

        @Override
        public void findWordsInDictionary(ArrayList<ArrayList<CellObj>> inSequences, ArrayList<ArrayList<CellObj>> outSequences) {
            super.findWordsInDictionary(inSequences, outSequences);
        }

        @Override
        public void selectSequenceOnField(ArrayList<CellObj> sequence) {
            super.selectSequenceOnField(sequence);
        }

        @Override
        public void filterSequences(ArrayList<ArrayList<CellObj>> sequences) {
            super.filterSequences(sequences);
        }

        @Override
        protected ArrayList<CellObj> getLongestSequence(ArrayList<ArrayList<CellObj>> sequences) {
            return super.getLongestSequence(sequences);
        }
    }

    @Test
    void searchSequencesMinDepth() {
        Dictionary dict = new Dictionary();
        try {
            dict.load("./dictionaries/russianNounsWithDefinition.txt");
        } catch (FileNotFoundException ignored) {}

        Game game = new Game(5, dict, new Player("player"),
                new ComputerPlayerForTests("computer", ComputerPlayerForTests.Intellect.MEDIUM));

        ArrayList<ArrayList<ComputerPlayerForTests.CellObj>> sequences = new ArrayList<>();
        ((ComputerPlayerForTests)game.secondPlayer()).searchSequences(sequences, 2);

        System.out.println(sequences.size());

        Optional<ArrayList<ComputerPlayer.CellObj>> min = sequences.stream().min(Comparator.comparing(List::size));
        min.ifPresent(System.out::println);
        Optional<ArrayList<ComputerPlayer.CellObj>> max = sequences.stream().max(Comparator.comparing(List::size));
        max.ifPresent(System.out::println);

        Assertions.assertEquals(95, sequences.size());
        Assertions.assertEquals(1, min.get().size());
        Assertions.assertEquals(2, max.get().size());
    }

    @Test
    void searchSequencesMaxDepth() {
        Dictionary dict = new Dictionary();
        try {
            dict.load("./dictionaries/russianNounsWithDefinition.txt");
        } catch (FileNotFoundException ignored) {}

        Game game = new Game(5, dict, new Player("player"),
                new ComputerPlayerForTests("computer", ComputerPlayerForTests.Intellect.MEDIUM));

        ArrayList<ArrayList<ComputerPlayer.CellObj>> sequences = new ArrayList<>();
        ((ComputerPlayerForTests)game.secondPlayer()).searchSequences(sequences, 6);

        System.out.println(sequences.size());

        Optional<ArrayList<ComputerPlayer.CellObj>> min = sequences.stream().min(Comparator.comparing(List::size));
        min.ifPresent(System.out::println);
        Optional<ArrayList<ComputerPlayer.CellObj>> max = sequences.stream().max(Comparator.comparing(List::size));
        max.ifPresent(System.out::println);

        Assertions.assertEquals(25455, sequences.size());
        Assertions.assertEquals(1, min.get().size());
        Assertions.assertEquals(6, max.get().size());
    }

    @Test
    void filterSequences() {
        Dictionary dict = new Dictionary();
        try {
            dict.load("./dictionaries/russianNounsWithDefinition.txt");
        } catch (FileNotFoundException ignored) {}

        Game game = new Game(5, dict, new Player("player"),
                new ComputerPlayerForTests("computer", ComputerPlayerForTests.Intellect.MEDIUM));

        ArrayList<ArrayList<ComputerPlayer.CellObj>> sequences = new ArrayList<>();
        ((ComputerPlayerForTests)game.secondPlayer()).searchSequences(sequences, 6);
        System.out.println(sequences.size());
        ((ComputerPlayerForTests)game.secondPlayer()).filterSequences(sequences);
        System.out.println(sequences.size());

        Optional<ArrayList<ComputerPlayer.CellObj>> min = sequences.stream().min(Comparator.comparing(List::size));
        min.ifPresent(System.out::println);
        Optional<ArrayList<ComputerPlayer.CellObj>> max = sequences.stream().max(Comparator.comparing(List::size));
        max.ifPresent(System.out::println);

        Assertions.assertEquals(644, sequences.size());
        Assertions.assertEquals(2, min.get().size());
        Assertions.assertEquals(6, max.get().size());
    }

    @Test
    void findWordsInDictionaryFromField() {
        Dictionary dict = new Dictionary();
        try {
            dict.load("./dictionaries/russianNounsWithDefinition.txt");
        } catch (FileNotFoundException ignored) {}

        Game game = new Game(5, dict, new Player("player"),
                new ComputerPlayerForTests("computer", ComputerPlayerForTests.Intellect.MEDIUM));

        ArrayList<ArrayList<ComputerPlayer.CellObj>> sequences = new ArrayList<>();
        ((ComputerPlayerForTests)game.secondPlayer()).searchSequences(sequences, 6);
        ((ComputerPlayerForTests)game.secondPlayer()).filterSequences(sequences);

        ArrayList<ArrayList<ComputerPlayer.CellObj>> resultSequences = new ArrayList<>();
        ((ComputerPlayerForTests)game.secondPlayer()).findWordsInDictionary(sequences, resultSequences);

        Assertions.assertTrue(resultSequences.size() > 0);
    }

    @Test
    void findWordsInDictionaryOneWordFound() {
        Dictionary dict = new Dictionary();
        try {
            dict.load("./dictionaries/russianNounsWithDefinition.txt");
        } catch (FileNotFoundException ignored) {}

        Game game = new Game(5, dict, new Player("player"),
                new ComputerPlayerForTests("computer", ComputerPlayerForTests.Intellect.MEDIUM));

        ArrayList<ArrayList<ComputerPlayer.CellObj>> sequences = new ArrayList<>();

        ArrayList<ComputerPlayer.CellObj> sequence1 = new ArrayList<>();
        sequence1.add(new ComputerPlayer.CellObj('Ы', 0,0));
        sequence1.add(new ComputerPlayer.CellObj(' ', 0,0));
        sequence1.add(new ComputerPlayer.CellObj('Ы', 0,0));

        ArrayList<ComputerPlayer.CellObj> sequence2 = new ArrayList<>();
        sequence2.add(new ComputerPlayer.CellObj('Р', 0,0));
        sequence2.add(new ComputerPlayer.CellObj(' ', 0,0));
        sequence2.add(new ComputerPlayer.CellObj('К', 0,0));
        sequence2.add(new ComputerPlayer.CellObj('А', 0,0));

        sequences.add(sequence1);
        sequences.add(sequence2);

        ArrayList<ArrayList<ComputerPlayer.CellObj>> resultSequences = new ArrayList<>();
        ((ComputerPlayerForTests)game.secondPlayer()).findWordsInDictionary(sequences, resultSequences);

        Assertions.assertEquals(1, resultSequences.size());
    }

    @Test
    void findWordsInDictionarySeveralWordsFound() {
        Dictionary dict = new Dictionary();
        try {
            dict.load("./dictionaries/russianNounsWithDefinition.txt");
        } catch (FileNotFoundException ignored) {}

        Game game = new Game(5, dict, new Player("player"),
                new ComputerPlayerForTests("computer", ComputerPlayerForTests.Intellect.MEDIUM));

        ArrayList<ArrayList<ComputerPlayer.CellObj>> sequences = new ArrayList<>();

        ArrayList<ComputerPlayer.CellObj> sequence1 = new ArrayList<>();
        sequence1.add(new ComputerPlayer.CellObj(' ', 0,0));
        sequence1.add(new ComputerPlayer.CellObj('Ы', 0,0));
        sequence1.add(new ComputerPlayer.CellObj('Р', 0,0));

        ArrayList<ComputerPlayer.CellObj> sequence2 = new ArrayList<>();
        sequence2.add(new ComputerPlayer.CellObj('Р', 0,0));
        sequence2.add(new ComputerPlayer.CellObj(' ', 0,0));
        sequence2.add(new ComputerPlayer.CellObj('К', 0,0));
        sequence2.add(new ComputerPlayer.CellObj('А', 0,0));

        ArrayList<ComputerPlayer.CellObj> sequence3 = new ArrayList<>();
        sequence3.add(new ComputerPlayer.CellObj('М', 0,0));
        sequence3.add(new ComputerPlayer.CellObj('О', 0,0));
        sequence3.add(new ComputerPlayer.CellObj('Р', 0,0));
        sequence3.add(new ComputerPlayer.CellObj(' ', 0,0));

        sequences.add(sequence1);
        sequences.add(sequence2);
        sequences.add(sequence3);

        ArrayList<ArrayList<ComputerPlayer.CellObj>> resultSequences = new ArrayList<>();
        ((ComputerPlayerForTests)game.secondPlayer()).findWordsInDictionary(sequences, resultSequences);

        Assertions.assertEquals(3, resultSequences.size());
    }

    @Test
    void findWordsInDictionaryNoFound() {
        Dictionary dict = new Dictionary();
        try {
            dict.load("./dictionaries/russianNounsWithDefinition.txt");
        } catch (FileNotFoundException ignored) {}

        Game game = new Game(5, dict, new Player("player"),
                new ComputerPlayerForTests("computer", ComputerPlayerForTests.Intellect.EASY));

        ArrayList<ArrayList<ComputerPlayer.CellObj>> sequences = new ArrayList<>();

        ArrayList<ComputerPlayer.CellObj> sequence1 = new ArrayList<>();
        sequence1.add(new ComputerPlayer.CellObj(' ', 0,0));
        sequence1.add(new ComputerPlayer.CellObj('Ы', 0,0));
        sequence1.add(new ComputerPlayer.CellObj('Ы', 0,0));

        ArrayList<ComputerPlayer.CellObj> sequence2 = new ArrayList<>();
        sequence2.add(new ComputerPlayer.CellObj('Й', 0,0));
        sequence2.add(new ComputerPlayer.CellObj(' ', 0,0));
        sequence2.add(new ComputerPlayer.CellObj('К', 0,0));
        sequence2.add(new ComputerPlayer.CellObj('Ы', 0,0));

        ArrayList<ComputerPlayer.CellObj> sequence3 = new ArrayList<>();
        sequence3.add(new ComputerPlayer.CellObj('М', 0,0));
        sequence3.add(new ComputerPlayer.CellObj('Ы', 0,0));
        sequence3.add(new ComputerPlayer.CellObj('Р', 0,0));
        sequence3.add(new ComputerPlayer.CellObj(' ', 0,0));

        sequences.add(sequence1);
        sequences.add(sequence2);
        sequences.add(sequence3);

        ArrayList<ArrayList<ComputerPlayer.CellObj>> resultSequences = new ArrayList<>();
        ((ComputerPlayerForTests)game.secondPlayer()).findWordsInDictionary(sequences, resultSequences);

        Assertions.assertEquals(0, resultSequences.size());
    }

    @Test
    void selectWordOnTheField() {
        Dictionary dict = new Dictionary();
        try {
            dict.load("./dictionaries/russianNounsWithDefinition.txt");
        } catch (FileNotFoundException ignored) {}

        Game game = new Game(5, dict, new Player("player"),
                new ComputerPlayerForTests("computer", ComputerPlayerForTests.Intellect.HARD));

        ArrayList<ArrayList<ComputerPlayer.CellObj>> sequences = new ArrayList<>();
        ((ComputerPlayerForTests)game.secondPlayer()).searchSequences(sequences, 6);
        ((ComputerPlayerForTests)game.secondPlayer()).filterSequences(sequences);

        ArrayList<ArrayList<ComputerPlayer.CellObj>> resultSequences = new ArrayList<>();
        ((ComputerPlayerForTests)game.secondPlayer()).findWordsInDictionary(sequences, resultSequences);

        ArrayList<ComputerPlayer.CellObj> longest =
                ((ComputerPlayerForTests)game.secondPlayer()).getLongestSequence(resultSequences);

        ((ComputerPlayerForTests)game.secondPlayer()).selectSequenceOnField(longest);

        for (int i = 0; i < longest.size(); i++) {
            Assertions.assertEquals(i, game.field().getCell(longest.get(i).row, longest.get(i).col).selectionIndex());
        }
    }

    @Test
    void successMove() {
        Dictionary dict = new Dictionary();
        try {
            dict.load("./dictionaries/russianNounsWithDefinition.txt");
        } catch (FileNotFoundException ignored) {}

        Game game = new Game(5, dict, new Player("player"),
                new ComputerPlayerForTests("computer", ComputerPlayerForTests.Intellect.MEDIUM));
        game.firstPlayer().skipMove();
        Assertions.assertEquals(1, game.secondPlayer().getWords().size());
    }
}

