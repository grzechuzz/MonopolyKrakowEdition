package model.field;

import model.player.Player;
import game.GameContext;

public abstract class Field {
    private final String name;
    private final int position;

    public Field(String name, int position) {
        this.name = name;
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public int getPosition() {
        return position;
    }

    public abstract void executeEffect(Player player, GameContext gc);
}
