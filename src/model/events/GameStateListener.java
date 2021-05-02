package model.events;

import java.util.EventListener;

public interface GameStateListener extends EventListener {
    void gameIsDone();
}
