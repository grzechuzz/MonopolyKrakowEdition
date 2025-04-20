package model.field;

import game.GameContext;
import model.player.Player;

public class StartField extends Field {

    public StartField(String name, int position) {
        super(name, position);
    }

    @Override
    public void executeEffect(Player player, GameContext gc) {
        // no action
    }
}
