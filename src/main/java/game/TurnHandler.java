package game;

import model.board.Board;
import model.dice.Dice;
import model.field.Field;
import model.jail.JailOutcome;
import model.jail.RemainInJail;
import model.jail.RolledDouble;
import model.player.Player;


public class TurnHandler {
    private final Dice dice;
    private final Board board;
    private final GameContext gameContext;

    public TurnHandler(GameContext gameContext, Dice dice, Board board) {
        this.dice = dice;
        this.board = board;
        this.gameContext = gameContext;
    }

    public void movePlayer(Player player, int oldPosition, int newPosition) {
        if (newPosition < oldPosition) {
            player.addBalance(500000);
            player.getStatus().setLaps(player.getStatus().getLaps() + 1);
            gameContext.getUserInteractionService().displayMessage("Przekroczono START, bonus 500000 PLN!");
        }

        player.setPosition(newPosition);
        Field field = board.getField(newPosition);
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
            dice.roll();
            diceResult = dice.sum();

            if (dice.isDouble()) {
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

        newPosition = (oldPosition + diceResult) % board.getAllFields().size();

        movePlayer(player, oldPosition, newPosition);
    }
}
