package model.field.effect;

import game.GameContext;
import model.field.Ownable;
import model.player.Player;

public class ResortSpecialFieldEffect implements FieldEffect {
    private final Ownable field;

    public ResortSpecialFieldEffect(Ownable field) {
        this.field = field;
    }

    @Override
    public void apply(Player player, GameContext gc) {
        if (field.getOwner() == null) {
            gc.getPropertyTransactionService().buyField(player, field);
        } else if (field.getOwner() != player) {
            gc.getPropertyTransactionService().pay(player, field.getOwner(), field.calculateRent());
        }
    }
}
