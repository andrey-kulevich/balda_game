package view;

import model.Player;
import view.helpers.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;

/** Represents the state of players in the game */
public class PlayerWidget extends RoundedPanel {

    /** Variants of player's visualisation depends of widget's position */
    public enum Orientation { RIGHT, LEFT }

    private Player _player;
    private final GameWidget _owner;
    private final JLabel _name = new JLabel();
    private final JLabel _score = new JLabel();
    private final JPanel _wordsPanel = new RoundedPanel(10);
    private final JPanel rowPack = new JPanel(new BorderLayout(20, 20));
    private final CustomMessageModal _wordDefinitionModal;

    /** Constructor
     *
     * @param owner game widget
     * @param orientation orientation of widget
     */
    public PlayerWidget(GameWidget owner, Orientation orientation) {
        super(10);
        _owner = Objects.requireNonNull(owner);

        setPreferredSize(new Dimension(220, 650));
        setLayout(new BorderLayout(20, 20));

        // word definition modal
        JLabel message = new JLabel("");
        message.setFont(GlobalStyles.MAIN_FONT);
        _wordDefinitionModal = new CustomMessageModal(null, message);
        CustomActionButton cancelButton = new CustomActionButton("ОК");
        cancelButton.addActionListener(e -> _wordDefinitionModal.setVisible(false));
        _wordDefinitionModal.addButton(cancelButton);

        // pack widgets
        JPanel namePanel = new RoundedPanel(10);
        JPanel scorePanel = new RoundedPanel(10);
        namePanel.setBackground(GlobalStyles.PRIMARY_COLOR);
        scorePanel.setBackground(GlobalStyles.PRIMARY_COLOR);
        _wordsPanel.setLayout(new BoxLayout(_wordsPanel, BoxLayout.Y_AXIS));
        _wordsPanel.setBackground(GlobalStyles.PRIMARY_COLOR);
        namePanel.setPreferredSize(new Dimension(150, 60));
        scorePanel.setPreferredSize(new Dimension(50, 60));
        _wordsPanel.setPreferredSize(new Dimension(210, 560));

        rowPack.setPreferredSize(new Dimension(210, 80));

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

    /** Link player model to this
     *
     * @param player player model
     */
    public void setPlayer(Player player) {
        _player = Objects.requireNonNull(player);
        String name = player.name();
        if (name.length() > 10) _name.setText("<html>" + name.substring(0, 10) +
                "<br>" + name.substring(10) + "</html>");
        else _name.setText(name);
        this.update();
    }

    /** Update widget depends of player model state */
    public void update() {
        // calculate new score
        _score.setText(((Integer)_player.getWords().stream().mapToInt(String::length).sum()).toString());
        _wordsPanel.removeAll();
        _wordsPanel.revalidate();
        _wordsPanel.repaint();

        // redraw words list
        for (String word : _player.getWords()) {
            JLabel wordLabel = new JLabel(word);
            wordLabel.setFont(GlobalStyles.MAIN_FONT);
            wordLabel.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    String definition = _owner.getGame().dictionary().getDefinition(wordLabel.getText());
                    if (definition.length() > 650) {
                        _wordDefinitionModal.setSize(new Dimension(500, 700));
                        _wordDefinitionModal.setLocation(520, 70);
                    } else if (definition.length() > 550) {
                        _wordDefinitionModal.setSize(new Dimension(500, 500));
                        _wordDefinitionModal.setLocation(520, 150);
                    } else if (definition.length() > 450) {
                        _wordDefinitionModal.setSize(new Dimension(500, 400));
                        _wordDefinitionModal.setLocation(520, 250);
                    } else {
                        _wordDefinitionModal.setSize(new Dimension(500, 300));
                        _wordDefinitionModal.setLocation(520, 300);
                    }

                    _wordDefinitionModal.setMessage(
                            "<html>" +
                                "<div style='width: 450; max-height: 150;'>" +
                                    "<h2 style='text-align: center;'>" +
                                        wordLabel.getText().toUpperCase() +
                                    "</h2>" +
                                    _owner.getGame().dictionary().getDefinition(wordLabel.getText()) +
                                "</div>" +
                            "</html>");
                    _wordDefinitionModal.setVisible(true);
                }
                public void mouseEntered(MouseEvent e) {
                    wordLabel.setFont(new Font("Roboto", Font.BOLD, 16));
                }
                public void mouseExited(MouseEvent e) {
                    wordLabel.setFont(GlobalStyles.MAIN_FONT);
                }
            });
            _wordsPanel.add(wordLabel);
        }

        // select active player
        if (_player.isActive()) {
            setBackground(GlobalStyles.SELECTED_ITEM_COLOR);
            rowPack.setBackground(GlobalStyles.SELECTED_ITEM_COLOR);
        } else {
            setBackground(GlobalStyles.SECONDARY_COLOR);
            rowPack.setBackground(GlobalStyles.SECONDARY_COLOR);
        }
    }
}
