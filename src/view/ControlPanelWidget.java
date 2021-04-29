package view;

import view.helpers.CustomActionButton;
import view.helpers.GlobalStyles;
import view.helpers.RoundedBorder;
import view.helpers.RoundedPanel;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class ControlPanelWidget extends RoundedPanel {

    private final MainWindow _owner;
    private CustomActionButton _skipButton = new CustomActionButton("ПРОПУСТИТЬ");
    private CustomActionButton _cancelButton = new CustomActionButton("ОТМЕНИТЬ");
    private CustomActionButton _confirmButton = new CustomActionButton("ПОДТВЕРДИТЬ");

    public ControlPanelWidget(MainWindow owner) {
        super(10);
        _owner = Objects.requireNonNull(owner);

        setPreferredSize(new Dimension(600, 50));
        setBackground(GlobalStyles.SECONDARY_COLOR);

        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(0, 2, 0, 2);
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        constraints.fill = GridBagConstraints.BOTH;

        add(_skipButton, constraints);
        add(_cancelButton, constraints);
        add(_confirmButton, constraints);
    }
}
