package view;

import view.helpers.GlobalStyles;
import view.helpers.RoundedPanel;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class SelectionFlowWidget extends RoundedPanel {

    private final GameWidget _owner;

    public SelectionFlowWidget(GameWidget owner) {
        super(10);
        _owner = Objects.requireNonNull(owner);
        setPreferredSize(new Dimension(600, 600));
        setBackground(GlobalStyles.SECONDARY_COLOR);
        setVisible(false);
    }

}
