package service.events;

import game.GameContext;
import model.field.Field;
import model.player.Player;
import org.mockito.InjectMocks;
import service.events.FieldEffectListener;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import model.board.Board;
import service.UserInteractionService;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FieldEffectListenerTest {

    @Mock
    GameContext gc;

    @Mock
    Board board;

    @Mock
    UserInteractionService ui;

    @Mock
    Field field;

    @Mock
    Player player;

    @InjectMocks
    FieldEffectListener listener;

    @BeforeEach
    void setUp() {
        when(gc.getBoard()).thenReturn(board);
        when(gc.getUserInteractionService()).thenReturn(ui);
        when(board.getField(5)).thenReturn(field);
    }

    @Test
    void testOnMove() {
        listener.onMove(player, 2, 5);

        verify(board).getField(5);

        verify(ui).showFieldInfo(field);
        verify(field).executeEffect(player, gc);
    }
}
