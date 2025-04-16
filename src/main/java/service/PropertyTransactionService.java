package service;

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

        ui.displayBankruptcyMessage(player);
    }

    public void forceSell(Player player) {
        List<Ownable> toSell = ui.propertiesToSell(player);
        int sum = 0;
        for (Ownable o : toSell) {
            sum += o.calculateValue();
            o.cleanUp();
        }
        ui.forceSellMessage(player, sum);
    }
}
