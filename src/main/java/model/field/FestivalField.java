package model.field;

import game.GameContext;
import model.player.Player;

public class FestivalField extends Field {

    public FestivalField(String name, int position) {
        super(name, position);
    }

    @Override
    public void executeEffect(Player player, GameContext gc) {
        gc.getFestivalService().startFestival(player);
    }
}
