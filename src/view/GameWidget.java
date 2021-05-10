package view;


import model.Game;
import view.helpers.CustomActionButton;
import view.helpers.CustomMessageModal;
import view.helpers.GlobalStyles;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class GameWidget extends JPanel {

    private final MainWindow _owner;
    private Game _game;
    private final FieldWidget _field;
    private final PlayerWidget _firstPlayer;
    private final PlayerWidget _secondPlayer;

    private final CustomMessageModal _wordDoesNotExistError;
//    private final CustomMessageModal _currentLetterDoesNotSelectedError;
//    private final CustomMessageModal _wordHadDiscoveredEarlierError;
//    private final CustomMessageModal _gameIsOver;
//    private final CustomMessageModal _skipMoveWarning;
//    private final CustomMessageModal _goToStartMenuWarning;

    public GameWidget(MainWindow owner) {
        _owner = Objects.requireNonNull(owner);

        // create modal windows
        JLabel message1 = new JLabel(
                    "<html>" +
                            "<div style='width: 450; text-align: center;'>" +
                                "Такое слово нет в доступных словарях.<br>Добавить его в словарь?" +
                            "</div>" +
                        "<html>");
        message1.setFont(GlobalStyles.HEADER_FONT);
        _wordDoesNotExistError = new CustomMessageModal(_owner, message1);
        CustomActionButton cancelButton1 = new CustomActionButton("Отмена");
        cancelButton1.addActionListener(e -> _wordDoesNotExistError.setVisible(false));
        _wordDoesNotExistError.addButton(cancelButton1);
        CustomActionButton confirmButton1 = new CustomActionButton("Да");
        confirmButton1.addActionListener(e -> _wordDoesNotExistError.setVisible(false));
        _wordDoesNotExistError.addButton(confirmButton1);



        // create main widgets
        _field = new FieldWidget(this);
        ControlPanelWidget _controlPanel = new ControlPanelWidget(this);
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

    public void confirmMove() {
        switch (_game.activePlayer().confirmMove()) {
            case SUCCESS:
                _firstPlayer.update();
                _secondPlayer.update();
                _field.update();
                break;
            case CURRENT_LETTER_DOES_NOT_SELECTED:

                break;
            case WORD_DOES_NOT_EXIST:
                _wordDoesNotExistError.setVisible(true);
                break;
            case WORD_HAD_DISCOVERED_EARLIER:

                break;
            case GAME_IS_OVER:

                break;
        }
    }

    public void undoCurrentActions() {
        _game.activePlayer().undoCurrentActions();
        _field.update();
    }

    public void skipMove() {
        _game.activePlayer().skipMove();
        _firstPlayer.update();
        _secondPlayer.update();
        _field.update();
    }
}
