package model.chance;

import game.GameContext;
import model.player.Player;
import utils.Rules;

public class MacheteFightCard implements ChanceCard {
    @Override
    public String getDescription() {
        return "Wisła czy Cracovia? Jesteś świadkiem walki kiboli na maczety. Drastyczne sceny mają fatalny wpływ " +
                "na twoją psychikę. Idziesz do Kobierzyna i tracisz 50 000 PLN (+ ewentualny czynsz).";
    }

    @Override
    public void apply(Player player, GameContext gc) {
        gc.getPropertyTransactionService().payToBank(player, Rules.CARD_MACHETE_FIGHT_MONEY);
        gc.getMovementService().moveTo(player, 25);
    }
}
