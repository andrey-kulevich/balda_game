package view;

import model.GameField;
import view.helpers.GlobalStyles;
import view.helpers.RoundedPanel;

import java.awt.*;
import java.util.Objects;

public class FieldWidget extends RoundedPanel {

    private final MainWindow _owner;
    private GameField _field;

    public FieldWidget(MainWindow owner) {
        super(10);
        _owner = Objects.requireNonNull(owner);
        setPreferredSize(new Dimension(600, 600));
        setBackground(GlobalStyles.SECONDARY_COLOR);
        setVisible(false);
    }

    public void setField(GameField field) {
        _field = Objects.requireNonNull(field);
        int fieldSize = _field.size();
        setLayout(new GridLayout(fieldSize, fieldSize, 4, 4));
        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                add(new CellWidget(i, j, fieldSize, _field.getCell(i, j).letter()));
            }
        }
        setVisible(true);
    }
}
