package model.field;

import game.GameContext;
import model.player.Player;

public class ChanceField extends Field {
    public ChanceField(String name, int position) {
        super(name, position);
    }

    @Override
    public void executeEffect(Player player, GameContext gc) {

    }
}
