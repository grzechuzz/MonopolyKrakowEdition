package service;

import io.GameIO;
import model.field.Ownable;
import model.field.PropertyField;
import model.player.Player;

import java.util.ArrayList;
import java.util.List;

public class ConsoleUserInteractionService implements UserInteractionService {
    private final GameIO io;

    public ConsoleUserInteractionService(GameIO io) {
        this.io = io;
    }

    @Override
    public boolean confirmPurchase(String fieldName, int price) {
        return io.askYesNo("Czy chcesz kupić pole " + fieldName + " za " + price + " PLN?");
    }

    @Override
    public List<Ownable> propertiesToSell(Player player) {
        io.displayMessage("Wybierz nieruchomości do sprzedaży.");
        List<Ownable> properties = player.getProperties();

        for (int i = 0; i < properties.size(); ++i)
            io.displayMessage(i + ") " + properties.get(i).getName() + " " + properties.get(i).calculateValue() + "PLN");

        io.displayMessage("Wpisz numery nieruchomości do sprzedaży (oddzielone spacją): ");
        List<Integer> indices = io.readInts();

        List<Ownable> toSell = new ArrayList<>();
        for (Integer index : indices) {
            if (index >= 0 && index < properties.size())
                toSell.add(properties.get(index));
        }

        return toSell;
    }

    @Override
    public PropertyField chooseFestivalField(Player player) {
        List<PropertyField> fields = new ArrayList<>();
        for (Ownable field : player.getProperties()) {
            if (field instanceof PropertyField)
                fields.add((PropertyField) field);
        }

        if (fields.isEmpty())
            return null;

        for (int i = 0; i < fields.size(); ++i) {
            io.displayMessage(i + ") " + fields.get(i).getName() + " " + fields.get(i).calculateValue() + " PLN");
        }

        io.displayMessage("Wybierz nieruchomość na której chcesz ustawić festiwal (CZYNSZ x2): ");
        int choice = io.readInt();

        if (choice >= 0 && choice < fields.size())
            return fields.get(choice);

        return null;
    }


    @Override
    public int promptHouseCount(PropertyField field, int max) {
        io.displayMessage("Ile domów chcesz postawić na polu " + field.getName() + " (0–" + max + ")?");
        int choice = io.readInt();

        return Math.max(0, Math.min(max, choice));
    }



    @Override
    public void displayMessage(String message) {
        io.displayMessage(message);
    }
}
