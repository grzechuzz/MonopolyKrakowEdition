package model.field.effect;

import game.GameContext;
import model.field.PropertyField;
import model.player.Player;

public class PropertyFieldEffect implements FieldEffect {
    private final PropertyField field;

    public PropertyFieldEffect(PropertyField field) {
        this.field = field;
    }

    @Override
    public void apply(Player player, GameContext gc) {
        if (field.getOwner() == null) {
            gc.getPropertyTransactionService().buyField(player, field);
        } else if (field.getOwner() != player) {
            gc.getPropertyTransactionService().pay(player, field.getOwner(), field.calculateRent());
            gc.getPropertyTransactionService().buyOpponentField(player, field.getOwner(), field);
        } else if (field.getOwner() == player && field.getHousesCount() < 3) {
            gc.getPropertyTransactionService().buildHouses(player, field);
        } else if (field.getOwner() == player && field.getHousesCount() == 3) {
            gc.getPropertyTransactionService().upgradeToHotel(player, field);
        }
    }
}
