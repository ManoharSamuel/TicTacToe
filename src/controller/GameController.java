package controller;

import models.*;
import strategies.winningstrategy.WinningStrategy;
import strategies.winningstrategy.WinningStrategyFactory;

import java.util.List;

public class GameController {
    public Game createGame(int dimension, List<Player> players) {
        try{
            return Game.builder().setDimensions(dimension)
                    .setPlayers(players)
                    .setWinningStrategies(List.of(WinningStrategyFactory.getWinningStrategy(dimension)))
                    .build();
        } catch (Exception e) {
            System.out.println("Could not start the game. Something went wrong.");
        }
        return null;
    }

    public void displayBoard(Game game) {
        game.getBoard().printBoard();
    }

    public GameState gameState(Game game) {
        return game.getGameState();
    }

    public Move executeMove(Game game, Player player) {
        Move move = player.makeMove(game.getBoard());
        game.getMoves().add(move);
        return move;
    }

    public String getWinner(Game game) {
        return game.getWinner().getName();
    }

    public Player checkWinner(Game game, Move recentMove) {
        for (WinningStrategy winningStrategy : game.getWinningStrategies()) {
            if(winningStrategy.checkWinner(game.getBoard(), recentMove) != null) {
                return recentMove.getPlayer();
            }
        }
        return null;
    }
}
