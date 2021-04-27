package view;

import view.helpers.GlobalStyles;
import view.helpers.RoundedBorder;

import javax.swing.*;

public class CellWidget extends JButton {
    public CellWidget() {
        //super(text);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorder(new RoundedBorder(5));
        setOpaque(true);
        setBackground(GlobalStyles.PRIMARY_COLOR);
    }
}
