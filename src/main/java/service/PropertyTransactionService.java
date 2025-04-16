package service;

import model.field.effect.PropertyField;
import model.player.Player;
import model.field.Ownable;

import java.util.List;

public class PropertyTransactionService {

    private final UserInteractionService ui;

    public PropertyTransactionService(UserInteractionService ui) {
        this.ui = ui;
    }

    public void pay(Player from, Player to, int amount) {
        if (from.getBalance() < amount) {
            forceSell(from);
            if (from.getBalance() < amount) {
                to.addBalance(from.getBalance());
                bankruptPlayer(from);
                return;
            }
        }

        from.subtractBalance(amount);
        to.addBalance(amount);
    }

    public void payToBank(Player player, int amount) {
        if (player.getBalance() < amount) {
            forceSell(player);
            if (player.getBalance() < amount) {
                bankruptPlayer(player);
                return;
            }
        }

        player.subtractBalance(amount);
    }

    public void bankruptPlayer(Player player) {
        player.getStatus().setEliminated(true);
        for (Ownable f : player.getProperties())
            f.cleanUp();

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

    public void buyProperty(Player player, Ownable field) {
        int price = field.getPrice();
        if (player.getBalance() < price) {
            ui.displayMessage("Nie wystarczające fundusze, by dokonać transakcji.");
            return;
        }

        boolean confirm = ui.confirmPurchase(field.getName(), price);
        if (!confirm)
            return;

        player.subtractBalance(price);
        player.addProperty(field);
        field.setOwner(player);
        ui.displayMessage(player.getNickname() + " kupuje " + field.getName() + " za " + price + " PLN");
    }

    public void buildHouses(Player player, PropertyField field) {
        boolean firstLap = (player.getStatus().getLaps() == 1);
        int maxHousesAllowed = firstLap ? 2 : 3;
        int housesRemaining = 3 - field.getHousesCount();
        int max = Math.min(maxHousesAllowed, housesRemaining);

        if (max <= 0)
            return;

        int requested = ui.promptHouseCount(field, max);
        int totalCost = (int)(requested * field.getPrice() * 0.5);

        if (player.getBalance() < totalCost) {
            ui.displayMessage("Nie wystarczające fundusze, by dokonać transakcji.");
            return;
        }

        field.setHousesCount(field.getHousesCount() + requested);
        player.subtractBalance(totalCost);
    }

    public void upgradeToHotel(Player player, PropertyField field) {
        if (field.getHousesCount() != 3)
            return;

        int totalCost = (int)(1.5 * field.getPrice());
        if (player.getBalance() < totalCost) {
            ui.displayMessage("Nie wystarczające fundusze, by dokonać transakcji.");
            return;
        }

        player.subtractBalance(totalCost);
        field.setHousesCount(0);
        field.setHotel(true);
    }

    public void buyOpponentField(Player buyer, Player seller, PropertyField field) {
        int buyoutPrice = (int)(field.calculateValue() * 1.5);

        if (buyer.getBalance() < buyoutPrice)
            return;

        boolean confirm = ui.confirmPurchase(field.getName(), buyoutPrice);
        if (!confirm)
            return;


        buyer.subtractBalance(buyoutPrice);
        seller.addBalance(buyoutPrice);

        seller.deleteProperty(field);
        buyer.addProperty(field);
        field.setOwner(buyer);

        ui.displayMessage(buyer.getNickname() + " odkupił " + field.getName()
                + " za " + buyoutPrice + " PLN od gracza " + seller.getNickname() + ".");
    }
}
