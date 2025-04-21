package model.chance;

import game.GameContext;
import model.player.Player;

public interface ChanceCard {
    String getDescription();
    void apply(Player player, GameContext gc);
}
