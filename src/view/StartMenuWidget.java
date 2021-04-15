package view;

import model.GameBuilder;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class StartMenuWidget extends JPanel {
    private GameBuilder _builder;

    public StartMenuWidget() {
        _builder = new GameBuilder();
        setPreferredSize(new Dimension(600, 600));
        //setBackground(GlobalStyles.PRIMARY_COLOR);
        //setBorder(new LineBorder(Color.DARK_GRAY, 1, true));
        setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Dimension arcs = new Dimension(GlobalStyles.BORDER_RADIUS, GlobalStyles.BORDER_RADIUS);
        int width = getWidth();
        int height = getHeight();
        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        //Draws the rounded panel with borders.
        graphics.setColor(GlobalStyles.PRIMARY_COLOR);
        graphics.fillRoundRect(0, 0, width-1, height-1, arcs.width, arcs.height);
        graphics.setColor(GlobalStyles.BORDER_COLOR);
        graphics.drawRoundRect(0, 0, width-1, height-1, arcs.width, arcs.height);
    }
}
