package model.player;

import model.field.Ownable;
import model.field.PropertyField;
import utils.Rules;

import java.util.ArrayList;
import java.util.List;


public class Player {
    private final String nickname;
    private int position;
    private int balance;
    private final PlayerStatus status;
    private final List<Ownable> properties;

    public Player(String nickname) {
        this.nickname = nickname;
        position = 0;
        balance = Rules.START_BALANCE;
        properties = new ArrayList<>();
        status = new PlayerStatus();
    }

    public String getNickname() {
        return nickname;
    }

    public int getPosition() {
        return position;
    }

    public int getBalance() {
        return balance;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void addBalance(int amount) {
        if (amount < 0)
            throw new IllegalArgumentException("Amount cannot be negative!");
        balance += amount;
    }

    public void subtractBalance(int amount) {
        if (amount < 0)
            throw new IllegalArgumentException("Amount cannot be negative!");
        if (balance < amount)
            throw new IllegalStateException("Balance too low: can't do that operation.");
        balance -= amount;
    }

    public List<Ownable> getProperties() {
        return properties;
    }

    public void addProperty(Ownable field) {
        field.setOwner(this);
        properties.add(field);
    }

    public void deleteProperty(Ownable field) {
        field.setOwner(null);
        properties.remove(field);
        if (field instanceof PropertyField pf)
            pf.setFestivalActive(false);
    }

    public PlayerStatus getStatus() {
        return status;
    }
}
