package model.field;

import game.GameContext;
import model.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import service.PropertyTransactionService;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SpecialFieldTest {

    @Mock
    PropertyTransactionService pts;

    @Mock
    GameContext gc;

    SpecialField special;
    SpecialField special2;

    @BeforeEach
    void setUp() {
        lenient().when(gc.getPropertyTransactionService()).thenReturn(pts);
        special = new SpecialField("a", 18, 200000, 20000);
        special2 = new SpecialField("b", 19, 200000, 20000);
    }

    @Test
    void testExecuteEffectWhenOwnerIsNull() {
        special.setOwner(null);
        Player a = new Player("a");

        special.executeEffect(a, gc);

        verify(pts).buyField(a, special);
    }

    @Test
    void testExecuteEffectWhenPlayerIsNotOwner() {
        Player a = new Player("a");
        Player b = new Player("b");
        special.setOwner(b);

        special.executeEffect(a, gc);

        verify(pts).pay(a, b, special.calculateRent());
    }

    @Test
    void testCalculateRentMultipleSpecialFields() {
        Player p = new Player("a");
        p.addProperty(special);
        p.addProperty(special2);

        assertAll(
                () -> assertEquals(40000, special.calculateRent()),
                () -> assertEquals(40000, special.calculateRent())
        );
    }
}

