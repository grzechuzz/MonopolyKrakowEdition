package game;

import service.*;

public class GameContext {
    private final PropertyTransactionService propertyTransactionService;
    private final UserInteractionService userInteractionService;
    private final TaxService taxService;
    private final JailService jailService;
    private final TravelService travelService;
    private final CardService cardService;
    private final FestivalService festivalService;

    private GameContext(Builder b) {
        this.propertyTransactionService = b.propertyTransactionService;
        this.userInteractionService = b.userInteractionService;
        this.taxService = b.taxService;
        this.jailService = b.jailService;
        this.travelService = b.travelService;
        this.cardService = b.cardService;
        this.festivalService = b.festivalService;
    }

    public static class Builder {
        private PropertyTransactionService propertyTransactionService;
        private UserInteractionService userInteractionService;
        private TaxService taxService;
        private JailService jailService;
        private TravelService travelService;
        private CardService cardService;
        private FestivalService festivalService;

        public Builder pts(PropertyTransactionService pts) {
            propertyTransactionService = pts;
            return this;
        }

        public Builder ui(UserInteractionService ui) {
            userInteractionService = ui;
            return this;
        }

        public Builder tax(TaxService ts) {
            taxService = ts;
            return this;
        }

        public Builder jail(JailService js) {
            jailService = js;
            return this;
        }

        public Builder travel(TravelService ts) {
            travelService = ts;
            return this;
        }

        public Builder card(CardService cs) {
            cardService = cs;
            return this;
        }

        public Builder festival(FestivalService fs) {
            festivalService = fs;
            return this;
        }

        public GameContext build() {
            return new GameContext(this);
        }
    }

    public UserInteractionService getUserInteractionService() {
        return userInteractionService;
    }

    public PropertyTransactionService getPropertyTransactionService() {
        return propertyTransactionService;
    }

    public TaxService getTaxService() {
        return taxService;
    }

    public JailService getJailService() {
        return jailService;
    }

    public TravelService getTravelService() {
        return travelService;
    }

    public CardService getCardService() {
        return cardService;
    }

    public FestivalService getFestivalService() {
        return festivalService;
    }
}
