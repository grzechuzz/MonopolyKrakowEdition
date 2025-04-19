package model.field.effect;

import game.GameContext;
import model.player.Player;

public class TravelFieldEffect implements FieldEffect {
    @Override
    public void apply(Player player, GameContext gc) {
        player.getStatus().setTravel(true);
    }
}
