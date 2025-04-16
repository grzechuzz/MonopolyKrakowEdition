package model.field;

import model.player.Player;

public interface FieldEffect {
    void apply(Player player, GameManager gm);
}
