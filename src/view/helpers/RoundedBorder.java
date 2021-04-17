package view.helpers;

import javax.swing.border.Border;
import java.awt.*;

public class RoundedBorder implements Border {

    private final int radius;

    public RoundedBorder(int radius) { this.radius = radius; }

    public Insets getBorderInsets(Component c) {
        return new Insets(4, 4, 4, 4);
    }

    public boolean isBorderOpaque() { return true; }

    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        g.setColor(Color.decode("#acadae"));
        g.drawRoundRect(x, y, width-1, height-1, radius, radius);
    }
}
