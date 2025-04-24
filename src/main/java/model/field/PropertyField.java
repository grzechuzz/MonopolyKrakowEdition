package model.field;

import game.GameContext;
import model.player.Player;
import utils.Rules;

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
            rent = baseRent * housesCount * Rules.RENT_HOUSE_MULTIPLIER;
        else if (housesCount == 0 && !hotel)
            rent = (int)(baseRent * Rules.RENT_EMPTY_FIELD_FACTOR);
        else
            rent = baseRent * Rules.RENT_HOTEL_MULTIPLIER;

        if (festivalActive)
            rent *= Rules.RENT_FESTIVAL_MULTIPLIER;

        return rent;
    }

    @Override
    public int calculateValue() {
        if (housesCount > 0 && !hotel)
            return basePrice + (int)(basePrice * Rules.BUILD_HOUSE_COST_FACTOR * housesCount);
        else if (housesCount == 0 && !hotel)
            return basePrice;
        else
            return (int)(basePrice * Rules.BUILD_HOTEL_COST_FACTOR * 2);
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
            gc.getPropertyTransactionService().buildHouses(player, this);
        } else if (owner != player) {
            gc.getPropertyTransactionService().pay(player, owner, calculateRent());
            gc.getPropertyTransactionService().buyOpponentField(player, owner, this);
        } else if (housesCount < Rules.MAX_HOUSES_ALLOWED && !hasHotel()) {
            gc.getPropertyTransactionService().buildHouses(player, this);
        } else if (housesCount == Rules.MAX_HOUSES_ALLOWED && !hasHotel()) {
            gc.getPropertyTransactionService().upgradeToHotel(player, this);
        }
    }
}
