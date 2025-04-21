package model.field;

import game.GameContext;
import model.chance.ChanceCard;
import model.player.Player;

public class ChanceField extends Field {
    public ChanceField(String name, int position) {
        super(name, position);
    }

    @Override
    public void executeEffect(Player player, GameContext gc) {
        ChanceCard chance = gc.getCardService().draw();
        gc.getUserInteractionService().displayMessage(chance.getDescription());
        chance.apply(player, gc);
    }
}
