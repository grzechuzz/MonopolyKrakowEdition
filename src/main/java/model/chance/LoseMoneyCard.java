package model.chance;

import game.GameContext;
import model.player.Player;

public class LoseMoneyCard implements ChanceCard {
    @Override
    public String getDescription() {
        return "Tracisz 150 000 PLN.";
    }

    @Override
    public void apply(Player player, GameContext gc) {
        gc.getPropertyTransactionService().payToBank(player, 150000);
    }
}
