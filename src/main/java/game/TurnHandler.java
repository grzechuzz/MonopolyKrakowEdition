package game;

import model.field.Field;
import model.jail.JailOutcome;
import model.jail.RemainInJail;
import model.jail.RolledDouble;
import model.player.Player;


public class TurnHandler {
    private final GameContext gameContext;

    public TurnHandler(GameContext gameContext) {
        this.gameContext = gameContext;
    }

    public void movePlayer(Player player, int oldPosition, int newPosition) {
        if (newPosition < oldPosition) {
            player.addBalance(500000);
            player.getStatus().setLaps(player.getStatus().getLaps() + 1);
            gameContext.getUserInteractionService().displayMessage("Przekroczono START, bonus 500000 PLN!");
        }

        player.setPosition(newPosition);
        Field field = gameContext.getBoard().getField(newPosition);
        field.executeEffect(player, gameContext);
    }

    public void executeTurn(Player player) {
        JailOutcome outcome = null;
        int diceResult = 0;
        int newPosition = -1;
        int oldPosition = player.getPosition();

        if (player.getStatus().getJailRounds() > 0) {
            outcome = gameContext.getJailService().handleJailedPlayer(player);

            if (outcome instanceof RemainInJail)
                return;
        }

        if (outcome instanceof RolledDouble)
            diceResult = ((RolledDouble) outcome).getSum();

        if (player.getStatus().canTravel()) {
            newPosition = gameContext.getTravelService().travel(player);
        }

        if (!(newPosition != -1 || diceResult > 0)) {
            gameContext.getDice().roll();
            diceResult = gameContext.getDice().sum();

            if (gameContext.getDice().isDouble()) {
                player.getStatus().setConsecutiveDoubles(player.getStatus().getConsecutiveDoubles() + 1);
                if (player.getStatus().getConsecutiveDoubles() >= 3) {
                    gameContext.getJailService().sendToJail(player);
                    gameContext.getUserInteractionService().displayMessage("Trzeci dublet z rzędu, idziesz do więzienia.");
                    return;
                }
            } else {
                player.getStatus().setConsecutiveDoubles(0);
            }
        }

        newPosition = (oldPosition + diceResult) % gameContext.getBoard().getAllFields().size();

        movePlayer(player, oldPosition, newPosition);
    }
}
