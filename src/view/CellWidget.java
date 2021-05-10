package view;

import model.Cell;
import view.helpers.CustomActionButton;
import view.helpers.GlobalStyles;

import java.awt.*;
import java.util.Objects;

public class CellWidget extends CustomActionButton {

    public CellWidget(int fieldSize, Character initLetter) {
        super();
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

        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (!getBackground().equals(Color.decode("#D0D0D0")) &&
                        !getBackground().equals(GlobalStyles.SELECTED_ITEM_COLOR))
                    setBackground(GlobalStyles.SECONDARY_COLOR);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (!getBackground().equals(Color.decode("#D0D0D0")) &&
                        !getBackground().equals(GlobalStyles.SELECTED_ITEM_COLOR))
                    setBackground(GlobalStyles.PRIMARY_COLOR);
            }
        });
    }

    public void setSelection(Cell.SelectionState state) {
        switch (state) {
            case NOT_SELECTED -> setBackground(GlobalStyles.PRIMARY_COLOR);
            case SELECTED_TO_WRITE_LETTER -> setBackground(Color.decode("#D0D0D0"));
            case SELECTED_TO_CREATE_WORD -> setBackground(GlobalStyles.SELECTED_ITEM_COLOR);
        }
    }

    public void setLetter(Character letter) {
        setText(Objects.requireNonNull(letter).toString());
    }
}
