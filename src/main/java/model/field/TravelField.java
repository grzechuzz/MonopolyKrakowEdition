package model.field;

import game.GameContext;
import model.player.Player;

public class TravelField extends Field {

    public TravelField(String name, int position) {
        super(name, position);
    }

    @Override
    public void executeEffect(Player player, GameContext gc) {
        player.getStatus().setTravel(true);
        player.getStatus().setConsecutiveDoubles(0);
    }
}
