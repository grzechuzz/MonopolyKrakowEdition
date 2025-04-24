package game;

import model.field.PropertyField;
import model.field.ResortField;
import model.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class VictoryCheckerTest {

    VictoryChecker vc;
    Player player1;
    Player player2;
    List<Player> players;

    @BeforeEach
    void setUp() {
        vc = new VictoryChecker();
        player1 = new Player("winner");
        player2 = new Player("loser");
        players = List.of(player1, player2);
    }

    @Test
    void testWinByMonopolies() {
        int[] positions = {1, 2, 3, 6, 8, 9, 11, 13, 14};
        for (int pos : positions) {
            PropertyField f = new PropertyField("f" + pos, pos, 100, 10);
            player1.addProperty(f);
        }

        boolean result = vc.checkVictory(player1, players);
        assertTrue(result);
    }

    @Test
    void testWinByWall() {
        int[] wallPositions = {1, 2, 3, 6, 8, 9};
        for (int pos : wallPositions) {
            PropertyField f = new PropertyField("f" + pos, pos, 100, 10);
            player1.addProperty(f);
        }

        boolean result = vc.checkVictory(player1, players);
        assertTrue(result);
    }

    @Test
    void testWinByResorts() {
        int[] resortPositions = {7, 15, 27, 35};
        for (int pos : resortPositions) {
            ResortField r = new ResortField("resort" + pos, pos, 200, 20);
            player1.addProperty(r);
        }

        boolean result = vc.checkVictory(player1, players);
        assertTrue(result);
    }

    @Test
    void testWinByBankruptcy() {
        player2.getStatus().setEliminated(true);

        boolean result = vc.checkVictory(player1, players);
        assertTrue(result);
    }

    @Test
    void testCheckVictoryIfAllConditionsAreFalse() {
        boolean result = vc.checkVictory(player1, players);
        assertFalse(result);
    }
}
