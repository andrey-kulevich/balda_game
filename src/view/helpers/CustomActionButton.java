package view.helpers;

import javax.swing.*;
import java.awt.*;

/** Customized button */
public class CustomActionButton extends JButton {

    private final Dimension arcs = new Dimension(10, 10);

    /** Create button with text and predefined behavior
     *
     * @param text button's text
     */
    public CustomActionButton(String text) {
        super(text);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setOpaque(false);
        setBackground(GlobalStyles.PRIMARY_COLOR);
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                setBackground(GlobalStyles.SECONDARY_COLOR);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                setBackground(GlobalStyles.PRIMARY_COLOR);
            }
        });
    }

    /** Create empty button without any behavior */
    public CustomActionButton() {
        super("");
        setContentAreaFilled(false);
        setFocusPainted(false);
        setOpaque(false);
        setBackground(GlobalStyles.PRIMARY_COLOR);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int width = getWidth();
        int height = getHeight();
        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setColor(getBackground());
        graphics.fillRoundRect(0, 0, width-1, height-1, arcs.width, arcs.height);
    }

    @Override
    protected void paintBorder(Graphics g) {
        super.paintComponent(g);
        int width = getWidth();
        int height = getHeight();
        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setColor(Color.decode("#acadae"));
        graphics.drawRoundRect(0, 0, width-1, height-1, arcs.width, arcs.height);
    }
}
