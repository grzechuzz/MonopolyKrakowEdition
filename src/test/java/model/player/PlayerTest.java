package model.player;

import model.field.*;
import model.field.PropertyField;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    Player player;

    @BeforeEach
    void setUp() {
        player = new Player("test");
    }

    @Test
    void testAddBalance() {
        player.addBalance(123);

        assertEquals(5000123, player.getBalance());
    }

    @Test
    void testSubtractBalance() {
        player.subtractBalance(50000);

        assertEquals(4950000, player.getBalance());
    }

    @Test
    void testAddBalanceShouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> player.addBalance(-1000));
    }

    @Test
    void testSubtractBalanceShouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> player.subtractBalance(-1000));
    }

    @Test
    void testSubtractBalanceShouldThrowIllegalStateException() {
        assertThrows(IllegalStateException.class, () -> player.subtractBalance(70000000));
    }

    @Test
    void testAddProperty() {
        player.addProperty(new PropertyField("PropertyField1", 0, 5, 2));
        player.addProperty(new ResortField("ResortField1", 1, 5, 2));

        assertAll(
                () -> assertEquals(2, player.getProperties().size()),
                () -> assertEquals(player, player.getProperties().get(0).getOwner()),
                () -> assertEquals(player, player.getProperties().get(1).getOwner())
        );

    }

    @Test
    void testDeleteProperty() {
        PropertyField pf = new PropertyField("PropertyField1", 0, 5, 2);
        pf.setFestivalActive(true);
        ResortField rf = new ResortField("ResortField1", 1, 5, 2);
        player.addProperty(pf);
        player.addProperty(rf);

        player.deleteProperty(pf);

        assertAll(
                () -> assertEquals(1, player.getProperties().size()),
                () -> assertNull(pf.getOwner()),
                () -> assertFalse(pf.isFestivalActive())
        );

    }
}
