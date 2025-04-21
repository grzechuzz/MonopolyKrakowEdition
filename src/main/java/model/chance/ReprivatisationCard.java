package model.chance;

import game.GameContext;
import model.field.PropertyField;
import model.player.Player;

public class ReprivatisationCard implements ChanceCard {
    @Override
    public String getDescription() {
        return "Jesteś beneficjentem afery reprywatyzacyjnej na krakowskim Kazimierzu. Jeżeli pole Plac Nowy jest " +
                "wolne, to stawiasz na nim jeden dom bez opłat. W przeciwnym wypadku pobierasz 300 000 PLN.";
    }

    @Override
    public void apply(Player player, GameContext gc) {
        PropertyField placNowy = (PropertyField) gc.getBoard().getField(36);
        if (placNowy.getOwner() == null) {
            player.addProperty(placNowy);
            placNowy.setHousesCount(1);
        } else {
            player.addBalance(300000);
        }
    }
}
