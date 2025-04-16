package model.field;

import model.player.Player;

public class Field {
    private final String name;
    private final int position;
    private final FieldEffect effect;

    public Field(String name, int position, FieldEffect effect) {
        this.name = name;
        this.position = position;
        this.effect = effect != null ? effect : new NoActionEffect();
    }

    public String getName() {
        return name;
    }

    public int getPosition() {
        return position;
    }

    public void executeEffect(Player player, GameManager gm) {
        effect.apply(player, gm);
    }
}
