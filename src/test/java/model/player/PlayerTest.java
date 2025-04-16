package model.player;

import model.field.*;
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
    void testAddProperty() {
        player.addProperty(new PropertyField("PropertyField1", 0, new NoActionEffect(), 5, 2));
        player.addProperty(new ResortField("ResortField1", 1, new NoActionEffect(), 5, 2));

        assertEquals(2, player.getProperties().size());
    }

    @Test
    void testDeleteProperty() {
        PropertyField pf = new PropertyField("PropertyField1", 0, new NoActionEffect(), 5, 2);
        ResortField rf = new ResortField("ResortField1", 1, new NoActionEffect(), 5, 2);
        player.addProperty(pf);
        player.addProperty(rf);

        player.deleteProperty(rf);

        assertEquals(1, player.getProperties().size());
    }
}
