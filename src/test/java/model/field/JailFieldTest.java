package model.field;

import game.GameContext;
import model.player.Player;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import service.JailService;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JailFieldTest {

    @Mock
    GameContext gc;

    @Mock
    JailService js;

    @Test
    void testExecuteEffect() {
        Player a = new Player("a");
        when(gc.getJailService()).thenReturn(js);
        a.getStatus().setJailRounds(0);
        JailField jf = new JailField("jail", 3);

        jf.executeEffect(a, gc);

        verify(js).sendToJail(a);
    }
}
