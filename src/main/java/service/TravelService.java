package service;

import model.board.Board;
import model.field.Field;
import model.field.Ownable;
import model.player.Player;

import java.util.ArrayList;
import java.util.List;

public class TravelService {
    private final UserInteractionService ui;
    private final Board board;

    public TravelService(UserInteractionService ui, Board board) {
        this.ui = ui;
        this.board = board;
    }

    public int travel(Player player) {
        List<Ownable> fieldsToTravel = new ArrayList<>();
        for (Field f : board.getAllFields()) {
            if (f instanceof Ownable ownable && (ownable.getOwner() == player || ownable.getOwner() == null))
                fieldsToTravel.add(ownable);
        }

        if (fieldsToTravel.isEmpty())
            return player.getPosition();

        Ownable o = ui.chooseTravelDestination(fieldsToTravel);
        if (o == null)
            return player.getPosition();


        player.getStatus().setTravel(false);
        return ((Field)o).getPosition();
    }
}
