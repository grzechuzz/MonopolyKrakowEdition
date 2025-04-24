package game;

import model.jail.RemainInJail;
import model.jail.RolledDouble;
import model.player.Player;
import model.dice.Dice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import service.TravelService;
import service.JailService;
import service.MovementService;
import service.UserInteractionService;

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
    MovementService ms;

    @Mock
    Dice dice;

    @InjectMocks
    TurnHandler th;

    Player p;

    @BeforeEach
    void setUp() {
        p = new Player("test");

        lenient().when(gc.getDice()).thenReturn(dice);
        lenient().when(gc.getMovementService()).thenReturn(ms);
        lenient().when(gc.getJailService()).thenReturn(js);
        lenient().when(gc.getTravelService()).thenReturn(ts);
        lenient().when(gc.getUserInteractionService()).thenReturn(ui);
    }

    @Test
    void testExecuteTurnWhenInJailRemainInJail() {
        p.getStatus().setJailRounds(2);
        when(js.handleJailedPlayer(p)).thenReturn(new RemainInJail());

        th.executeTurn(p);

        verifyNoInteractions(dice, ts, ms);
    }

    @Test
    void testExecuteTurnWhenInJailRolledDouble() {
        p.getStatus().setJailRounds(1);
        when(js.handleJailedPlayer(p)).thenReturn(new RolledDouble(4));

        th.executeTurn(p);

        verify(dice, never()).roll();
        verify(ms).moveBySteps(p, 4);
    }


    @Test
    void testExecuteTurnWhenCanTravel() {
        p.getStatus().setTravel(true);
        when(ts.travel(p)).thenReturn(7);

        th.executeTurn(p);

        verify(dice, never()).roll();
        verify(ts).travel(p);
        verify(ms).moveTo(p, 7);
        verify(ms, never()).moveBySteps(p, 7);
    }


    @Test
    void testExecuteTurnNormalMove() {
        p.getStatus().setConsecutiveDoubles(1);
        when(dice.sum()).thenReturn(3);
        when(dice.isDouble()).thenReturn(false);

        th.executeTurn(p);

        assertEquals(0, p.getStatus().getConsecutiveDoubles());
        verify(dice).roll();
        verify(ms).moveBySteps(p, 3);
    }

    @Test
    void testExecuteTurnRolledThirdDoubleInARow() {
        p.getStatus().setConsecutiveDoubles(2);
        when(dice.isDouble()).thenReturn(true);

        th.executeTurn(p);

        verify(dice).roll();
        verify(js).sendToJail(p);
        verify(ms, never()).moveBySteps(any(), anyInt());
    }
}


