package model.chance;

import game.GameContext;
import model.player.Player;
import utils.Rules;

public class DlugaTramBlockCard implements ChanceCard {
    @Override
    public String getDescription() {
        return "Twój źle zaparkowany samochód doprowadził do zatrzymania tramwajowego na ulicy Długiej. " +
                "Tracisz 500 000 PLN (zapłata mandatu + odholowanie auta).";
    }

    @Override
    public void apply(Player player, GameContext gc) {
        gc.getPropertyTransactionService().payToBank(player, Rules.CARD_DLUGA_TRAM_BLOCK_FINE);
    }
}
