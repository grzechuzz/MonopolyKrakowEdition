package model.chance;

import game.GameContext;
import model.player.Player;

public class GoToZakrzowekCard implements ChanceCard {
    @Override
    public String getDescription() {
        return "Jest dziś 30 stopni celsjusza. Idziesz na pole Zakrzówek, by się schłodzić!";
    }

    @Override
    public void apply(Player player, GameContext gc) {
        gc.getMovementService().moveTo(player, 27);
    }
}
