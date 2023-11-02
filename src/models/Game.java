package models;

import exception.DuplicateSymbolException;
import exception.InvalidBotCountException;
import exception.InvalidDimensionsException;
import exception.InvalidNoOfPlayersException;
import strategies.winningstrategy.WinningStrategy;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Game {
    private List<Player> players;
    private Board board;
    private List<Move> moves;
    private Player winner;
    private int nextPlayerIndex;
    private List<WinningStrategy> winningStrategies;
    private GameState gameState;

    private Game(List<Player> players, Board board, List<WinningStrategy> winningStrategies) {
        this.players = players;
        this.board = board;
        this.moves = new ArrayList<>();
        this.nextPlayerIndex = 0;
        this.winningStrategies = winningStrategies;
        this.gameState = GameState.IN_PROGRESS;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Board getBoard() {
        return board;
    }

    public List<Move> getMoves() {
        return moves;
    }

    public Player getWinner() {
        return winner;
    }

    public List<WinningStrategy> getWinningStrategies() {
        return winningStrategies;
    }

    public GameState getGameState() {
        return gameState;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private List<Player> players;
        private int dimensions;
        private List<WinningStrategy> winningStrategies;

        private Builder() {
            this.players = new ArrayList<>();
            this.dimensions = 0;
            this.winningStrategies = new ArrayList<>();
        }

        public Builder setPlayers(List<Player> players) {
            this.players = players;
            return this;
        }

        public Builder setDimensions(int dimensions) {
            this.dimensions = dimensions;
            return this;
        }

        public Builder setWinningStrategies(List<WinningStrategy> winningStrategies) {
            this.winningStrategies = winningStrategies;
            return this;
        }

        public void addPlayers(Player player) {
            players.add(player);
        }

        public void addWinningStrategies(WinningStrategy winningStrategy) {
            winningStrategies.add(winningStrategy);
        }

        private void validateBotCount() {
            int botCount = 0;
            for(Player player : players) {
                if (player.getPlayerType().equals(PlayerType.BOT)) {
                    botCount++;
                    if (botCount > 1) {
                        throw new InvalidBotCountException("Bot count exceeded the permitted value of 1");
                    }
                }
            }
        }

        private void validateNoOfPlayers() {
            if (dimensions-1 != players.size()) {
                throw new InvalidNoOfPlayersException("Number of Players should be 1 less than the dimensions");
            }
        }

        private void validateDimensions() {
            if (dimensions < 3 || dimensions > 10) {
                throw new InvalidDimensionsException("Dimensions should be >= 3 and < 11");
            }
        }

        private void validateUniqueSymbolForAllPlayers() {
            Set<Character> mySet = new HashSet<>();
            for(Player player : players) {
                mySet.add(player.getSymbol().getSymbolChar());
            }

            if (mySet.size() != players.size()) {
                throw new DuplicateSymbolException("All players should have unique symbols");
            }
        }

        private void validate() {
            validateBotCount();
            validateDimensions();
            validateNoOfPlayers();
            validateUniqueSymbolForAllPlayers();
        }

        public Game build() {
            validate();
            return new Game(players, new Board(dimensions), winningStrategies);
        }
    }
}
