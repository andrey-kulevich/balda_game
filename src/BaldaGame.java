import model.*;
import view.MainWindow;

import javax.swing.*;

public class BaldaGame extends JFrame {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainWindow window = new MainWindow();
        });
    }
}
