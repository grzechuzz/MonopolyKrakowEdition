package model.chance;

import game.GameContext;
import model.field.PropertyField;
import model.player.Player;

import java.util.ArrayList;
import java.util.List;

public class ForcedSaleCard implements ChanceCard {
    @Override
    public String getDescription() {
        return "Sprzedaj dowolne pole przeciwnika (z wyjątkiem hoteli).";
    }

    @Override
    public void apply(Player player, GameContext gc) {
        List<Player> opponents = new ArrayList<>();
        for (Player p : gc.getPlayers()) {
            if (p != player)
                opponents.add(p);
        }

        List<PropertyField> fields = new ArrayList<>();
        for (Player opponent : opponents) {
            for (var o : opponent.getProperties()) {
                if (o instanceof PropertyField pf && !pf.hasHotel())
                    fields.add(pf);
            }
        }

        if (fields.isEmpty())
            return;

        PropertyField toSell = gc.getUserInteractionService().chooseFieldToForceSell(player, fields);
        if (toSell == null)
            return;

        gc.getPropertyTransactionService().forceSellOneField(toSell);
    }
}
