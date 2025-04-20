package model.field;

import game.GameContext;
import model.player.Player;

public class JailField extends Field {

    public JailField(String name, int position) {
        super(name, position);
    }

    @Override
    public void executeEffect(Player player, GameContext gc) {
        if (player.getStatus().getJailRounds() == 0)
            gc.getJailService().sendToJail(player);
    }
}
