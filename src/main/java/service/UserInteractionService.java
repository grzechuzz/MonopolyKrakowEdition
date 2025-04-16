package service;

import model.field.Ownable;
import model.field.PropertyField;
import model.player.Player;

import java.util.List;

public interface UserInteractionService {
    public boolean confirmPurchase(String fieldName, int price);
    public List<Ownable> propertiesToSell(Player player);
    public PropertyField chooseFestivalField(Player player);

}
