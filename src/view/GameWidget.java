package view;


import model.Game;
import view.helpers.GlobalStyles;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class GameWidget extends JPanel {

    private final MainWindow _owner;
    private final FieldWidget _field;
    private final ControlPanelWidget _controlPanel;
    private final PlayerWidget _firstPlayer;
    private final PlayerWidget _secondPlayer;

    public GameWidget(MainWindow owner) {
        _owner = Objects.requireNonNull(owner);
        _field = new FieldWidget(owner);
        _controlPanel = new ControlPanelWidget(owner);
        _firstPlayer = new PlayerWidget(owner, PlayerWidget.Orientation.LEFT);
        _secondPlayer = new PlayerWidget(owner, PlayerWidget.Orientation.RIGHT);
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

    public void setGame(Game game) {
        _field.setField(game.field());
        _firstPlayer.setPlayer(game.firstPlayer());
        _secondPlayer.setPlayer(game.secondPlayer());
        setVisible(true);
    }
}
