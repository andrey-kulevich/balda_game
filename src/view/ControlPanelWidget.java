package view;

import view.helpers.CustomActionButton;
import view.helpers.GlobalStyles;
import view.helpers.RoundedPanel;

import java.awt.*;
import java.util.Objects;

/** Panel with control buttons */
public class ControlPanelWidget extends RoundedPanel {

    private final GameWidget _owner;

    /** Constructor
     *
     * @param owner parent game widget
     */
    public ControlPanelWidget(GameWidget owner) {
        super(10);
        _owner = Objects.requireNonNull(owner);

        setPreferredSize(new Dimension(600, 50));
        setBackground(GlobalStyles.SECONDARY_COLOR);

        CustomActionButton skipButton = new CustomActionButton("ПРОПУСТИТЬ");
        CustomActionButton cancelButton = new CustomActionButton("ОТМЕНИТЬ");
        CustomActionButton confirmButton = new CustomActionButton("ПОДТВЕРДИТЬ");

        skipButton.addActionListener(e -> _owner.skipMove());
        cancelButton.addActionListener(e -> _owner.getGame().activePlayer().undoCurrentActions());
        confirmButton.addActionListener(e -> _owner.getGame().activePlayer().confirmMove());

        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(0, 2, 0, 2);
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        constraints.fill = GridBagConstraints.BOTH;

        add(skipButton, constraints);
        add(cancelButton, constraints);
        add(confirmButton, constraints);
    }
}
