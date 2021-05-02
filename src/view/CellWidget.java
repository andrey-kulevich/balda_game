package view;

import model.Cell;
import view.helpers.CustomActionButton;

import java.awt.*;
import java.util.Objects;

public class CellWidget extends CustomActionButton {

    private final int _row;
    private final int _col;

    public CellWidget(int row, int col, int fieldSize, Character initLetter) {
        super("");
        if (row < 0 || col < 0) throw new IllegalArgumentException("Invalid indexes");
        _row = row;
        _col = col;
        int fontSize;
        switch (fieldSize) {
            case 5 -> fontSize = 60;
            case 6 -> fontSize = 55;
            case 7 -> fontSize = 45;
            case 8 -> fontSize = 35;
            default -> fontSize = 30;
        }
        setFont(new Font("Roboto", Font.PLAIN, fontSize));
        this.setLetter(initLetter);
    }

    public void setSelection(Cell.SelectionState state) {
        switch (state) {
            case NOT_SELECTED -> setBackground(Color.WHITE);
            case SELECTED_TO_WRITE_LETTER -> setBackground(Color.decode("#D0D0D0"));
            case SELECTED_TO_CREATE_WORD -> setBackground(Color.decode("#B8B8B8"));
        }
    }

    public void setLetter(Character letter) {
        setText(Objects.requireNonNull(letter).toString());
    }
}
