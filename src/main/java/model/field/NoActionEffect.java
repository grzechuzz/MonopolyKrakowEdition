package model.field;

import model.player.Player;

public class NoActionEffect implements FieldEffect {
    @Override
    public void apply(Player player, GameManager gm) {
        // no action;
    }
}
