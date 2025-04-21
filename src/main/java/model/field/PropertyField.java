package model.field;

import game.GameContext;
import model.player.Player;

public class PropertyField extends Field implements Ownable {
    private Player owner;
    private final int basePrice;
    private final int baseRent;
    private int housesCount;
    private boolean hotel;
    private boolean festivalActive;

    public PropertyField(String name, int position, int basePrice, int baseRent) {
        super(name, position);
        this.basePrice = basePrice;
        this.baseRent = baseRent;
        this.housesCount = 0;
        this.hotel = false;
        this.festivalActive = false;
    }

    public int getHousesCount() {
        return housesCount;
    }

    public void setHousesCount(int housesCount) {
        this.housesCount = housesCount;
    }

    public boolean hasHotel() {
        return hotel;
    }

    public void setHotel(boolean hotel) {
        this.hotel = hotel;
    }

    public boolean isFestivalActive() {
        return festivalActive;
    }

    public void setFestivalActive(boolean festivalActive) {
        this.festivalActive = festivalActive;
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
        int rent;
        if (housesCount > 0 && !hotel)
            rent = baseRent * housesCount;
        else if (housesCount == 0 && !hotel)
            rent = baseRent / 4;
        else
            rent = baseRent * 6;

        if (festivalActive)
            rent *= 2;

        return rent;
    }

    @Override
    public int calculateValue() {
        if (housesCount > 0 && !hotel)
            return basePrice + (int)(basePrice * 0.5 * housesCount);
        else if (housesCount == 0 && !hotel)
            return basePrice;
        else
            return basePrice * 3;
    }

    @Override
    public void cleanUp() {
        owner = null;
        housesCount = 0;
        hotel = false;
        festivalActive = false;
    }

    @Override
    public void executeEffect(Player player, GameContext gc) {
        if (owner == null) {
            gc.getPropertyTransactionService().buyField(player, this);
        } else if (owner != player) {
            gc.getPropertyTransactionService().pay(player, owner, calculateRent());
            gc.getPropertyTransactionService().buyOpponentField(player, owner, this);
        } else if (housesCount < 3 && !hasHotel()) {
            gc.getPropertyTransactionService().buildHouses(player, this);
        } else if (housesCount == 3 && !hasHotel()) {
            gc.getPropertyTransactionService().upgradeToHotel(player, this);
        }
    }
}
