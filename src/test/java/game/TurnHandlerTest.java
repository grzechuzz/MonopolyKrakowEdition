package game;

import model.board.Board;
import model.dice.Dice;
import model.field.Field;
import model.field.StartField;
import model.jail.RemainInJail;
import model.jail.RolledDouble;
import model.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import service.JailService;
import service.TravelService;
import service.UserInteractionService;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TurnHandlerTest {
    @Mock
    GameContext gc;

    @Mock
    UserInteractionService ui;

    @Mock
    JailService js;

    @Mock
    TravelService ts;

    @Mock
    Dice dice;

    TurnHandler th;
    Board board;
    Player p;

    @BeforeEach
    void setUp() {
        p = new Player("test");
        board = spy(new Board());
        th = new TurnHandler(gc);

        lenient().when(gc.getBoard()).thenReturn(board);
        lenient().when(gc.getDice()).thenReturn(dice);
        lenient().when(gc.getJailService()).thenReturn(js);
        lenient().when(gc.getTravelService()).thenReturn(ts);
        lenient().when(gc.getUserInteractionService()).thenReturn(ui);

        List<Field> dummy = Collections.nCopies(10, mock(Field.class));
        lenient().when(board.getAllFields()).thenReturn(dummy);
    }

    @Test
    void testMovePlayerPassesStart() {
        StartField sf = new StartField("DoNothing", 1);
        when(gc.getBoard().getField(1)).thenReturn(sf);
        p.setPosition(38);
        int oldLaps = p.getStatus().getLaps();

        th.movePlayer(p, p.getPosition(), 1);

        assertAll(
                () -> assertEquals(1, p.getPosition()),
                () -> assertEquals(5500000, p.getBalance()),
                () -> assertEquals(oldLaps + 1, p.getStatus().getLaps())
        );
        verify(ui).displayMessage(contains("Przekroczono"));
        verify(ui).showFieldInfo(sf);
    }

    @Test
    void testMovePlayerWithoutPassingStart() {
        StartField sf = new StartField("DoNothing", 1);
        when(gc.getBoard().getField(14)).thenReturn(sf);
        p.setPosition(7);

        th.movePlayer(p, p.getPosition(), 14);

        assertAll(
                () -> assertEquals(5000000, p.getBalance()),
                () -> assertEquals(14, p.getPosition())
        );
        verify(ui, never()).displayMessage(contains("Przekroczono"));
        verify(ui).showFieldInfo(sf);
    }

    @Test
    void testExecuteTurnWhenInJailRemainInJail() {
        p.getStatus().setJailRounds(2);
        when(js.handleJailedPlayer(p)).thenReturn(new RemainInJail());

        th.executeTurn(p);

        verifyNoInteractions(dice, ts, board);
    }

    @Test
    void testExecuteTurnWhenInJailRolledDouble() {
        p.getStatus().setJailRounds(1);
        when(js.handleJailedPlayer(p)).thenReturn(new RolledDouble(4));

        Field target = mock(Field.class);
        when(board.getField(4 % 10)).thenReturn(target);

        th.executeTurn(p);

        verify(dice, never()).roll();
        verify(target).executeEffect(p, gc);
        assertEquals(4, p.getPosition());
    }

    @Test
    void testExecuteTurnWhenCanTravel() {
        p.getStatus().setTravel(true);
        when(ts.travel(p)).thenReturn(7);

        Field target = mock(Field.class);
        when(board.getField(7 % 10)).thenReturn(target);

        th.executeTurn(p);

        verify(dice, never()).roll();
        verify(ts).travel(p);
        verify(target).executeEffect(p, gc);
        assertEquals(7, p.getPosition());
    }

    @Test
    void testExecuteTurnNormalMove() {
        p.getStatus().setConsecutiveDoubles(1);
        when(dice.sum()).thenReturn(3);
        when(dice.isDouble()).thenReturn(false);

        Field target = mock(Field.class);
        when(board.getField(3 % 10)).thenReturn(target);

        th.executeTurn(p);

        verify(dice).roll();
        assertEquals(0, p.getStatus().getConsecutiveDoubles());
        verify(target).executeEffect(p, gc);
        assertEquals(3, p.getPosition());
    }

    @Test
    void testExecuteTurnRolledDoubleButNotThirdInARow() {
        p.getStatus().setConsecutiveDoubles(1);
        when(dice.sum()).thenReturn(2);
        when(dice.isDouble()).thenReturn(true);

        Field target = mock(Field.class);
        when(board.getField(2 % 10)).thenReturn(target);

        th.executeTurn(p);

        verify(dice).roll();
        assertEquals(2, p.getStatus().getConsecutiveDoubles());
        verify(target).executeEffect(p, gc);
        assertEquals(2, p.getPosition());
    }


    @Test
    void testExecuteTurnRolledThirdDoubleInARow() {
        p.getStatus().setConsecutiveDoubles(2);
        when(dice.isDouble()).thenReturn(true);

        th.executeTurn(p);

        verify(dice).roll();
        verify(js).sendToJail(p);
        verify(board, never()).getField(anyInt());
    }
}

