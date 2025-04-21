package service;

import model.chance.ChanceCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CardServiceTest {
    @Mock
    Random random;

    @Mock
    ChanceCard c1, c2, c3;

    CardService cs;

    @BeforeEach
    void setUp() {
        List<ChanceCard> cards = List.of(c1, c2, c3);
        cs = new CardService(cards, random);
    }

    @Test
    void testDraw() {
        when(random.nextInt(3)).thenReturn(2);

        ChanceCard card = cs.draw();

        assertSame(c3, card);
        verify(random).nextInt(3);
    }
}
