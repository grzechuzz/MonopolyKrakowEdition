package game;

import model.player.Player;

import java.util.List;

public class GameEngine {
    private final GameContext gameContext;
    private final TurnHandler turnHandler;
    private final VictoryChecker victoryChecker;
    private int currentPlayer = 0;

    public GameEngine(GameContext gameContext, TurnHandler turnHandler, VictoryChecker victoryChecker) {
        this.gameContext = gameContext;
        this.turnHandler = turnHandler;
        this.victoryChecker = victoryChecker;
    }

    public void runGame() {
        List<Player> players = gameContext.getPlayers();
        while (players.size() > 1) {
            Player p = players.get(currentPlayer);

          if (p.getStatus().isEliminated()) {
              players.remove(currentPlayer);
              continue;
          }

          turnHandler.executeTurn(p);

          if (p.getStatus().isEliminated()) {
              players.remove(currentPlayer);
          } else if (victoryChecker.checkVictory(p, players)) {
              break;
          } else if(p.getStatus().getConsecutiveDoubles() == 0) {
              currentPlayer = (currentPlayer + 1) % players.size();
          }
        }

        Player winner = players.get(0);
        gameContext.getUserInteractionService().displayMessage("Koniec gry! Zwycięzca: " + winner.getNickname());
    }
}