package view;

import model.Game;
import view.helpers.GlobalStyles;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class MainWindow extends JFrame {

    private final GridBagConstraints _gbc = new GridBagConstraints();
    private final StartMenuWidget _startMenu = new StartMenuWidget(this);
    private GameWidget _gameWidget = new GameWidget(this);

    public MainWindow() {
        setTitle("Супер Балда");
        setIconImage(new ImageIcon("./img/icon.png").getImage());
        setSize(new Dimension(1120, 760));
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(GlobalStyles.PRIMARY_COLOR);
        setLayout( new GridBagLayout());

        add(_gameWidget, _gbc);
        add(_startMenu, _gbc);

        setVisible(true);
        setLocationRelativeTo(null);
    }

    public void runGame(Game game) {
        _gameWidget.setGame(Objects.requireNonNull(game));
        _gameWidget.setVisible(true);
    }

    public void toStartMenu() {
        _gameWidget.setVisible(false);
        _gameWidget = new GameWidget(this);
        add(_gameWidget, _gbc);
        _startMenu.setVisible(true);
    }
}
