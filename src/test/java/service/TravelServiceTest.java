package service;

import model.field.PropertyField;
import model.field.ResortField;
import model.field.SpecialField;
import model.player.Player;
import model.board.Board;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TravelServiceTest {
    @Mock
    UserInteractionService ui;

    @Mock
    Board board;

    @InjectMocks
    TravelService ts;

    @Test
    void testTravelReturnsCorrectFieldPosition() {
        PropertyField pf1 = new PropertyField("pf", 0, 30000, 15000);
        PropertyField pf2 = new PropertyField("pf2", 1, 30000, 15000);
        ResortField rf1 = new ResortField("rf", 2, 40000, 10000);
        SpecialField sf1 = new SpecialField("sf", 3, 50000, 5000);
        Player player = new Player("test1");
        Player player2 = new Player("test2");
        pf2.setOwner(player2);
        pf1.setOwner(player);

        when(board.getAllFields()).thenReturn(List.of(pf1, pf2, rf1, sf1));
        when(ui.chooseTravelDestination(List.of(pf1, rf1, sf1))).thenReturn(sf1);

        assertAll(
                () -> assertEquals(3, ts.travel(player)),
                () -> assertFalse(player.getStatus().canTravel())
        );
        verify(ui).chooseTravelDestination(List.of(pf1, rf1, sf1));
    }

    @Test
    void testTravelEmptyListReturnsPlayerPosition() {
        Player player = new Player("test");
        player.setPosition(19);
        when(board.getAllFields()).thenReturn(List.of());

        assertAll(
                () -> assertEquals(19, ts.travel(player)),
                () -> assertFalse(player.getStatus().canTravel())
        );
    }

    @Test
    void testTravelWrongChoiceReturnsPlayerPosition() {
        PropertyField pf1 = new PropertyField("pf", 0, 30000, 15000);
        PropertyField pf2 = new PropertyField("pf2", 1, 30000, 15000);
        ResortField rf1 = new ResortField("rf", 2, 40000, 10000);
        SpecialField sf1 = new SpecialField("sf", 3, 50000, 5000);
        Player player = new Player("test1");
        Player player2 = new Player("test2");
        pf2.setOwner(player2);
        pf1.setOwner(player);
        player.setPosition(19);
        player.getStatus().setTravel(true);

        when(board.getAllFields()).thenReturn(List.of(pf1, pf2, rf1, sf1));
        when(ui.chooseTravelDestination(List.of(pf1, rf1, sf1))).thenReturn(null);

        assertAll(
                () -> assertEquals(19, ts.travel(player)),
                () -> assertFalse(player.getStatus().canTravel())
        );
        verify(ui).chooseTravelDestination(List.of(pf1, rf1, sf1));
    }
}
