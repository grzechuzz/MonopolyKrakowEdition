package model.dice;

import java.util.Random;

public class Dice {
    private final Random random;
    private int dice1;
    private int dice2;

    public Dice() {
        this.random = new Random();
    }

    public Dice(Random random) {
        this.random = random;
    }

    public void roll() {
        dice1 = 1 + random.nextInt(6);
        dice2 = 1 + random.nextInt(6);
    }

    public int sum() {
        return dice1 + dice2;
    }

    public boolean isDouble() {
        return dice1 == dice2;
    }

    @Override
    public String toString() {
        return "(K1, K2): (" + dice1 + ", " + dice2 + ")";
    }
}
