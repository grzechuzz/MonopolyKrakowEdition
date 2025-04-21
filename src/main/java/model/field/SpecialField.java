package model.field;

import game.GameContext;
import model.player.Player;

public class SpecialField extends Field implements Ownable {
    private Player owner;
    private final int basePrice;
    private final int baseRent;

    public SpecialField(String name, int position, int basePrice, int baseRent) {
        super(name, position);
        this.basePrice = basePrice;
        this.baseRent = baseRent;
    }

    @Override
    public Player getOwner() {
        return owner;
    }

    @Override
    public void setOwner(Player owner) {
        this.owner = owner;
    }

    @Override
    public int getPrice() {
        return basePrice;
    }

    @Override
    public int calculateRent() {
        return baseRent;
    }

    @Override
    public int calculateValue() {
        return basePrice;
    }

    @Override
    public void cleanUp() {
        owner = null;
    }

    public void executeEffect(Player player, GameContext gc) {
        if (owner == null) {
            gc.getPropertyTransactionService().buyField(player, this);
        } else if (owner != player) {
            gc.getPropertyTransactionService().pay(player, owner, calculateRent());
        }
    }
}

