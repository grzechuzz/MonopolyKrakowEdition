package model.chance;

import game.GameContext;
import model.player.Player;

public class StationStairsCard implements ChanceCard {
    @Override
    public String getDescription() {
        return "Jesteś świadkiem działających schodów ruchomych przy Dworcu Głównym. " +
                "Twoje nagranie staje się viralem. Pobierz 300 000 PLN.";
    }

    @Override
    public void apply(Player player, GameContext gc) {
        player.addBalance(300000);
    }
}
