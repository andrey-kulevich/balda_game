package model;

/** Listener in main player's actions */
public interface PlayerListener {
    void moveConfirmed(Game.WordCheckStatus status);
    void currentActionsUndone();
    void moveSkipped();
}
