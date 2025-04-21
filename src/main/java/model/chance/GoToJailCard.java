package model.chance;

import game.GameContext;
import model.player.Player;

public class GoToJailCard implements ChanceCard {
    @Override
    public String getDescription() {
        return "Idziesz do więzienia. ";
    }

    @Override
    public void apply(Player player, GameContext gc) {
        gc.getJailService().sendToJail(player);
    }
}
