package service;

import model.board.Board;
import model.field.Field;
import model.field.PropertyField;
import model.player.Player;

public class FestivalService {
    private final UserInteractionService ui;
    private PropertyField activeFestival;

    public FestivalService(UserInteractionService ui, Board board) {
        this.ui = ui;
    }

    public boolean startFestival(Player player) {
        PropertyField field = ui.chooseFestivalField(player);
        if (field == null)
            return false;

        if (activeFestival != null)
            activeFestival.setFestivalActive(false);

        field.setFestivalActive(true);
        activeFestival = field;
        ui.displayMessage("Festiwal na polu " + field.getName() +". Czynsz x2!");

        return true;
    }
}
