package service;

import model.player.Player;
import model.dice.Dice;
import model.jail.*;
import utils.Rules;

public class JailService {
    private final Dice dice;
    private final UserInteractionService ui;
    private final PropertyTransactionService pts;

    public JailService(Dice dice, UserInteractionService ui, PropertyTransactionService pts) {
        this.dice = dice;
        this.ui = ui;
        this.pts = pts;
    }

    public void sendToJail(Player player) {
        player.getStatus().setJailRounds(Rules.JAIL_ROUNDS);
        player.setPosition(Rules.JAIL_POSITION);
        player.getStatus().setConsecutiveDoubles(0);
    }

    public JailOutcome handleJailedPlayer(Player player) {
        int choice = ui.chooseJailOption();

        if (choice == 1 && player.getBalance() < Rules.JAIL_AMOUNT_TO_EXIT)
            choice = 2;
        else if (choice == 3 && !player.getStatus().hasJailExitCard())
            choice = 2;

        switch (choice) {
            case 1:
                pts.payToBank(player, Rules.JAIL_AMOUNT_TO_EXIT);
                player.getStatus().setJailRounds(0);
                ui.displayMessage(player.getNickname() + " wychodzi z więzienia!");
                return new PaidOut();
            case 2:
                dice.roll();
                if (dice.isDouble()) {
                    player.getStatus().setJailRounds(0);
                    ui.displayMessage(player.getNickname() + " wychodzi z więzienia!");
                    return new RolledDouble(dice.sum());
                }
                break;
            case 3:
                player.getStatus().setJailRounds(0);
                player.getStatus().setJailExitCard(false);
                ui.displayMessage(player.getNickname() + " wychodzi z więzienia!");
                return new UsedCard();
        }

        player.getStatus().setJailRounds(player.getStatus().getJailRounds() - 1);
        ui.displayMessage("Gracz " + player.getNickname() + " pozostaje w więzieniu.");
        return new RemainInJail();
    }
}
