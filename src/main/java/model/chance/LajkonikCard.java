package model.chance;

import game.GameContext;
import model.player.Player;
import utils.Rules;

public class LajkonikCard implements ChanceCard {
    @Override
    public String getDescription() {
        return "Zostałes uderzony buławą przez Lajkonika podczas pochodu! Przynosi Ci to szczęście - " +
                "otrzymujesz 250 000 PLN!";
    }

    @Override
    public void apply(Player player, GameContext gc) {
        player.addBalance(Rules.CARD_LAJKONIK_REWARD);
    }
}
