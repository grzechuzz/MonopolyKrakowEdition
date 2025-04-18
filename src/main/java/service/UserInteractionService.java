package service;

import model.field.Ownable;
import model.field.PropertyField;
import model.player.Player;

import java.util.List;

public interface UserInteractionService {
    boolean confirmPurchase(String fieldName, int price);
    List<Ownable> propertiesToSell(Player player);
    PropertyField chooseFestivalField(Player player);
    Ownable chooseTravelDestination(List<Ownable> fields);
    int promptHouseCount(PropertyField field, int max);
    int chooseJailOption();
    void displayMessage(String message);
}
