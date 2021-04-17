package view;

import model.GameBuilder;
import model.Player;
import view.helpers.CustomActionButton;
import view.helpers.CustomMessageModal;
import view.helpers.GlobalStyles;
import view.helpers.RoundedBorder;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.util.Objects;

public class StartMenuWidget extends JPanel {

    private final JFrame _owner;
    private final GameBuilder _builder = new GameBuilder();

    private final JTextField _firstPlayerName = new JTextField();
    private final JTextField _secondPlayerName = new JTextField();
    private final JComboBox<String> _fieldSizeSelect = new JComboBox<>(new String[]{"5x5", "6x6", "7x7", "8x8", "9x9"});
    private final JCheckBox _jargonDictionary = new JCheckBox("Жаргонные слова");
    private final JCheckBox _russianTownsDictionary = new JCheckBox("Города России");
    private final JCheckBox _slangDictionary = new JCheckBox("Сленговые слова");
    private final CustomMessageModal _errorWindow;

    public StartMenuWidget(JFrame owner) {

        _owner = Objects.requireNonNull(owner);

        JLabel errorMessage = new JLabel("Введите имена игроков!");
        errorMessage.setFont(GlobalStyles.HEADER_FONT);
        _errorWindow = new CustomMessageModal(_owner, errorMessage);
        CustomActionButton cancelButton = new CustomActionButton("ОК");
        cancelButton.addActionListener(e -> _errorWindow.setVisible(false));
        _errorWindow.addButton(cancelButton);

        setPreferredSize(new Dimension(600, 600));
        setBackground(GlobalStyles.SECONDARY_COLOR);
        setLayout(new GridBagLayout());
        setBorder(new RoundedBorder(20));
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(20,20,20,20);

        // text fields
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1.0;
        constraints.gridy = 0;
        constraints.gridx = 0;

        JLabel firstPlayerName = new JLabel("Имя первого игрока");
        firstPlayerName.setFont(GlobalStyles.HEADER_FONT);
        add(firstPlayerName, constraints);
        constraints.gridx = 1;
        JLabel secondPlayerName = new JLabel("Имя второго игрока");
        secondPlayerName.setFont(GlobalStyles.HEADER_FONT);
        add(secondPlayerName, constraints);

        _firstPlayerName.setFont(GlobalStyles.MAIN_FONT);
        _secondPlayerName.setFont(GlobalStyles.MAIN_FONT);
        _firstPlayerName.setBorder(new RoundedBorder(10));
        _secondPlayerName.setBorder(new RoundedBorder(10));
        _firstPlayerName.setBackground(GlobalStyles.SECONDARY_COLOR);
        _secondPlayerName.setBackground(GlobalStyles.SECONDARY_COLOR);
        constraints.gridy = 1;
        constraints.gridx = 0;
        add(_firstPlayerName, constraints);
        constraints.gridx = 1;
        add(_secondPlayerName, constraints);

        // combobox
        constraints.gridy = 2;
        constraints.gridx = 0;
        JLabel fieldSizeLabel = new JLabel("Размер поля");
        fieldSizeLabel.setFont(GlobalStyles.HEADER_FONT);
        add(fieldSizeLabel, constraints);
        constraints.gridy = 3;
        _fieldSizeSelect.setFont(GlobalStyles.MAIN_FONT);
        _fieldSizeSelect.setBackground(GlobalStyles.SECONDARY_COLOR);
        add(_fieldSizeSelect, constraints);

        // additional dictionaries
        JLabel additionalDictionariesLabel = new JLabel("Дополнительные словари");
        additionalDictionariesLabel.setFont(GlobalStyles.HEADER_FONT);
        constraints.gridy = 4;
        add(additionalDictionariesLabel, constraints);

        _jargonDictionary.setFont(GlobalStyles.MAIN_FONT);
        _jargonDictionary.setBackground(GlobalStyles.SECONDARY_COLOR);
        _russianTownsDictionary.setFont(GlobalStyles.MAIN_FONT);
        _russianTownsDictionary.setBackground(GlobalStyles.SECONDARY_COLOR);
        _slangDictionary.setFont(GlobalStyles.MAIN_FONT);
        _slangDictionary.setBackground(GlobalStyles.SECONDARY_COLOR);

        constraints.gridy = 5;
        add(_jargonDictionary, constraints);
        constraints.gridy = 6;
        add(_russianTownsDictionary, constraints);
        constraints.gridy = 7;
        add(_slangDictionary, constraints);

        // start button
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.gridy = 8;

        CustomActionButton _startButton = new CustomActionButton("СОЗДАТЬ");
        _startButton.addActionListener(e -> this.onClickStart());
        add(_startButton, constraints);

        setVisible(true);
    }

    private void onClickStart() {
        String first = _firstPlayerName.getText();
        String second = _secondPlayerName.getText();
        if (second.isEmpty() || first.isEmpty()) {
            _errorWindow.setVisible(true);
        } else {
            _builder.setFirstPlayer(new Player(first));
            _builder.setSecondPlayer(new Player(second));
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

            setVisible(false);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
