package service;

import model.chance.ChanceCard;

import java.util.List;
import java.util.Random;

public class CardService {
    private final List<ChanceCard> cards;
    private final Random random;

    public CardService(List<ChanceCard> cards, Random random) {
        this.cards = cards;
        this.random = random;
    }

    public ChanceCard draw() {
        int idx = random.nextInt(cards.size());
        return cards.get(idx);
    }
}
