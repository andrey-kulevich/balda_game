package view.helpers;

import javax.swing.*;
import java.awt.*;

/** Customized modal window */
public class CustomMessageModal extends JDialog {

    private final JLabel _text;
    private final JPanel _buttonGroup = new JPanel(new FlowLayout(FlowLayout.RIGHT));

    /** Constructor
     *
     * @param owner parent JFrame
     * @param message text in window
     */
    public CustomMessageModal(JFrame owner, JLabel message) {
        super(owner, "", true);
        setLocation(520, 300);
        setResizable(false);
        setSize(new Dimension(500, 200));
        getContentPane().setBackground(GlobalStyles.PRIMARY_COLOR);

        _text = message;

        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
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

    /** Add button to window
     *
     * @param button button
     */
    public void addButton(JButton button) { _buttonGroup.add(button); }

    /** Change message in modal
     *
     * @param message text
     */
    public void setMessage(String message) { _text.setText(message); }
}
