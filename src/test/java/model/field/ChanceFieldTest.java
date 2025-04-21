package model.field;

import game.GameContext;
import model.chance.ChanceCard;
import model.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import service.CardService;
import service.PropertyTransactionService;
import service.UserInteractionService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ChanceFieldTest {
    @Mock
    GameContext gc;
    @Mock
    CardService cs;
    @Mock
    UserInteractionService ui;
    @Mock
    ChanceCard card;

    @Test
    void testExecuteEffect() {
        when(gc.getCardService()).thenReturn(cs);
        when(gc.getUserInteractionService()).thenReturn(ui);
        when(cs.draw()).thenReturn(card);
        when(card.getDescription()).thenReturn("desc");
        ChanceField f = new ChanceField("Szansa", 5);
        Player p = new Player("p");

        f.executeEffect(p, gc);

        verify(ui).displayMessage("SZANSA: desc");
        verify(card).apply(p, gc);
    }
}

