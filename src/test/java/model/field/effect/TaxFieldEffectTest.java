package model.field.effect;

import game.GameContext;
import model.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import service.TaxService;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaxFieldEffectTest {

    @Mock
    GameContext gc;

    @Mock
    TaxService ts;

    @InjectMocks
    TaxFieldEffect tfe;

    @BeforeEach
    void setUp() {
        when(gc.getTaxService()).thenReturn(ts);
    }

    @Test
    void testTaxFieldEffect() {
        Player player = new Player("test");
        tfe.apply(player, gc);
        verify(ts).payTax(player);
    }
}
