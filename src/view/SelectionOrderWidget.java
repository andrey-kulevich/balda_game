package view;

import view.helpers.GlobalStyles;
import view.helpers.RoundedPanel;

import javax.swing.*;
import java.awt.*;

/** Displays the current selected letters in specified order */
public class SelectionOrderWidget extends RoundedPanel {

    private final JLabel _lettersOrder = new JLabel();

    /** Constructor */
    public SelectionOrderWidget() {
        super(10);
        setPreferredSize(new Dimension(600, 30));
        setBackground(GlobalStyles.SECONDARY_COLOR);
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(1,1,1,1);
        constraints.fill = GridBagConstraints.CENTER;
        constraints.weightx = 1.0;
        _lettersOrder.setFont(GlobalStyles.HEADER_FONT);
        add(_lettersOrder, constraints);
        setVisible(true);
    }

    /** Add selected letter to sequence
     *
     * @param letter selected letter
     */
    public void addLetter(Character letter) {
        if (_lettersOrder.getText().isEmpty())
            _lettersOrder.setText(_lettersOrder.getText() + letter);
        else
            _lettersOrder.setText(_lettersOrder.getText() + " > " + letter);
    }

    /** Remove all letters */
    public void clear() { _lettersOrder.setText(""); }
}
