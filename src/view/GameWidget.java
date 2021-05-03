package view;


import model.Game;
import model.events.PlayerActionListener;
import view.helpers.GlobalStyles;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class GameWidget extends JPanel implements PlayerActionListener {

    private Game _game;
    private final MainWindow _owner;
    private final FieldWidget _field;
    private final ControlPanelWidget _controlPanel;
    private final PlayerWidget _firstPlayer;
    private final PlayerWidget _secondPlayer;

    public GameWidget(MainWindow owner) {
        _owner = Objects.requireNonNull(owner);
        _field = new FieldWidget(this);
        _controlPanel = new ControlPanelWidget(this);
        _firstPlayer = new PlayerWidget(this, PlayerWidget.Orientation.LEFT);
        _secondPlayer = new PlayerWidget(this, PlayerWidget.Orientation.RIGHT);
        setPreferredSize(new Dimension(1080, 660));
        setBackground(GlobalStyles.PRIMARY_COLOR);

        setLayout(new BorderLayout(10, 10));

        add(_firstPlayer, BorderLayout.WEST);

        JPanel verticalPack = new JPanel(new BorderLayout(10, 10));
        verticalPack.setBackground(GlobalStyles.PRIMARY_COLOR);
        verticalPack.setLayout(new BorderLayout());
        verticalPack.add(_field, BorderLayout.NORTH);
        verticalPack.add(_controlPanel, BorderLayout.SOUTH);

        add(verticalPack, BorderLayout.CENTER);
        add(_secondPlayer, BorderLayout.EAST);

        setVisible(false);
    }

    public Game getGame() { return _game; }

    public void setGame(Game game) {
        _game = Objects.requireNonNull(game);
        _field.initField();
        game.firstPlayer().addWord("ложка");
        game.firstPlayer().addWord("душа");
        game.firstPlayer().addWord("жизнь");
        _firstPlayer.setPlayer(game.firstPlayer());
        _secondPlayer.setPlayer(game.secondPlayer());
        setVisible(true);
    }

    @Override
    public void cellSelected(int col, int row) {
        // update specified cell and top panel with letters chain
    }

    @Override
    public void moveCommitted() {
        _secondPlayer.update();
        _firstPlayer.update();
        _field.update();
        // clear and remove top panel with letters chain
    }

    @Override
    public void actionsUndone() {
        _field.update();
    }
}
