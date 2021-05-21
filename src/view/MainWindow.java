package view;

import model.Game;
import view.helpers.CustomActionButton;
import view.helpers.CustomMessageModal;
import view.helpers.GlobalStyles;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Objects;

/** Main view frame */
public class MainWindow extends JFrame {

    private final GridBagConstraints _gbc = new GridBagConstraints();
    private final StartMenuWidget _startMenu = new StartMenuWidget(this);
    private GameWidget _gameWidget = new GameWidget(this);
    private final CustomMessageModal _saveAddedWordsWarning;

    /** constructor */
    public MainWindow() {
        //define main settings
        setTitle("Супер Балда");
        setIconImage(new ImageIcon("./img/icon.png").getImage());
        setSize(new Dimension(1120, 760));
        setResizable(false);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        // modal window on frame close
        JLabel message = new JLabel("Сохранить добавленные слова?");
        message.setFont(GlobalStyles.HEADER_FONT);
        _saveAddedWordsWarning = new CustomMessageModal(null, message);
        CustomActionButton cancelButton = new CustomActionButton("ОТМЕНА");
        cancelButton.addActionListener(e -> _saveAddedWordsWarning.setVisible(false));
        _saveAddedWordsWarning.addButton(cancelButton);
        CustomActionButton notSaveButton = new CustomActionButton("НЕТ");
        notSaveButton.addActionListener(e -> {
            _saveAddedWordsWarning.setVisible(false);
            System.exit(0);
        });
        _saveAddedWordsWarning.addButton(notSaveButton);
        CustomActionButton confirmButton = new CustomActionButton("ДА");
        confirmButton.addActionListener(e -> {
            try { _gameWidget.getGame().dictionary().saveAddedWords(); }
            catch (IOException ioException) { ioException.printStackTrace(); }
            _saveAddedWordsWarning.setVisible(false);
            System.exit(0);
        });
        _saveAddedWordsWarning.addButton(confirmButton);

        // open modal window on frame close
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                if (_gameWidget.getGame() == null) System.exit(0);
                else _saveAddedWordsWarning.setVisible(true);
            }
        });

        // add widgets to layout
        getContentPane().setBackground(GlobalStyles.PRIMARY_COLOR);
        setLayout( new GridBagLayout());

        add(_gameWidget, _gbc);
        add(_startMenu, _gbc);

        setVisible(true);
        setLocationRelativeTo(null);
    }

    /** Start game with specified settings
     *
     * @param game game model
     */
    public void runGame(Game game) {
        game.firstPlayer().addListener(_gameWidget);
        game.secondPlayer().addListener(_gameWidget);
        _gameWidget.setGame(Objects.requireNonNull(game));
        _gameWidget.setVisible(true);
    }

    /** Exit to start menu and destroy the current game */
    public void toStartMenu() {
        _gameWidget.setVisible(false);
        _gameWidget = new GameWidget(this);
        add(_gameWidget, _gbc);
        _startMenu.setVisible(true);
    }
}
