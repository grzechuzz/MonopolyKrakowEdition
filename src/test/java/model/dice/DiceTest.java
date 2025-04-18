package model.dice;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class DiceTest {

    @Test
    void testRollDouble() {
        Random fake = new Random() {
            @Override
            public int nextInt(int bound) { return 3; }
        };

        Dice dice = new Dice(fake);

        dice.roll();

        assertAll(
                () -> assertEquals(8, dice.sum()),
                () -> assertTrue(dice.isDouble())
        );
    }

    @Test
    void testRollNonDouble() {
        Random fake = new Random() {
            private int call = 0;
            @Override
            public int nextInt(int bound) { return call++; }
        };

        Dice dice = new Dice(fake);

        dice.roll();

        assertAll(
                () -> assertEquals(3, dice.sum()),
                () -> assertFalse(dice.isDouble())
        );
    }
}
