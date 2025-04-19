package model.field.effect;

import game.GameContext;
import model.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import service.FestivalService;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FestivalFieldEffectTest {

    @Mock
    GameContext gc;
    @Mock
    FestivalService festSvc;

    FieldEffect fx = new FestivalFieldEffect();

    @BeforeEach
    void setUp() {
        when(gc.getFestivalService()).thenReturn(festSvc);
    }

    @Test
    void TestStartFestival() {
        Player p = new Player("Ola");
        fx.apply(p, gc);
        verify(festSvc).startFestival(p);
    }
}

