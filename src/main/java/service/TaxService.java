package service;

import model.field.Ownable;
import model.player.Player;

import java.util.List;

public class TaxService {
    private final UserInteractionService ui;
    private final PropertyTransactionService pts;

    public TaxService(UserInteractionService ui, PropertyTransactionService pts) {
        this.ui = ui;
        this.pts = pts;
    }

    public void payTax(Player player) {
        List<Ownable> fields = player.getProperties();
        if (fields.isEmpty())
            return;

        int toPay = 0;
        for (Ownable o : fields)
            toPay += o.calculateValue();

        int tax = (int)(toPay * 0.1);

        boolean paid = pts.payToBank(player, tax);
        if (paid)
            ui.displayMessage("Gracz " + player.getNickname() + " zapłacił " + tax + " PLN podatku.");
    }
}
