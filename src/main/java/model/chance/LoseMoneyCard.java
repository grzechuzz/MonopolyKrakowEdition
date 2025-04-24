package model.chance;

import game.GameContext;
import model.player.Player;
import utils.Rules;

public class LoseMoneyCard implements ChanceCard {
    @Override
    public String getDescription() {
        return "Tracisz 200 000 PLN.";
    }

    @Override
    public void apply(Player player, GameContext gc) {
        gc.getPropertyTransactionService().payToBank(player, Rules.CARD_LOSE_MONEY);
    }
}
