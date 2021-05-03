package model.events;

import java.util.EventListener;

public interface PlayerActionListener extends EventListener {
    void cellSelected(int col, int row);
    void moveCommitted();
    void actionsUndone();
}
