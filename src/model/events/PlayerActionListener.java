package model.events;

import java.util.EventListener;

public interface PlayerActionListener extends EventListener {
    void cellSelected(int col, int row);
    void letterInserted(int col, int row);
    void moveConfirmed();
    void moveSkipped();
    void actionsUndone();
}
