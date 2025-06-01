package service;

import io.GameIO;
import model.player.Player;
import model.field.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    public boolean confirmHotelUpgrade(PropertyField field, int price) {
        return io.askYesNo("Zamienić 3 domy na HOTEL na polu " + field.getName() + " za " + price + " PLN?");
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
    public Ownable chooseTravelDestination(List<Ownable> fields) {
        io.displayMessage("Wybierz pole, na które chcesz podróżować.");
        for (int i = 0; i < fields.size(); ++i)
            io.displayMessage(i + ") " + fields.get(i).getName());

        io.displayMessage("Wybierz numer pola, na które chcesz przejść: ");
        int choice = io.readInt();

        if (choice < 0 || choice >= fields.size())
            return null;

        return fields.get(choice);
    }

    @Override
    public PropertyField chooseFieldToForceSell(Player player, List<PropertyField> fields) {
        io.displayMessage(player.getNickname() + ", wybierz jedno pole przeciwnika do wymuszonej sprzedaży");
        for (int i = 0; i < fields.size(); ++i) {
            PropertyField pf = fields.get(i);
            io.displayMessage(i + ") " + pf.getName() + " (wartość: " + pf.calculateValue() + " PLN)");
        }
        int choice = io.readInt();
        if (choice < 0 || choice >= fields.size())
            return null;

        return fields.get(choice);
    }

    @Override
    public int promptHouseCount(PropertyField field, int max) {
        io.displayMessage("Ile domów chcesz postawić na polu " + field.getName() + " (0–" + max + ")?");
        int choice = io.readInt();

        return Math.max(0, Math.min(max, choice));
    }

    @Override
    public int chooseJailOption() {
        io.displayMessage("JESTES W WIĘZIENIU! Wybierz opcję: ");
        io.displayMessage("1) Wpłać 500 000 PLN, by wyjśc.");
        io.displayMessage("2) Wyrzuć dublet");
        io.displayMessage("3) Użyj karty wyjścia z więzienia");
        io.displayMessage("Podaj wybór: ");
        int choice = io.readInt();

        if (!(choice >= 1 && choice <= 3))
            choice = 2;

        return choice;
    }

    @Override
    public void displayMessage(String message) {
        io.displayMessage(message);
    }

    @Override
    public void showPlayerStatus(Player p) {
        String props = p.getProperties().stream()
                .map(Ownable::getName)
                .collect(Collectors.joining(", "));
        displayMessage(String.format("[%s] $%,d | pole %d | nieruchomości: %s",
                p.getNickname(), p.getBalance(), p.getPosition(),
                props.isEmpty() ? "—" : props));
    }

    @Override
    public void showFieldInfo(Field field) {
        StringBuilder sb = new StringBuilder("➜ Pole: " + field.getName());

        if (field instanceof PropertyField pf) {
            if (pf.getOwner() == null) {
                sb.append(" | wolne | cena: ").append(pf.getPrice()).append(" PLN");
            } else {
                sb.append(" | właściciel: ").append(pf.getOwner().getNickname());

                if (pf.hasHotel()) {
                    sb.append(" | HOTEL");
                } else {
                    sb.append(" | domy: ").append(pf.getHousesCount());
                }

                int rent = pf.calculateRent();
                sb.append(" | czynsz: ").append(rent).append(" PLN");

                if (pf.getOwner() != null && !pf.hasHotel()) {
                    int buyout = (int)(pf.calculateValue() * 1.5);
                    sb.append(" | odkup: ").append(buyout).append(" PLN");
                }

                if (pf.isFestivalActive()) {
                    sb.append(" | FESTIWAL x2");
                }
            }
        } else if (field instanceof ResortField rf) {
            sb.append(" | resort | cena: ").append(rf.getPrice())
                    .append(" | czynsz: ").append(rf.calculateRent());
        }  else if (field instanceof SpecialField sf) {
        if (sf.getOwner() == null) {
            sb.append(" | wolne | cena: ").append(sf.getPrice()).append(" PLN");
        } else {
            sb.append(" | specjalne | cena: ").append(sf.getPrice())
                    .append(" | czynsz: ").append(sf.calculateRent()).append(" PLN");
            }
        }

        io.displayMessage(sb.toString());
    }

    @Override
    public List<Player> askForPlayers(int howMany) {
        List<Player> players = new ArrayList<>();
        for (int i = 1; i <= howMany; i++) {
            io.displayMessage("Podaj nick gracza " + i + ": ");
            String nick = io.readString().trim();
            if (nick.isBlank()) nick = "Player" + i;
            players.add(new Player(nick));
        }
        return players;
    }

    @Override
    public void waitForRoll(Player player) {
        io.waitForEnter("Tura gracza " + player.getNickname() + ". Wcisnij ENTER, by rzucić kostkami");
    }
}
