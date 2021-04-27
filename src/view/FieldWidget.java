package view;

import model.GameField;
import view.helpers.GlobalStyles;
import view.helpers.RoundedBorder;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class FieldWidget extends JPanel {

    private final MainWindow _owner;
    private GameField _field;

    public FieldWidget(MainWindow owner) {
        _owner = Objects.requireNonNull(owner);
        setPreferredSize(new Dimension(600, 600));
        setBackground(GlobalStyles.SECONDARY_COLOR);
        setBorder(new RoundedBorder(15));
        setVisible(false);
    }

    public void setField(GameField field) {
        _field = Objects.requireNonNull(field);
        int fieldSize = _field.size();
        setLayout(new GridLayout(fieldSize, fieldSize, 5, 5));
        for (int i = 0; i < fieldSize * fieldSize; i++) {
            add(new CellWidget());
        }
        setVisible(true);
    }
}
