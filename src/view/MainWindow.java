package view;

import model.Game;
import view.helpers.GlobalStyles;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class MainWindow extends JFrame {

    private StartMenuWidget _startMenu = new StartMenuWidget(this);
    private GameWidget _gameWidget = new GameWidget(this);

    public MainWindow() {
        setTitle("Супер Балда");
        setIconImage(new ImageIcon("./img/icon.png").getImage());
        setSize(new Dimension(1120, 720));
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(GlobalStyles.PRIMARY_COLOR);

        setLayout( new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        add(_gameWidget, gbc);
        add(_startMenu, gbc);

        setVisible(true);
        setLocationRelativeTo(null);
    }

    public void runGame(Game game) {
        _gameWidget.setGame(Objects.requireNonNull(game));
        _gameWidget.setVisible(true);
    }

    public void toStartMenu() {
        _gameWidget = null;
        _startMenu.setVisible(true);
    }
}
