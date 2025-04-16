package model.field;

import model.player.Player;

public interface Ownable {
    Player getOwner();
    void setOwner(Player owner);
    int getPrice();
    int calculateRent();
    int calculateValue();
    void cleanUp();
}
