package game;

import model.jail.JailOutcome;
import model.jail.RemainInJail;
import model.jail.RolledDouble;
import model.player.Player;
import utils.Rules;


public class TurnHandler {
    private final GameContext gameContext;

    public TurnHandler(GameContext gameContext) {
        this.gameContext = gameContext;
    }

    public void executeTurn(Player player) {
        gameContext.getUserInteractionService().showPlayerStatus(player);

        JailOutcome outcome = null;
        if (player.getStatus().getJailRounds() > 0) {
            outcome = gameContext.getJailService().handleJailedPlayer(player);
            if (outcome instanceof RemainInJail)
                return;
        }

        int diceResult = 0;
        if (outcome instanceof RolledDouble rd) {
            diceResult = rd.getSum();
        }

        if (player.getStatus().canTravel()) {
            int target = gameContext.getTravelService().travel(player);
            gameContext.getMovementService().moveTo(player, target);
            return;
        }

        if (diceResult == 0) {
            gameContext.getDice().roll();
            diceResult = gameContext.getDice().sum();
            gameContext.getUserInteractionService().displayMessage(gameContext.getDice().toString());

            if (gameContext.getDice().isDouble()) {
                player.getStatus().setConsecutiveDoubles(player.getStatus().getConsecutiveDoubles() + 1);
                if (player.getStatus().getConsecutiveDoubles() >= Rules.MAX_DOUBLES) {
                    gameContext.getJailService().sendToJail(player);
                    gameContext.getUserInteractionService().displayMessage("Trzeci dublet z rzędu - idziesz do więzienia");
                    return;
                }
            } else {
                player.getStatus().setConsecutiveDoubles(0);
            }
        }

        gameContext.getMovementService().moveBySteps(player, diceResult);
    }
}
