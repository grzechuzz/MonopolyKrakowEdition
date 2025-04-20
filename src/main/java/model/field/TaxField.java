package model.field;

import game.GameContext;
import model.player.Player;

public class TaxField extends Field {

    public TaxField(String name, int position) {
        super(name, position);
    }

    @Override
    public void executeEffect(Player player, GameContext gc) {
        gc.getTaxService().payTax(player);
    }
}
