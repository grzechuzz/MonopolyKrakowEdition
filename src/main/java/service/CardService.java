package service;

import model.chance.ChanceCard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class CardService {
    private final List<ChanceCard> cards;
    private final List<ChanceCard> cardsDeck;
    private final Random random;

    public CardService(List<ChanceCard> cards, Random random) {
        this.cards = cards;
        this.random = random;
        this.cardsDeck = new ArrayList<>();
        reshuffle();
    }

    public ChanceCard draw() {
       if (cardsDeck.isEmpty())
           reshuffle();

       return cardsDeck.removeFirst();
    }

    private void reshuffle() {
        cardsDeck.clear();
        cardsDeck.addAll(cards);
        Collections.shuffle(cardsDeck, random);
    }
}
