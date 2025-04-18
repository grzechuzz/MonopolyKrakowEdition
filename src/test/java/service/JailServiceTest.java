package service;

import model.dice.Dice;
import model.jail.*;
import model.player.Player;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JailServiceTest {

    @Mock
    Dice dice;

    @Mock
    UserInteractionService ui;

    @Mock
    PropertyTransactionService pts;

    @InjectMocks
    JailService js;

    private Player player;

    @BeforeEach
    void setUp() {
        player = new Player("Test");
    }

    @Test
    void testSendToJail() {
        js.sendToJail(player);

        assertAll(
                () -> assertEquals(2, player.getStatus().getJailRounds()),
                () -> assertEquals(0, player.getStatus().getConsecutiveDoubles()),
                () -> assertEquals(10, player.getPosition())
        );
    }

    @Test
    void testHandleJailedPlayerPayment() {
        player.getStatus().setJailRounds(2);
        when(ui.chooseJailOption()).thenReturn(1);
        when(pts.payToBank(player, 500000)).thenReturn(true);

        JailOutcome jo = js.handleJailedPlayer(player);

        assertAll(
                () -> assertInstanceOf(PaidOut.class, jo),
                () -> assertEquals(0, player.getStatus().getJailRounds())
        );
        verify(pts).payToBank(player, 500000);
        verify(ui).displayMessage(contains("wychodzi"));
    }

    @Test
    void testHandleJailedPlayerPaymentWithNotEnoughMoneyShouldInvokeDiceRoll() {
        player.getStatus().setJailRounds(2);
        player.subtractBalance(5000000);
        when(ui.chooseJailOption()).thenReturn(1);

        js.handleJailedPlayer(player);

        verify(dice).roll();
    }

    @Test
    void testHandleJailedPlayerRolledDouble() {
        player.getStatus().setJailRounds(2);
        when(ui.chooseJailOption()).thenReturn(2);
        when(dice.isDouble()).thenReturn(true);

        JailOutcome jo = js.handleJailedPlayer(player);

        assertAll(
                () -> assertInstanceOf(RolledDouble.class, jo),
                () -> assertEquals(0, player.getStatus().getJailRounds())
        );
        verify(ui).displayMessage(contains("wychodzi"));
    }

    @Test
    void testHandleJailedPlayerDoubleNotRolled() {
        player.getStatus().setJailRounds(2);
        when(ui.chooseJailOption()).thenReturn(2);
        when(dice.isDouble()).thenReturn(false);

        JailOutcome jo = js.handleJailedPlayer(player);

        assertAll(
                () -> assertInstanceOf(RemainInJail.class, jo),
                () -> assertEquals(1, player.getStatus().getJailRounds())
        );
        verify(ui).displayMessage(contains("pozostaje"));
    }

    @Test
    void testHandleJailedPlayerCardExit() {
        player.getStatus().setJailExitCard(true);
        player.getStatus().setJailRounds(2);
        when(ui.chooseJailOption()).thenReturn(3);

        JailOutcome jo = js.handleJailedPlayer(player);

        assertAll(
                () -> assertInstanceOf(UsedCard.class, jo),
                () -> assertEquals(0, player.getStatus().getJailRounds()),
                () -> assertFalse(player.getStatus().hasJailExitCard())
        );
        verify(ui).displayMessage(contains("wychodzi"));
    }

    @Test
    void testHandleJailedPlayerCardExitWhenDoesNotHaveCardShouldInvokeDiceRoll() {
        player.getStatus().setJailRounds(2);
        when(ui.chooseJailOption()).thenReturn(3);

        js.handleJailedPlayer(player);

        verify(dice).roll();
    }
}
