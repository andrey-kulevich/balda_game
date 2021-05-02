package view.helpers;

import javax.swing.*;
import java.awt.*;

public class RoundedPanel extends JPanel {

    private Color backgroundColor;
    private final int cornerRadius;

    public RoundedPanel(LayoutManager layout, int radius) {
        super(layout);
        cornerRadius = radius;
        setBorder(BorderFactory.createEmptyBorder(7, 7, 7, 7));
        setOpaque(false);
    }

    public RoundedPanel(LayoutManager layout, int radius, Color bgColor) {
        super(layout);
        cornerRadius = radius;
        backgroundColor = bgColor;
        setBorder(BorderFactory.createEmptyBorder(7, 7, 7, 7));
        setOpaque(false);
    }

    public RoundedPanel(int radius) {
        super();
        cornerRadius = radius;
        setBorder(BorderFactory.createEmptyBorder(7, 7, 7, 7));
        setOpaque(false);
    }

    public RoundedPanel(int radius, Color bgColor) {
        super();
        cornerRadius = radius;
        backgroundColor = bgColor;
        setBorder(BorderFactory.createEmptyBorder(7, 7, 7, 7));
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Dimension arcs = new Dimension(cornerRadius, cornerRadius);
        int width = getWidth();
        int height = getHeight();
        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (backgroundColor != null) graphics.setColor(backgroundColor);
        else graphics.setColor(getBackground());

        graphics.fillRoundRect(0, 0, width-1, height-1, arcs.width, arcs.height);
        graphics.setColor(Color.decode("#acadae"));
        graphics.drawRoundRect(0, 0, width-1, height-1, arcs.width, arcs.height);
    }
}
