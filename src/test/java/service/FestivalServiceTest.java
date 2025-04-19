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
class FestivalServiceTest {
    @Mock
    UserInteractionService ui;

    @InjectMocks
    FestivalService fs;

    @Test
    void testStartFestivalShouldReturnTrue() {
        Player player = new Player("test");
        PropertyField field = new PropertyField("pf1", 1, null, 50000, 9000);
        player.addProperty(field);

        when(ui.chooseFestivalField(player)).thenReturn(field);

        assertAll(
                () -> assertTrue(fs.startFestival(player)),
                () -> assertTrue(field.isFestivalActive())
        );
    }

    @Test
    void testStartFestivalShouldReturnFalse() {
        Player player = new Player("test");
        when(ui.chooseFestivalField(player)).thenReturn(null);

        assertFalse(fs.startFestival(player));
    }

    @Test
    void testStartFestivalShouldChangeFestivalField() {
        Player player = new Player("test");
        PropertyField field = new PropertyField("pf1", 1, null, 50000, 9000);
        PropertyField field2 = new PropertyField("pf2", 3, null, 70000, 11000);
        player.addProperty(field);
        player.addProperty(field2);

        when(ui.chooseFestivalField(player)).thenReturn(field2);
        fs.startFestival(player);

        when(ui.chooseFestivalField(player)).thenReturn(field);

        assertAll(
                () -> fs.startFestival(player),
                () -> assertFalse(field2.isFestivalActive()),
                () -> assertTrue(field.isFestivalActive())
        );
    }
}
