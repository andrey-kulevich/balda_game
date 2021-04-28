package view;

import model.Player;
import view.helpers.GlobalStyles;
import view.helpers.RoundedBorder;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

public class PlayerWidget extends JPanel {

    public enum Orientation { RIGHT, LEFT }

    private final MainWindow _owner;
    private final Color ACTIVE_PLAYER_COLOR = Color.decode("#B8B8B8");
    private JLabel _name = new JLabel();
    private JLabel _score = new JLabel();;
    private ArrayList<JLabel> _words = new ArrayList<>();
    private final JPanel namePanel = new JPanel();
    private final JPanel scorePanel = new JPanel();
    private final JPanel wordsPanel = new JPanel();

    public PlayerWidget(MainWindow owner, Orientation orientation) {
        _owner = Objects.requireNonNull(owner);

        setPreferredSize(new Dimension(220, 650));
        setBackground(GlobalStyles.SECONDARY_COLOR);
        setBorder(new RoundedBorder(15));
        setLayout(new BorderLayout(20, 20));

        namePanel.setBackground(GlobalStyles.PRIMARY_COLOR);
        scorePanel.setBackground(GlobalStyles.PRIMARY_COLOR);
        wordsPanel.setLayout(new BoxLayout(wordsPanel, BoxLayout.Y_AXIS));
        wordsPanel.setBackground(GlobalStyles.PRIMARY_COLOR);
        namePanel.setPreferredSize(new Dimension(150, 80));
        scorePanel.setPreferredSize(new Dimension(60, 80));
        wordsPanel.setPreferredSize(new Dimension(210, 570));
        namePanel.setBorder(new RoundedBorder(10));
        scorePanel.setBorder(new RoundedBorder(10));
        wordsPanel.setBorder(new RoundedBorder(10));

        _words.add(new JLabel("hihihii"));
        _words.add(new JLabel("aboba"));
        _words.add(new JLabel("californication"));

        for (JLabel word : _words) {
            word.setFont(GlobalStyles.MAIN_FONT);
            wordsPanel.add(word);
        }

        JPanel rowPack = new JPanel(new BorderLayout(20, 20));
        rowPack.setPreferredSize(new Dimension(210, 80));
        rowPack.setBackground(GlobalStyles.PRIMARY_COLOR);

        if (orientation == Orientation.RIGHT) {
            rowPack.add(namePanel, BorderLayout.LINE_END);
            rowPack.add(scorePanel, BorderLayout.LINE_START);
        } else {
            rowPack.add(namePanel, BorderLayout.LINE_START);
            rowPack.add(scorePanel, BorderLayout.LINE_END);
        }

        add(rowPack, BorderLayout.PAGE_START);
        add(wordsPanel, BorderLayout.PAGE_END);
    }

    public void setPlayer(Player player) {

        _name.setText(player.name());
        _score.setText(((Integer)calculateScore()).toString());
        _name.setFont(new Font("Roboto", Font.PLAIN, 22));
        _score.setFont(new Font("Roboto", Font.PLAIN, 25));
        namePanel.add(_name, BorderLayout.SOUTH);
        scorePanel.add(_score, BorderLayout.CENTER);
    }

    private int calculateScore() {
        return 0;
    }


}
