package game;

import model.field.Field;
import model.field.PropertyField;
import model.field.ResortField;
import model.field.Ownable;
import model.player.Player;

import java.util.ArrayList;
import java.util.Set;
import java.util.List;

public class VictoryChecker {

    private static final Set<Set<Integer>> monopoly_sets = Set.of(
            Set.of(1, 2, 3),
            Set.of(6, 8, 9),
            Set.of(11, 13, 14),
            Set.of(16, 18, 19),
            Set.of(21, 23, 24),
            Set.of(26, 28, 29),
            Set.of(31, 32, 34),
            Set.of(36, 38, 39)
    );

    public boolean winByMonopolies(Player player) {
        List<Ownable> properties = player.getProperties();
        List<Integer> owned = new ArrayList<>();

        for (Ownable field : properties) {
            if (field instanceof PropertyField)
                owned.add(((Field)field).getPosition());
        }

        int count = 0;
        for (Set<Integer> monopoly : monopoly_sets) {
            boolean hasAll = true;
            for (Integer position : monopoly) {
                if (!owned.contains(position)) {
                    hasAll = false;
                    break;
                }
            }
            if (hasAll)
                count++;
        }
        return count >= 3;
    }

    public boolean winByResorts(Player player) {
        int count = 0;
        for (Ownable field : player.getProperties()) {
            if (field instanceof ResortField)
                count++;
        }
        return count >= 4;
    }

    public boolean winByBankruptcy(List<Player> players) {
        int count = 0;
        for (Player player : players) {
            if (!player.getStatus().isEliminated())
                count++;
        }
        return count == 1;
    }

    public boolean checkVictory(Player player, List<Player> players) {
        return winByMonopolies(player) || winByResorts(player) || winByBankruptcy(players);
    }
}