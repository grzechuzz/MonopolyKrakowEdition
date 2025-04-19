package model.field.effect;

import model.player.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TravelFieldEffectTest {
    @Test
    void testTravelFieldEffect() {
        Player player = new Player("test");

        assertFalse(player.getStatus().canTravel());

        new TravelFieldEffect().apply(player,  /* gc */ null);

        assertTrue(player.getStatus().canTravel());
    }
}
