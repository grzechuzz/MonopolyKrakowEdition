package model.chance;

import game.GameContext;
import model.player.Player;
import utils.Rules;

public class ShineClubCard implements ChanceCard {
    @Override
    public String getDescription() {
        return "Jako zagorzały fan muzyki elektronicznej odwiedzasz Shine'a. Jesteś zawiedziony niskim poziomem DJ'a " +
                "i wszczynasz awanturę na skutek której zostajesz wyrzucony z klubu. Zapłać 100 000 PLN.";
    }

    @Override
    public void apply(Player player, GameContext gc) {
        gc.getPropertyTransactionService().payToBank(player, Rules.CARD_SHINE_CLUB_FINE);
    }
}
