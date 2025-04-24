package service;

import model.board.Board;
import model.player.Player;
import service.events.MovementListener;
import utils.Rules;

import java.util.ArrayList;
import java.util.List;

public class MovementService {
    private final Board board;
    private final List<MovementListener> listeners = new ArrayList<>();

    public MovementService(Board board) {
        this.board = board;
    }

    public void addListener(MovementListener listener) {
        listeners.add(listener);
    }

    public void removeListener(MovementListener listener) {
        listeners.remove(listener);
    }

    public void moveBySteps(Player player, int steps) {
        int size = board.getAllFields().size();
        int oldPosition = player.getPosition();
        int newPosition = (oldPosition + steps) % size;

        if (newPosition < oldPosition)
            passStart(player);

        land(player, oldPosition, newPosition);
    }

    public void moveTo(Player player, int target) {
        int oldPosition = player.getPosition();

        if (target < oldPosition)
            passStart(player);

        land(player, oldPosition, target);
    }

    private void passStart(Player player) {
        player.addBalance(Rules.PASS_START_BONUS);
        player.getStatus().setLaps(player.getStatus().getLaps() + 1);
    }

    private void land(Player player, int from, int to) {
        player.setPosition(to);
        callEvent(player, from, to);
    }

    private void callEvent(Player player, int from, int to) {
        for (MovementListener listener : listeners)
            listener.onMove(player, from, to);
    }
}
