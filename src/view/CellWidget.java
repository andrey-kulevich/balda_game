package view;

import view.helpers.GlobalStyles;
import view.helpers.RoundedBorder;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CellWidget extends JButton implements ActionListener {

    public CellWidget() {
        //super(text);

        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorder(new RoundedBorder(5));
        setOpaque(true);
        setBackground(GlobalStyles.PRIMARY_COLOR);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
