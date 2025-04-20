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

@ExtendWith(MockitoExtension.class)
class ResortFieldTest {

    @Mock
    PropertyTransactionService pts;

    @Mock
    GameContext gc;

    ResortField resort;

    @BeforeEach
    void setUp() {
        lenient().when(gc.getPropertyTransactionService()).thenReturn(pts);
        resort = new ResortField("resort", 18, 200000, 20000);
    }

    @Test
    void testExecuteEffectWhenOwnerIsNull() {
        resort.setOwner(null);
        Player a = new Player("a");

        resort.executeEffect(a, gc);

        verify(pts).buyField(a, resort);
    }

    @Test
    void testExecuteEffectWhenPlayerIsNotOwner() {
        Player a = new Player("a");
        Player b = new Player("b");
        resort.setOwner(b);

        resort.executeEffect(a, gc);

        verify(pts).pay(a, b, resort.calculateRent());
    }
}
