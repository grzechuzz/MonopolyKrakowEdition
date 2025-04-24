package service.events;

import model.player.Player;

public interface MovementListener {
    void onMove(Player player, int from, int to);
}
