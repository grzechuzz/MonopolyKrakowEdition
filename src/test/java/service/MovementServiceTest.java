package service;

import model.board.Board;
import model.field.Field;
import model.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import service.events.MovementListener;
import utils.Rules;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MovementServiceTest {

    @Mock
    Board board;
    @Mock
    MovementListener listener1;
    @Mock
    MovementListener listener2;

    @InjectMocks
    MovementService ms;

    Player player;
    List<Field> dummyFields;

    @BeforeEach
    void setUp() {
        dummyFields = Collections.nCopies(10, mock(Field.class));
        lenient().when(board.getAllFields()).thenReturn(dummyFields);

        player = new Player("test");

        ms.addListener(listener1);
        ms.addListener(listener2);
    }

    @Test
    void testMoveByStepsWithoutPassingStart() {
        player.setPosition(2);

        ms.moveBySteps(player, 3);

        assertAll(
                () -> assertEquals(5, player.getPosition()),
                () -> assertEquals(Rules.START_BALANCE, player.getBalance()),
                () -> assertEquals(1, player.getStatus().getLaps())
        );

        verify(listener1).onMove(player, 2, 5);
        verify(listener2).onMove(player, 2, 5);
    }

    @Test
    void testMoveByStepsPassingStart() {
        player.setPosition(8);
        int steps = 5;

        ms.moveBySteps(player, steps);

        assertAll(
                () -> assertEquals(3, player.getPosition()),
                () -> assertEquals(Rules.START_BALANCE + Rules.PASS_START_BONUS, player.getBalance()),
                () -> assertEquals(2, player.getStatus().getLaps())
        );

        verify(listener1).onMove(player, 8, 3);
        verify(listener2).onMove(player, 8, 3);
    }

    @Test
    void testMoveToWithoutPassingStart() {
        player.setPosition(3);

        ms.moveTo(player, 7);

        assertAll(
                () -> assertEquals(7, player.getPosition()),
                () -> assertEquals(Rules.START_BALANCE, player.getBalance()),
                () -> assertEquals(1, player.getStatus().getLaps())
        );

        verify(listener1).onMove(player, 3, 7);
        verify(listener2).onMove(player, 3, 7);
    }

    @Test
    void testMoveToPassingStart() {
        player.setPosition(6);

        ms.moveTo(player, 2);

        assertAll(
                () -> assertEquals(2, player.getPosition()),
                () -> assertEquals(Rules.START_BALANCE + Rules.PASS_START_BONUS, player.getBalance()),
                () -> assertEquals(2, player.getStatus().getLaps())
        );

        verify(listener1).onMove(player, 6, 2);
        verify(listener2).onMove(player, 6, 2);
    }
}
