package view;

import model.ComputerPlayer;
import model.GameBuilder;
import model.Player;
import view.helpers.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.io.FileNotFoundException;
import java.util.Objects;

/** Represents the start menu with setting to create new game */
public class StartMenuWidget extends RoundedPanel {

    private final MainWindow _owner;
    private final GameBuilder _builder = new GameBuilder();
    private final JTextField _firstPlayerNameField = new CustomTextField();
    private final JTextField _secondPlayerNameField = new CustomTextField();
    private final JLabel _secondPlayerName = new JLabel("Второй игрок");
    private final JLabel _difficultyLevel = new JLabel("Уровень сложности");
    private final JComboBox<String> _difficultiesSelect = new JComboBox<>(new String[]{"Легкий", "Средний", "Сложный"});
    private final JComboBox<String> _fieldSizeSelect = new JComboBox<>(new String[]{"5x5", "6x6", "7x7", "8x8", "9x9"});
    private final JCheckBox _jargonDictionary = new JCheckBox("Жаргонные слова");
    private final JCheckBox _russianTownsDictionary = new JCheckBox("Города России");
    private final JCheckBox _slangDictionary = new JCheckBox("Сленговые слова");
    private final CustomMessageModal _emptyNamesError;
    private final CustomMessageModal _longNamesError;
    private final CustomMessageModal _equalNamesError;
    private final JCheckBox _isComputerPlayer = new JCheckBox("компьютерный игрок");

    /** Constructor
     *
     * @param owner parent JFrame
     */
    public StartMenuWidget(MainWindow owner) {
        super(10);
        _owner = Objects.requireNonNull(owner);

        // modal windows for player's names validation
        JLabel message1 = new JLabel("Введите имена игроков!");
        message1.setFont(GlobalStyles.HEADER_FONT);
        _emptyNamesError = new CustomMessageModal(_owner, message1);
        CustomActionButton cancelButton1 = new CustomActionButton("ОК");
        cancelButton1.addActionListener(e -> _emptyNamesError.setVisible(false));
        _emptyNamesError.addButton(cancelButton1);

        JLabel message2 = new JLabel("<html>Имена слишком длинные<br>(максимум 20 символов).</html>");
        message2.setFont(GlobalStyles.HEADER_FONT);
        _longNamesError = new CustomMessageModal(_owner, message2);
        CustomActionButton cancelButton2 = new CustomActionButton("ОК");
        cancelButton2.addActionListener(e -> _longNamesError.setVisible(false));
        _longNamesError.addButton(cancelButton2);

        JLabel message3 = new JLabel("Имена не могут быть одинаковыми.");
        message3.setFont(GlobalStyles.HEADER_FONT);
        _equalNamesError = new CustomMessageModal(_owner, message3);
        CustomActionButton cancelButton3 = new CustomActionButton("ОК");
        cancelButton3.addActionListener(e -> _equalNamesError.setVisible(false));
        _equalNamesError.addButton(cancelButton3);

        setPreferredSize(new Dimension(600, 600));
        setBackground(GlobalStyles.SECONDARY_COLOR);
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(15,15,15,15);

        // text fields
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1.0;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;

        JLabel title = new JLabel("НОВАЯ ИГРА", SwingConstants.CENTER);
        title.setFont(GlobalStyles.HEADER_FONT);
        add(title, constraints);

        constraints.gridwidth = 1;
        constraints.gridy = 1;
        JLabel firstPlayerName = new JLabel("Первый игрок");
        firstPlayerName.setFont(GlobalStyles.HEADER_FONT);
        add(firstPlayerName, constraints);

        constraints.gridx = 1;
        add(_firstPlayerNameField, constraints);

        constraints.gridy = 2;
        constraints.gridx = 0;
        _secondPlayerName.setFont(GlobalStyles.HEADER_FONT);
        add(_secondPlayerName, constraints);

        constraints.gridx = 1;
        add(_secondPlayerNameField, constraints);

        constraints.gridy = 2;
        constraints.gridx = 0;
        _difficultyLevel.setFont(GlobalStyles.HEADER_FONT);
        _difficultyLevel.setVisible(false);
        add(_difficultyLevel, constraints);

        constraints.gridx = 1;
        _difficultiesSelect.setVisible(false);
        _difficultiesSelect.setFont(GlobalStyles.MAIN_FONT);
        _difficultiesSelect.setBackground(GlobalStyles.PRIMARY_COLOR);
        add(_difficultiesSelect, constraints);

        constraints.gridx = 0;
        _isComputerPlayer.setFont(GlobalStyles.MAIN_FONT);
        _isComputerPlayer.setBackground(GlobalStyles.SECONDARY_COLOR);
        _isComputerPlayer.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                _difficultiesSelect.setVisible(true);
                _secondPlayerNameField.setVisible(false);
                _difficultyLevel.setVisible(true);
                _secondPlayerName.setVisible(false);
            } else {
                _difficultiesSelect.setVisible(false);
                _secondPlayerNameField.setVisible(true);
                _difficultyLevel.setVisible(false);
                _secondPlayerName.setVisible(true);
            }
        });
        constraints.gridy = 3;
        add(_isComputerPlayer, constraints);

        // combobox
        constraints.gridy = 4;
        JLabel fieldSizeLabel = new JLabel("Размер поля");
        fieldSizeLabel.setFont(GlobalStyles.HEADER_FONT);
        add(fieldSizeLabel, constraints);
        constraints.gridx = 1;
        _fieldSizeSelect.setFont(GlobalStyles.MAIN_FONT);
        _fieldSizeSelect.setBackground(GlobalStyles.PRIMARY_COLOR);
        add(_fieldSizeSelect, constraints);
        constraints.gridx = 0;

        // additional dictionaries
        JLabel additionalDictionariesLabel = new JLabel("Доп. словари");
        additionalDictionariesLabel.setFont(GlobalStyles.HEADER_FONT);
        constraints.gridy = 5;
        add(additionalDictionariesLabel, constraints);

        _jargonDictionary.setFont(GlobalStyles.MAIN_FONT);
        _jargonDictionary.setBackground(GlobalStyles.SECONDARY_COLOR);
        _russianTownsDictionary.setFont(GlobalStyles.MAIN_FONT);
        _russianTownsDictionary.setBackground(GlobalStyles.SECONDARY_COLOR);
        _slangDictionary.setFont(GlobalStyles.MAIN_FONT);
        _slangDictionary.setBackground(GlobalStyles.SECONDARY_COLOR);

        constraints.gridy = 6;
        add(_jargonDictionary, constraints);
        constraints.gridy = 7;
        add(_russianTownsDictionary, constraints);
        constraints.gridy = 8;
        add(_slangDictionary, constraints);

        // start button
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.gridy = 9;

        CustomActionButton _startButton = new CustomActionButton("СОЗДАТЬ");
        _startButton.addActionListener(e -> this.onClickStart());
        add(_startButton, constraints);

        setVisible(true);
    }

    /** Generate the new game with specified settings */
    private void onClickStart() {
        String first = _firstPlayerNameField.getText();
        String second = _secondPlayerNameField.getText();
        if ((second.isEmpty() && !_isComputerPlayer.isSelected()) || first.isEmpty()) {
            _emptyNamesError.setVisible(true);
        } else if (first.length() > 20 || second.length() > 20) {
            _longNamesError.setVisible(true);
        } else if (first.equals(second)) {
            _equalNamesError.setVisible(true);
        } else {
            _builder.setFirstPlayer(new Player(first));
            if (_isComputerPlayer.isSelected()) {
                ComputerPlayer.Intellect intellect;
                switch(Objects.requireNonNull(_difficultiesSelect.getSelectedItem()).toString()) {
                    case "Легкий" -> intellect = ComputerPlayer.Intellect.EASY;
                    case "Средний" -> intellect = ComputerPlayer.Intellect.MEDIUM;
                    case "Сложный" -> intellect = ComputerPlayer.Intellect.HARD;
                    default -> throw new IllegalStateException("Unexpected value");
                }
                _builder.setSecondPlayer(new ComputerPlayer("Компьютер", intellect));
            } else {
                _builder.setSecondPlayer(new Player(second));
            }
            _builder.setFieldSize(Character.getNumericValue(
                    Objects.requireNonNull(_fieldSizeSelect.getSelectedItem()).toString().charAt(0)));
            try {
                _builder.addDictionary("./dictionaries/russianNounsWithDefinition.txt");
                _builder.addDictionary("./dictionaries/customDictionary.txt");
                if (_jargonDictionary.isSelected())
                    _builder.addDictionary("./dictionaries/jargon.txt");
                if (_russianTownsDictionary.isSelected())
                    _builder.addDictionary("./dictionaries/russianCities.txt");
                if (_slangDictionary.isSelected())
                    _builder.addDictionary("./dictionaries/russianYoungSlang.txt");
            } catch (FileNotFoundException ignored) { }

            _builder.dictionary().setModifiableDictionary("./dictionaries/customDictionary.txt");
            _owner.runGame(_builder.initGame());

            setVisible(false);
        }
    }
}
