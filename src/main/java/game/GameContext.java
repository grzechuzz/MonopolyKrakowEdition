package game;

import model.board.Board;
import model.dice.Dice;
import model.player.Player;
import service.*;

import java.util.List;
import java.util.Objects;

public class GameContext {
    private final PropertyTransactionService propertyTransactionService;
    private final UserInteractionService userInteractionService;
    private final TaxService taxService;
    private final JailService jailService;
    private final TravelService travelService;
    private final CardService cardService;
    private final FestivalService festivalService;
    private final MovementService movementService;
    private final Board board;
    private final Dice dice;
    private final List<Player> players;

    private GameContext(Builder b) {
        this.propertyTransactionService = Objects.requireNonNull(b.propertyTransactionService);
        this.userInteractionService = Objects.requireNonNull(b.userInteractionService);
        this.taxService = Objects.requireNonNull(b.taxService);
        this.jailService = Objects.requireNonNull(b.jailService);
        this.travelService = Objects.requireNonNull(b.travelService);
        this.cardService = Objects.requireNonNull(b.cardService);
        this.festivalService = Objects.requireNonNull(b.festivalService);
        this.movementService = Objects.requireNonNull(b.movementService);
        this.board = Objects.requireNonNull(b.board);
        this.dice = Objects.requireNonNull(b.dice);
        this.players = Objects.requireNonNull(b.players);
    }

    public static class Builder {
        private PropertyTransactionService propertyTransactionService;
        private UserInteractionService userInteractionService;
        private TaxService taxService;
        private JailService jailService;
        private TravelService travelService;
        private CardService cardService;
        private FestivalService festivalService;
        private MovementService movementService;
        private Board board;
        private Dice dice;
        private List<Player> players;

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

        public Builder movement(MovementService ms) {
            movementService = ms;
            return this;
        }

        public Builder board(Board board) {
            this.board = board;
            return this;
        }

        public Builder dice(Dice dice) {
            this.dice = dice;
            return this;
        }

        public Builder players(List<Player> players) {
            this.players = players;
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

    public MovementService getMovementService() {
        return movementService;
    }

    public Board getBoard() {
        return board;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Dice getDice() {
        return dice;
    }
}
