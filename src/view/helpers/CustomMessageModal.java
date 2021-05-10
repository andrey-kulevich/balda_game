package view.helpers;

import javax.swing.*;
import java.awt.*;

public class CustomMessageModal extends JDialog {

    private final GridBagConstraints constraints = new GridBagConstraints();
    private final JLabel _text;
    private final JPanel _buttonGroup = new JPanel(new FlowLayout(FlowLayout.RIGHT));

    public CustomMessageModal(JFrame owner, JLabel message) {
        super(owner, "", true);
        setLocation(520, 300);
        setResizable(false);
        setSize(new Dimension(500, 200));
        getContentPane().setBackground(GlobalStyles.PRIMARY_COLOR);

        _text = message;

        setLayout(new GridBagLayout());
        constraints.insets = new Insets(20,20,20,20);
        constraints.weightx = 1.0;
        constraints.fill = GridBagConstraints.CENTER;
        constraints.gridwidth = 3;
        constraints.gridy = 0;
        constraints.gridx = 0;
        add(message, constraints);

        constraints.weighty = 1.0;
        constraints.gridy = 1;
        constraints.gridx = 1;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.SOUTHEAST;
        _buttonGroup.setBackground(GlobalStyles.PRIMARY_COLOR);
        add(_buttonGroup, constraints);
    }

    public void addButton(JButton button) { _buttonGroup.add(button); }

    public void setMessage(String message) { _text.setText(message); }
}
