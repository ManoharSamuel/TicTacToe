package controllers;

import java.util.List;
import models.Game;
import models.GameState;
import models.Player;
import strategies.WinningStrategy.WinningStrategies;

public class GameController {
    public Game createGame(List<Player> players, int dimension, List<WinningStrategies> winningStrategies) {
        return Game.getBuilder()
                .setDimension(dimension)
                .setPlayers(players)
                .setWinningStrategies(winningStrategies)
                .build();
    }

    public void displayBoard(Game game) {
        game.displayBoard();
    }

    public void undoMove(Game game) {
        game.undoMove();
    }

    public void makeMove(Game game) {
        game.makeMove();
    }

    public void printResult(Game game) {
        game.printResult();
    }

    public GameState getGameState(Game game) {
        return game.getGameState();
    }
}
