package view;

import view.helpers.GlobalStyles;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {

    private StartMenuWidget _startMenu = new StartMenuWidget(this);

    public MainWindow() {
        setTitle("Супер Балда");
        setIconImage(new ImageIcon("./img/icon.png").getImage());
        setSize(new Dimension(1120, 720));
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(GlobalStyles.PRIMARY_COLOR);

        setLayout( new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        add(_startMenu, gbc);

        setVisible(true);
        setLocationRelativeTo(null);
    }

//    private class RepaintObserver implements GameListener {
//        @Override
//        public void repaint() {
//            fieldView.repaint();
//            panelView.repaint();
//        }
//    }
}
