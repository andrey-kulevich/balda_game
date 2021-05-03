package view;

import model.Cell;
import model.GameField;
import view.helpers.GlobalStyles;
import view.helpers.RoundedPanel;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;

public class FieldWidget extends RoundedPanel {

    private final GameWidget _owner;
    private CellWidget[][] _cells;

    public FieldWidget(GameWidget owner) {
        super(10);
        _owner = Objects.requireNonNull(owner);
        setPreferredSize(new Dimension(600, 600));
        setBackground(GlobalStyles.SECONDARY_COLOR);
        setVisible(false);
    }

    public void initField() {
        GameField field = _owner.getGame().field();
        _cells = new CellWidget[field.size()][field.size()];
        int fieldSize = field.size();
        setLayout(new GridLayout(fieldSize, fieldSize, 4, 4));
        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                CellWidget cell = new CellWidget(fieldSize, field.getCell(i, j).letter());
                int finalI = i;
                int finalJ = j;
                cell.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        if (_owner.getGame().activePlayer().selectCell(finalI, finalJ))
                            cell.setSelection(field.getCell(finalI, finalJ).selectionState());
                    }
                });
                cell.addKeyListener(new KeyListener() {
                    public void keyPressed(KeyEvent e) {
                        if (_owner.getGame().activePlayer().writeToSelectedCell(e.getKeyChar()))
                            cell.setLetter(field.getCell(finalI, finalJ).letter());
                    }
                    public void keyTyped(KeyEvent e) { }
                    public void keyReleased(KeyEvent e) { }
                });
                _cells[i][j] = cell;
                add(cell);
            }
        }
        setVisible(true);
    }

    public void update() {
        GameField field = _owner.getGame().field();
        for (int i = 0; i < field.size(); i++) {
            for (int j = 0; j < field.size(); j++) {
                Cell cell = field.getCell(i, j);
                _cells[i][j].setLetter(cell.letter());
                _cells[i][j].setSelection(cell.selectionState());
            }
        }
    }
}
