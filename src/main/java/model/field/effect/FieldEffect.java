package model.field.effect;

import game.GameContext;
import model.player.Player;

public interface FieldEffect {
    void apply(Player player, GameContext gc);
}
