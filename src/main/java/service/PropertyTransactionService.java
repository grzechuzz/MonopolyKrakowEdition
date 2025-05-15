package service;

import model.field.PropertyField;
import model.player.Player;
import model.field.Ownable;
import utils.Rules;

import java.util.List;

public class PropertyTransactionService {

    private final UserInteractionService ui;

    public PropertyTransactionService(UserInteractionService ui) {
        this.ui = ui;
    }

    public void bankruptPlayer(Player player) {
        player.getStatus().setEliminated(true);
        for (Ownable f : player.getProperties())
            f.cleanUp();

        player.getProperties().clear();
        ui.displayMessage("Gracz " + player.getNickname() + " jest bankrutem!");
    }

    public void forceSell(Player player) {
        List<Ownable> toSell = ui.propertiesToSell(player);
        int sum = 0;
        for (Ownable o : toSell) {
            sum += o.calculateValue();
            o.cleanUp();
            player.deleteProperty(o);
        }
        player.addBalance(sum);
        ui.displayMessage("Gracz " + player.getNickname() + " sprzedaje nieruchomości o wartości " + sum + " PLN!");
    }

    public boolean pay(Player from, Player to, int amount) {
        if (from.getBalance() < amount) {
            while (from.getBalance() < amount && !from.getProperties().isEmpty())
                forceSell(from);
            if (from.getBalance() < amount) {
                to.addBalance(from.getBalance());
                bankruptPlayer(from);
                return false;
            }
        }

        from.subtractBalance(amount);
        to.addBalance(amount);
        return true;
    }

    public boolean payToBank(Player player, int amount) {
        if (player.getBalance() < amount) {
            while (player.getBalance() < amount && !player.getProperties().isEmpty())
                forceSell(player);
            if (player.getBalance() < amount) {
                bankruptPlayer(player);
                return false;
            }
        }

        player.subtractBalance(amount);
        return true;
    }

    public boolean buyField(Player player, Ownable field) {
        int price = field.getPrice();
        if (player.getBalance() < price) {
            ui.displayMessage("Niewystarczające fundusze, by dokonać transakcji.");
            return false;
        }

        boolean confirm = ui.confirmPurchase(field.getName(), price);
        if (!confirm)
            return false;

        player.subtractBalance(price);
        player.addProperty(field);
        ui.displayMessage(player.getNickname() + " kupuje " + field.getName() + " za " + price + " PLN");
        return true;
    }

    public boolean buildHouses(Player player, PropertyField field) {
        if (field.getOwner() != player)
            return false;

        boolean firstLap = (player.getStatus().getLaps() == 1);
        int maxHousesAllowed = firstLap ? Rules.MAX_HOUSES_ALLOWED_FIRST_LAP : Rules.MAX_HOUSES_ALLOWED;
        int housesRemaining = 3 - field.getHousesCount();
        int max = Math.min(maxHousesAllowed, housesRemaining);

        if (max <= 0)
            return false;

        int requested = ui.promptHouseCount(field, max);
        int totalCost = (int)(requested * field.getPrice() * Rules.BUILD_HOUSE_COST_FACTOR);

        if (player.getBalance() < totalCost) {
            ui.displayMessage("Niewystarczające fundusze, by dokonać transakcji.");
            return false;
        }

        field.setHousesCount(field.getHousesCount() + requested);
        player.subtractBalance(totalCost);
        return true;
    }

    public boolean upgradeToHotel(Player player, PropertyField field) {
        if (field.getOwner() != player)
            return false;

        if (field.getHousesCount() != Rules.MAX_HOUSES_ALLOWED)
            return false;

        int totalCost = (int)(Rules.BUILD_HOTEL_COST_FACTOR * field.getPrice());
        if (player.getBalance() < totalCost) {
            ui.displayMessage("Niewystarczające fundusze, by dokonać transakcji.");
            return false;
        }

        if (!ui.confirmHotelUpgrade(field, totalCost))
            return false;

        player.subtractBalance(totalCost);
        field.setHousesCount(0);
        field.setHotel(true);
        return true;
    }

    public boolean buyOpponentField(Player buyer, Player seller, PropertyField field) {
        if (field.getOwner() != seller || field.hasHotel())
            return false;

        int buyoutPrice = (int)(field.calculateValue() * Rules.BUY_OPPONENT_FIELD_FACTOR);

        if (buyer.getBalance() < buyoutPrice)
            return false;

        boolean confirm = ui.confirmPurchase(field.getName(), buyoutPrice);
        if (!confirm)
            return false;

        buyer.subtractBalance(buyoutPrice);
        seller.addBalance(buyoutPrice);

        seller.deleteProperty(field);
        buyer.addProperty(field);

        ui.displayMessage(buyer.getNickname() + " odkupił " + field.getName()
                + " za " + buyoutPrice + " PLN od gracza " + seller.getNickname() + ".");

        return true;
    }

    public boolean forceSellOneField(PropertyField field) {
        Player seller = field.getOwner();

        if (seller == null || field.hasHotel())
            return false;

        int val = field.calculateValue();
        field.cleanUp();
        seller.deleteProperty(field);
        seller.addBalance(val);
        ui.displayMessage(seller.getNickname() + " sprzedaje pole " + field.getName() + " za " + val +
                " PLN (wymuszona sprzedaż).");

        return true;
    }
}
