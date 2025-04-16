package model.player;

import model.field.Ownable;

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
        balance = 5000000;
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

    public void addBalance(int balance) {
        this.balance += balance;
    }

    public void subtractBalance(int balance) {
        this.balance -= balance;
    }

    public List<Ownable> getProperties() {
        return properties;
    }

    public void addProperty(Ownable field) {
        properties.add(field);
    }

    public void deleteProperty(Ownable field) {
        properties.remove(field);
    }

    public PlayerStatus getStatus() {
        return status;
    }
}
