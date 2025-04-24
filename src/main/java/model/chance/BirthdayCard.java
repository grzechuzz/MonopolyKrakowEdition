package model.chance;

import game.GameContext;
import model.player.Player;
import utils.Rules;

public class BirthdayCard implements ChanceCard {
    @Override
    public String getDescription() {
        return "Masz dziś urodziny. Otrzymujesz 60 000 PLN od każdego z przeciwników.";
    }

    @Override
    public void apply(Player player, GameContext gc) {
        for (Player p : gc.getPlayers())
            if (p != player)
                gc.getPropertyTransactionService().pay(p, player, Rules.CARD_BIRTHDAY_GIFT);
    }
}
