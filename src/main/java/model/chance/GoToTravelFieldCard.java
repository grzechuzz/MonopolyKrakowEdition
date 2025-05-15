package model.chance;

import game.GameContext;
import model.player.Player;

public class GoToTravelFieldCard implements ChanceCard {
    @Override
    public String getDescription() {
        return "Idziesz na pole PODRÓŻ.";
    }

    @Override
    public void apply(Player player, GameContext gc) {
        gc.getMovementService().moveTo(player, 30);
    }

}
