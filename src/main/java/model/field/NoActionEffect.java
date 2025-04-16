package model.field;

import game.GameContext;
import model.player.Player;

public class NoActionEffect implements FieldEffect {
    @Override
    public void apply(Player player, GameContext gc) {
        // no action;
    }
}
