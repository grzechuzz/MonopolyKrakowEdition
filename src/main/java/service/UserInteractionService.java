package service;

import model.field.Ownable;
import model.field.effect.PropertyField;
import model.player.Player;

import java.util.List;

public interface UserInteractionService {
    boolean confirmPurchase(String fieldName, int price);
    List<Ownable> propertiesToSell(Player player);
    PropertyField chooseFestivalField(Player player);
    int promptHouseCount(PropertyField field, int max);
    void displayMessage(String message);
}
