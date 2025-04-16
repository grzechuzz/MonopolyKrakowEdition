package model.field.effect;

import game.GameContext;
import model.player.Player;

public class JailFieldEffect implements FieldEffect {
    @Override
    public void apply(Player player, GameContext gc) {
        if (player.getStatus().getJailRounds() == 0)
            player.getStatus().setJailRounds(2);
    }
}
