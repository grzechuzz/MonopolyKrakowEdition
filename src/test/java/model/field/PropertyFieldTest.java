package model.field;

import game.GameContext;
import model.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import service.PropertyTransactionService;
import utils.Rules;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PropertyFieldTest {

    @Mock
    PropertyTransactionService pts;

    @Mock
    GameContext gc;

    PropertyField field;

    @BeforeEach
    void setUp() {
        field = new PropertyField("Grodzka", 38, 900000, 450000);
        lenient().when(gc.getPropertyTransactionService()).thenReturn(pts);
    }

    @Test
    void testCalculateRentNoHousesNoHotel() {
        assertEquals(450000 * Rules.RENT_EMPTY_FIELD_FACTOR, field.calculateRent());
    }

    @Test
    void testCalculateRentWithHouses() {
        field.setHousesCount(3);
        assertEquals(450000 * Rules.RENT_HOUSE_MULTIPLIER * field.getHousesCount(), field.calculateRent());
    }

    @Test
    void testCalculateRentWithHotel() {
        field.setHotel(true);
        assertEquals(450000 * Rules.RENT_HOTEL_MULTIPLIER, field.calculateRent());
    }

    @Test
    void testCalculateValueNoHousesNoHotel() {
        assertEquals(900000, field.calculateValue());
    }

    @Test
    void testCalculateValueWithHouses() {
        field.setHousesCount(2);
        assertEquals(900000 * Rules.BUILD_HOUSE_COST_FACTOR * field.getHousesCount() + 900000, field.calculateValue());
    }

    @Test
    void testCalculateValueWithHotel() {
        field.setHotel(true);
        assertEquals(900000 * 2 * Rules.BUILD_HOTEL_COST_FACTOR, field.calculateValue());
    }

    @Test
    void testExecuteEffectWhenUnowned() {
        Player a = new Player("a");
        field.setOwner(null);

        field.executeEffect(a, gc);

        verify(pts).buyField(a, field);
        verify(pts).buildHouses(a, field);
    }

    @Test
    void testExecuteEffectWhenOwnedPaysRent() {
        Player a = new Player("a");
        Player b = new Player("b");
        field.setOwner(b);

        field.executeEffect(a, gc);

        verify(pts).pay(a, b, field.calculateRent());
    }

    @Test
    void testExecuteEffectWhenOwnedBySelfAndHasLessThanThreeHouses() {
        Player a = new Player("a");
        field.setOwner(a);
        field.setHousesCount(1);

        field.executeEffect(a, gc);

        verify(pts).buildHouses(a, field);
    }

    @Test
    void testExecuteEffectWhenOwnedBySelfAndHasThreeHouses() {
        Player a = new Player("a");
        field.setOwner(a);
        field.setHousesCount(3);

        field.executeEffect(a, gc);

        verify(pts).upgradeToHotel(a, field);
    }
}
