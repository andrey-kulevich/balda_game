package view.helpers;

import javax.swing.*;
import java.awt.*;

public class CustomTextField extends JTextField {

    private Dimension arcs = new Dimension(10, 10);

    public CustomTextField() {
        setFont(GlobalStyles.MAIN_FONT);
        setBorder(new RoundedBorder(10));
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
