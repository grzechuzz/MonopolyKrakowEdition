package app;


import io.GameIO;
import service.*;
import io.*;
import model.dice.Dice;
import model.board.Board;
import model.chance.*;
import model.player.Player;
import game.*;

import java.util.List;
import java.util.Random;


public class Main {
    public static void main(String[] args) {
        GameIO io = new ConsoleIO();
        UserInteractionService ui = new ConsoleUserInteractionService(io);
        Dice dice = new Dice();
        Board board = new Board();

        PropertyTransactionService pts = new PropertyTransactionService(ui);
        TaxService tax = new TaxService(ui, pts);
        FestivalService fest = new FestivalService(ui);
        JailService jail = new JailService(dice, ui, pts);
        TravelService travel = new TravelService(ui, board);

        List<ChanceCard> deck = List.of(
                new BirthdayCard(),
                new GoToJailCard(),
                new MacheteFightCard(),
                new ReprivatisationCard(),
                new StationStairsCard(),
                new LoseMoneyCard(),
                new RecieveCardToExitJailCard()
        );
        CardService cards = new CardService(deck, new Random());

        List<Player> players = List.of(new Player("Grzech"),
                                       new Player("Goha"),
                                       new Player("Jaca"),
                                       new Player("Kox"));
        GameContext ctx = new GameContext.Builder()
                .pts(pts).ui(ui).tax(tax).jail(jail).travel(travel).festival(fest).card(cards).board(board).dice(dice).players(players).build();

        TurnHandler th = new TurnHandler(ctx);
        VictoryChecker vc = new VictoryChecker();
        GameEngine engine = new GameEngine(ctx, th, vc);

        engine.runGame();
    }
}
