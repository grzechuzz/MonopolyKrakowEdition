package model.chance;

import game.GameContext;
import model.player.Player;

public class JuwenaliaCard implements ChanceCard {
    @Override
    public String getDescription() {
        return "Czas na Juwenalia Krakoskie! Ustawiasz festiwal na dowolnym posiadanym przez Ciebie polu!";
    }

    @Override
    public void apply(Player player, GameContext gc) {
        gc.getFestivalService().startFestival(player);
    }
}
