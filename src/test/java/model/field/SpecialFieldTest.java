package model.field;

import game.GameContext;
import model.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import service.PropertyTransactionService;

import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SpecialFieldTest {

    @Mock
    PropertyTransactionService pts;

    @Mock
    GameContext gc;

    SpecialField special;

    @BeforeEach
    void setUp() {
        lenient().when(gc.getPropertyTransactionService()).thenReturn(pts);
        special = new SpecialField("resort", 18, 200000, 20000);
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
}

