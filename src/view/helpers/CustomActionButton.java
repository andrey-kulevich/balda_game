package view.helpers;

import javax.swing.*;

public class CustomActionButton extends JButton {
    public CustomActionButton(String text) {
        super(text);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorder(new RoundedBorder(10));
    }
}
