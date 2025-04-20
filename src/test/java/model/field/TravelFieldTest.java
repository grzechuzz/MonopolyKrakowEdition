package model.field;

import game.GameContext;
import model.player.Player;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TravelFieldTest {

    @Mock
    GameContext gc;

    @Test
    void testExecuteEffect() {
        Player a = new Player("a");
        TravelField tf = new TravelField("podróż", 4);

        tf.executeEffect(a, gc);

        assertTrue(a.getStatus().canTravel());
    }
}
