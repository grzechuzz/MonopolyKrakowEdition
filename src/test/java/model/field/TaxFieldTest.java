package model.field;

import game.GameContext;
import model.player.Player;
import service.TaxService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class TaxFieldTest {

    @Mock
    TaxService ts;

    @Mock
    GameContext gc;

    @Test
    void testExecuteEffect() {
        Player a = new Player("a");
        TaxField tf = new TaxField("PODATEK", 19);
        when(gc.getTaxService()).thenReturn(ts);

        tf.executeEffect(a, gc);
        verify(ts).payTax(a);
    }
}
