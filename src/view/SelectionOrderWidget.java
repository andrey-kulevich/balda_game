package view;

import view.helpers.GlobalStyles;
import view.helpers.RoundedPanel;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class SelectionOrderWidget extends RoundedPanel {

    private final GameWidget _owner;
    private final JLabel _lettersOrder = new JLabel();

    public SelectionOrderWidget(GameWidget owner) {
        super(10);
        _owner = Objects.requireNonNull(owner);
        setPreferredSize(new Dimension(600, 50));
        setBackground(GlobalStyles.SECONDARY_COLOR);
        add(_lettersOrder, BorderLayout.CENTER);
        setVisible(true);
    }

    public void addLetter(Character letter) {
        _lettersOrder.setText(_lettersOrder.getText() + letter + " -> ");
    }

    public void clear() {
        _lettersOrder.setText("");
        setVisible(false);
    }
}
