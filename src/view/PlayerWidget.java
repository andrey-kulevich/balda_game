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

    private Player _player;

    private final Color ACTIVE_PLAYER_COLOR = Color.decode("#B8B8B8");
    private JLabel _name = new JLabel();
    private JLabel _score = new JLabel();
    private JPanel _wordsPanel = new JPanel();

    public PlayerWidget(MainWindow owner, Orientation orientation) {
        MainWindow _owner = Objects.requireNonNull(owner);

        setPreferredSize(new Dimension(220, 650));
        setBorder(new RoundedBorder(15));
        setLayout(new BorderLayout(20, 20));

        JPanel namePanel = new JPanel();
        JPanel scorePanel = new JPanel();
        namePanel.setBackground(GlobalStyles.PRIMARY_COLOR);
        scorePanel.setBackground(GlobalStyles.PRIMARY_COLOR);
        _wordsPanel.setLayout(new BoxLayout(_wordsPanel, BoxLayout.Y_AXIS));
        _wordsPanel.setBackground(GlobalStyles.PRIMARY_COLOR);
        namePanel.setPreferredSize(new Dimension(150, 80));
        scorePanel.setPreferredSize(new Dimension(60, 80));
        _wordsPanel.setPreferredSize(new Dimension(210, 570));
        namePanel.setBorder(new RoundedBorder(10));
        scorePanel.setBorder(new RoundedBorder(10));
        _wordsPanel.setBorder(new RoundedBorder(10));

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
        add(_wordsPanel, BorderLayout.PAGE_END);

        _name.setFont(new Font("Roboto", Font.PLAIN, 22));
        _score.setFont(new Font("Roboto", Font.PLAIN, 25));
        namePanel.add(_name, BorderLayout.SOUTH);
        scorePanel.add(_score, BorderLayout.CENTER);
    }

    public void setPlayer(Player player) {
        _player = Objects.requireNonNull(player);
        String name = player.name();
        if (name.length() > 10) {
            _name.setText("<html>" + name.substring(0, 10) + "<br>" + name.substring(10, name.length() - 1) + "</html>");
        } else {
            _name.setText(name);
        }
        _score.setText(((Integer)calculateScore()).toString());
    }

    private int calculateScore() {
        return _player.getWords().stream().mapToInt(String::length).sum();
    }

    public void update() {
        _score.setText(((Integer)calculateScore()).toString());
        _wordsPanel.removeAll();
        _wordsPanel.revalidate();
        _wordsPanel.repaint();
        for (String word : _player.getWords()) {
            JLabel wordLabel = new JLabel(word);
            wordLabel.setFont(GlobalStyles.MAIN_FONT);
            _wordsPanel.add(wordLabel);
        }
    }
}
