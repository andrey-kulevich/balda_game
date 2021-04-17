package view.helpers;

import javax.swing.*;
import java.awt.*;

public class CustomMessageModal extends JDialog {

    private final GridBagConstraints constraints = new GridBagConstraints();

    public CustomMessageModal(JFrame owner, JLabel message) {
        super(owner, "", true);
        setLocation(520, 300);
        setResizable(false);
        setSize(new Dimension(500, 200));
        getContentPane().setBackground(GlobalStyles.PRIMARY_COLOR);

        setLayout(new GridBagLayout());
        constraints.insets = new Insets(20,20,20,20);
        constraints.weightx = 1.0;
        constraints.fill = GridBagConstraints.CENTER;
        constraints.gridwidth = 3;
        constraints.gridy = 0;
        constraints.gridx = 0;
        add(message, constraints);
    }

    public void addButton(JButton button) {
        constraints.fill = GridBagConstraints.PAGE_END;
        constraints.weighty = 1.0;
        constraints.ipadx = 40;
        constraints.gridy = 1;
        constraints.gridx = 1;
        constraints.anchor = GridBagConstraints.SOUTHEAST;
        add(button, constraints);
    }
}
