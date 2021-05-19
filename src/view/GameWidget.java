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
    private final SelectionOrderWidget _selectionOrder;

    private final CustomMessageModal _wordDoesNotExistError;
    private final CustomMessageModal _currentLetterDoesNotSelectedError;
    private final CustomMessageModal _wordHadDiscoveredEarlierError;
    private final CustomMessageModal _gameIsOver;
    private final CustomMessageModal _skipMoveWarning;
    private final CustomMessageModal _goToStartMenuWarning;
    private final CustomMessageModal _aboutGame;

    public GameWidget(MainWindow owner) {
        _owner = Objects.requireNonNull(owner);

        // create main widgets
        _field = new FieldWidget(this);
        ControlPanelWidget _controlPanel = new ControlPanelWidget(this);
        _firstPlayer = new PlayerWidget(this, PlayerWidget.Orientation.LEFT);
        _secondPlayer = new PlayerWidget(this, PlayerWidget.Orientation.RIGHT);

        setPreferredSize(new Dimension(1080, 700));
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

        JLabel message6 = new JLabel(
                "<html>" +
                        "<div style='width: 500; text-align: center;'>" +
                            "Выйти в главное меню?<br>Весь текущий прогресс будет утерян." +
                        "</div>" +
                    "<html>");
        message6.setFont(GlobalStyles.HEADER_FONT);
        _goToStartMenuWarning = new CustomMessageModal(_owner, message6);

        JPanel topBar = new JPanel(new BorderLayout(10, 10));
        topBar.setBackground(GlobalStyles.PRIMARY_COLOR);
        CustomActionButton toMainMenuButton = new CustomActionButton("В МЕНЮ");
        toMainMenuButton.setPreferredSize(new Dimension(220, 30));
        toMainMenuButton.addActionListener(e -> _goToStartMenuWarning.setVisible(true));
        CustomActionButton aboutGameButton = new CustomActionButton("ОБ ИГРЕ");
        aboutGameButton.setPreferredSize(new Dimension(220, 30));

        JLabel message7 = new JLabel(
                "<html>" +
                        "<div style='width: 450;'>" +
                            "<h2 style='text-align: center;'>Правила игры</h2>" +
                            "<p>В начале игры есть поле из клеток, посреди которого написано слово. " +
                            "Игроки по очереди выбирают одну букву из алфавита и располагают её на поле таким образом, " +
                            "чтобы получилось новое слово (игрок должен самостоятельно задать слово). " +
                            "Добавленная буква обязательно должна входить в новое слово. " +
                            "Игрок может пропустить ход. За каждое слово игрок получает некоторое количество очков, " +
                            "равное числу букв в слове. Побеждает игрок, набравший наибольшее количество очков. " +
                            "Правильность слов оценивается согласно словарю.</p> <br>" +
                            "Автор приложения: " +
                            "<a href='#'>https://github.com/andrey-kulevich</a>" +
                        "</div>" +
                    "<html>");
        message7.setFont(GlobalStyles.MAIN_FONT);
        _aboutGame = new CustomMessageModal(_owner, message7);
        _aboutGame.setLocation(520, 100);

        aboutGameButton.addActionListener(e -> _aboutGame.setVisible(true));
        _selectionOrder = new SelectionOrderWidget();
        topBar.add(toMainMenuButton, BorderLayout.WEST);
        topBar.add(_selectionOrder, BorderLayout.CENTER);
        topBar.add(aboutGameButton, BorderLayout.EAST);
        add(topBar, BorderLayout.NORTH);

        // create modal windows
        JLabel message1 = new JLabel(
                    "<html>" +
                            "<div style='width: 450; text-align: center;'>" +
                                "Такое слово нет в доступных словарях.<br>Добавить его в словарь?" +
                            "</div>" +
                        "<html>");
        message1.setFont(GlobalStyles.HEADER_FONT);
        _wordDoesNotExistError = new CustomMessageModal(_owner, message1);
        CustomActionButton cancelButton1 = new CustomActionButton("ОТМЕНА");
        cancelButton1.addActionListener(e -> _wordDoesNotExistError.setVisible(false));
        _wordDoesNotExistError.addButton(cancelButton1);
        CustomActionButton confirmButton1 = new CustomActionButton("ДА");
        confirmButton1.addActionListener(e -> {
            _game.activePlayer().addWordToDictionary(_game.field().getSelectedWord(), " ");
            this.confirmMove();
            _wordDoesNotExistError.setVisible(false);
        });
        _wordDoesNotExistError.addButton(confirmButton1);

        JLabel message2 = new JLabel(
                    "<html>" +
                            "<div style='width: 500; text-align: center;'>" +
                                "Ошибка!<br>В вашем слове нет буквы, добавленной в этом ходу." +
                            "</div>" +
                        "<html>");
        message2.setFont(GlobalStyles.HEADER_FONT);
        _currentLetterDoesNotSelectedError = new CustomMessageModal(_owner, message2);
        _currentLetterDoesNotSelectedError.setSize(new Dimension(550, 200));
        CustomActionButton confirmButton2 = new CustomActionButton("ОК");
        confirmButton2.addActionListener(e -> _currentLetterDoesNotSelectedError.setVisible(false));
        _currentLetterDoesNotSelectedError.addButton(confirmButton2);

        JLabel message3 = new JLabel("Это слово уже было отгадано ранее.");
        message3.setFont(GlobalStyles.HEADER_FONT);
        _wordHadDiscoveredEarlierError = new CustomMessageModal(_owner, message3);
        CustomActionButton confirmButton3 = new CustomActionButton("ОК");
        confirmButton3.addActionListener(e -> _wordHadDiscoveredEarlierError.setVisible(false));
        _wordHadDiscoveredEarlierError.addButton(confirmButton3);

        JLabel message4 = new JLabel("ИГРА ОКОНЧЕНА!");
        message4.setFont(GlobalStyles.HEADER_FONT);
        _gameIsOver = new CustomMessageModal(_owner, message4);
        _gameIsOver.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        CustomActionButton confirmButton4 = new CustomActionButton("В МЕНЮ");
        confirmButton4.addActionListener(e -> _owner.toStartMenu());
        _gameIsOver.addButton(confirmButton4);

        JLabel message5 = new JLabel("Вы уверены, что хотите пропустить ход?");
        message5.setFont(GlobalStyles.HEADER_FONT);
        _skipMoveWarning = new CustomMessageModal(_owner, message5);
        CustomActionButton cancelButton5 = new CustomActionButton("ОТМЕНА");
        cancelButton5.addActionListener(e -> _skipMoveWarning.setVisible(false));
        _skipMoveWarning.addButton(cancelButton5);
        CustomActionButton confirmButton5 = new CustomActionButton("ДА");
        confirmButton5.addActionListener(e -> {
            _game.activePlayer().skipMove();
            _firstPlayer.update();
            _secondPlayer.update();
            _field.update();
            _skipMoveWarning.setVisible(false);
        });
        _skipMoveWarning.addButton(confirmButton5);

        CustomActionButton cancelButton6 = new CustomActionButton("ОТМЕНА");
        cancelButton6.addActionListener(e -> _goToStartMenuWarning.setVisible(false));
        _goToStartMenuWarning.addButton(cancelButton6);
        CustomActionButton confirmButton6 = new CustomActionButton("ДА");
        confirmButton6.addActionListener(e -> {
            _owner.toStartMenu();
            _goToStartMenuWarning.setVisible(false);
        });
        _goToStartMenuWarning.addButton(confirmButton6);

        _aboutGame.setSize(new Dimension(500, 500));
        CustomActionButton confirmButton7 = new CustomActionButton("ОК");
        confirmButton7.addActionListener(e -> _aboutGame.setVisible(false));
        _aboutGame.addButton(confirmButton7);

        setVisible(false);
    }

    public Game getGame() { return _game; }

    public void setGame(Game game) {
        _game = Objects.requireNonNull(game);
        _field.initField();
        _firstPlayer.setPlayer(game.firstPlayer());
        _secondPlayer.setPlayer(game.secondPlayer());
        setVisible(true);
    }

    public void confirmMove() {
        switch (_game.activePlayer().confirmMove()) {
            case SUCCESS -> {
                _firstPlayer.update();
                _secondPlayer.update();
                _field.update();
                _selectionOrder.clear();
            }
            case CURRENT_LETTER_DOES_NOT_SELECTED -> _currentLetterDoesNotSelectedError.setVisible(true);
            case WORD_DOES_NOT_EXIST -> _wordDoesNotExistError.setVisible(true);
            case WORD_HAD_DISCOVERED_EARLIER -> _wordHadDiscoveredEarlierError.setVisible(true);
            case GAME_IS_OVER -> _gameIsOver.setVisible(true);
        }
    }

    public void undoCurrentActions() {
        _game.activePlayer().undoCurrentActions();
        _field.update();
        _selectionOrder.clear();
    }

    public void skipMove() {
        _skipMoveWarning.setVisible(true);
        _selectionOrder.clear();
    }

    public void extendSelectionOrder(Character letter) {
        _selectionOrder.addLetter(letter);
    }
}
