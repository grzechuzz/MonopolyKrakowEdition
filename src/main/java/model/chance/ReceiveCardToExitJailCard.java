package model.chance;

import game.GameContext;
import model.player.Player;

public class ReceiveCardToExitJailCard implements ChanceCard {
    @Override
    public String getDescription() {
        return "Otrzymujesz kartę: 'Wyjdź z więzienia'";
    }

    @Override
    public void apply(Player player, GameContext gc) {
        player.getStatus().setJailExitCard(true);
    }
}
