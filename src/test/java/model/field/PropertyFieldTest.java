package model.field;

import model.field.effect.NoActionEffect;
import model.field.effect.PropertyField;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PropertyFieldTest {
    PropertyField field;

    @BeforeEach
    void setUp() {
        field = new PropertyField("Grodzka", 38, new NoActionEffect(), 900000, 450000);
    }

    @Test
    void calculateRentNoHousesNoHotelTest() {
        assertEquals(112500, field.calculateRent());
    }

    @Test
    void calculateRentWithHousesTest() {
        field.setHousesCount(3);
        assertEquals(1350000, field.calculateRent());
    }

    @Test
    void calculateRentWithHotelTest() {
        field.setHotel(true);
        assertEquals(2700000, field.calculateRent());
    }

    @Test
    void calculateValueNoHouesNoHotelTest() {
        assertEquals(900000, field.calculateValue());
    }

    @Test
    void calculateValueWithHousesTest() {
        field.setHousesCount(2);
        assertEquals(1800000, field.calculateValue());
    }

    @Test
    void calculateValueWithHotelTest() {
        field.setHotel(true);
        assertEquals(2700000, field.calculateValue());
    }
}
