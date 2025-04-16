package model.field;

import model.player.Player;

public class ResortField extends Field implements Ownable {
    private Player owner;
    private final int basePrice;
    private final int baseRent;

    public ResortField(String name, int position, FieldEffect fe, int basePrice, int baseRent) {
        super(name, position, fe);
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
}
