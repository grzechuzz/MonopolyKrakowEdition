package service;

import io.GameIO;
import model.field.Ownable;
import model.field.PropertyField;
import model.field.ResortField;
import model.player.Player;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ConsoleUserInteractionServiceTest {
    @Mock
    GameIO io;

    @InjectMocks
    ConsoleUserInteractionService cuis;

    Player player;
    PropertyField pf;
    ResortField rf;

    @BeforeEach
    void setUp() {
        player = new Player("krzychu");
        pf = new PropertyField("test", 0, null, 50000, 20000);
        rf = new ResortField("Resort", 2, null, 100000, 30000);
    }

    @Test
    void testPropertiesToSell() {
        player.addProperty(pf);
        player.addProperty(rf);
        when(io.readInts()).thenReturn(List.of(1, 3, 5));

        assertEquals(List.of(rf), cuis.propertiesToSell(player));
    }

    @Test
    void testChooseFestivalField() {
        PropertyField pf2 = new PropertyField("test2", 1, null, 55000, 23000);
        player.addProperty(pf);
        player.addProperty(pf2);
        player.addProperty(rf);
        when(io.readInt()).thenReturn(0);

        assertEquals(pf, cuis.chooseFestivalField(player));
    }

    @Test
    void testChooseFestivalFieldChooseWrongIndex() {
        PropertyField pf2 = new PropertyField("test2", 1, null, 55000, 23000);
        player.addProperty(pf);
        player.addProperty(pf2);
        player.addProperty(rf);
        when(io.readInt()).thenReturn(2);

        assertNull(cuis.chooseFestivalField(player));
    }

    @Test
    void testChooseFestivalFieldEmptyList() {
        assertNull(cuis.chooseFestivalField(player));
    }

    @Test
    void testPromptHouseCount() {
        when(io.readInt()).thenReturn(2);

        assertEquals(2, cuis.promptHouseCount(pf, 3));
    }

    @Test
    void testPromptHouseCountWantOverLimit() {
        when(io.readInt()).thenReturn(5);

        assertEquals(2, cuis.promptHouseCount(pf, 2));
    }

    @Test
    void testPromptHouseCountNegativeArgumentGiven() {
        when(io.readInt()).thenReturn(-3);

        assertEquals(0, cuis.promptHouseCount(pf, 3));
    }


    @Test
    void testChooseJailOptionCorrectArgumentGiven() {
        when(io.readInt()).thenReturn(3);

        assertEquals(3, cuis.chooseJailOption());
    }


    @Test
    void testChooseJailOptionWrongArgumentGiven() {
        when(io.readInt()).thenReturn(88);

        assertEquals(2, cuis.chooseJailOption());
    }

    @Test
    void testChooseTravelDestinationReturnCorrectField() {
        PropertyField pf2 = new PropertyField("test2", 4, null, 50000, 20000);
        ResortField rf2 = new ResortField("Resort2", 9, null, 100000, 30000);
        List<Ownable> o = new ArrayList<>(List.of(pf, pf2, rf, rf2));
        when(io.readInt()).thenReturn(3);

        assertEquals(cuis.chooseTravelDestination(o), rf2);
    }

    @Test
    void testChooseTravelDestinationReturnNull() {
        List<Ownable> o = new ArrayList<>(List.of(pf, rf));
        when(io.readInt()).thenReturn(3);

        assertNull(cuis.chooseTravelDestination(o));
    }
}
