package model.field;

import game.GameContext;
import model.player.Player;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import service.FestivalService;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FestivalFieldTest {

    @Mock
    GameContext gc;

    @Mock
    FestivalService fs;

    @Test
    void testExecuteEffect() {
        Player a = new Player("a");
        FestivalField ff = new FestivalField("festival", 4);
        when(gc.getFestivalService()).thenReturn(fs);

        ff.executeEffect(a, gc);

        verify(fs).startFestival(a);
    }
}
