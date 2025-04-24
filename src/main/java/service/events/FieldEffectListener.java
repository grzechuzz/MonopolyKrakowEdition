package service.events;

import game.GameContext;
import model.player.Player;
import model.field.Field;

public class FieldEffectListener implements MovementListener {
    private final GameContext gameContext;

    public FieldEffectListener(GameContext gameContext) {
        this.gameContext = gameContext;
    }

    @Override
    public void onMove(Player player, int from, int to) {
        Field f = gameContext.getBoard().getField(to);
        gameContext.getUserInteractionService().showFieldInfo(f);
        f.executeEffect(player, gameContext);
    }
}
