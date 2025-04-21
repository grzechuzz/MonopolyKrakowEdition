package model.chance;

import game.GameContext;
import game.TurnHandler;
import model.player.Player;

public class MacheteFightCard implements ChanceCard {
    @Override
    public String getDescription() {
        return "Wisła czy Cracovia? Jesteś świadkiem walki kiboli na maczety. Drastyczne sceny mają fatalny wpływ " +
                "na twoją psychikę. Idziesz do Kobierzyna i tracisz 50 000 PLN (+ ewentualny czynsz).";
    }

    @Override
    public void apply(Player player, GameContext gc) {
        TurnHandler th = new TurnHandler(gc);
        th.movePlayer(player, player.getPosition(), 25);
    }
}
