package model.chance;

import game.GameContext;
import model.player.Player;

public class ZapiekankaCard implements ChanceCard {
    @Override
    public String getDescription() {
        return "Jesteś bardzo głodny i masz ochotę na zapiekankę. Idziesz na pole Okrąglak. Dodatkowo masz dziś bardzo " +
                "dobry humor, więc stawiasz zapiekanki pozostałym graczom - łącznie za 200 000 PLN.";
    }

    @Override
    public void apply(Player player, GameContext gc) {
        gc.getMovementService().moveTo(player, 37);
        gc.getPropertyTransactionService().payToBank(player, 200000);
    }
}
