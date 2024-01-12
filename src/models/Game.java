package models;

import exceptions.InvalidBoardDimensionException;
import exceptions.InvalidBotCountException;
import exceptions.InvalidPlayerCountException;
import exceptions.InvalidSymbolCountException;
import strategies.WinningStrategy.WinningStrategies;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Game {
    private List<Player> players;
    private List<Move> moves;
    private Player winner;
    private Board board;
    private int currentPlayerIndex;
    private GameState gameState;
    private List<WinningStrategies> winningStrategies;

    private Game(List<Player> players, int dimension, List<WinningStrategies> winningStrategies) {
        this.players = players;
        this.moves = new ArrayList<>();
        this.currentPlayerIndex = 0;
        this.board = new Board(dimension);
        this.winningStrategies = winningStrategies;
        this.gameState = GameState.INPROGRESS;
    }

    public GameState getGameState() {
        return this.gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public List<Move> getMoves() {
        return moves;
    }

    public void setMoves(List<Move> moves) {
        this.moves = moves;
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    public void setCurrentPlayerIndex(int currentPlayerIndex) {
        this.currentPlayerIndex = currentPlayerIndex;
    }

    public List<WinningStrategies> getWinningStrategies() {
        return winningStrategies;
    }

    public void setWinningStrategies(List<WinningStrategies> winningStrategies) {
        this.winningStrategies = winningStrategies;
    }

    public static Builder getBuilder() {
        return new Builder();
    }

    public void displayBoard() {
        board.displayBoard();
    }

    public void printResult() {
        if (gameState.equals(GameState.COMPLETED)) {
            System.out.println("Game has ended. Winner is "+getWinner().getName()+" .");
            System.out.println("Thanks for playing. Here is the final status of the board :");
        } else if (gameState.equals(GameState.DRAW)) {
            System.out.println("Game has ended in a draw because all the cells are filled.");
        }
    }

    public void makeMove() {
        Player player = players.get(currentPlayerIndex);
        Cell proposedCell = player.makeMove(board);
        if (!validateCell(proposedCell)) {
            System.out.println("Given values are out of bounds. Please try again.");
            return;
        }
        System.out.println("Move made at row : "+proposedCell.getRow()+" and col : "+proposedCell.getColumn());
        Cell cellInboard = board.getBoard().get(proposedCell.getRow()).get(proposedCell.getColumn());
        cellInboard.setCellState(CellState.FILLED);
        cellInboard.setPlayer(player);
        Move move = new Move(player, cellInboard);
        moves.add(move);
        if (checkGameWon(player, move)) {
            return;
        }
        if (checkGameDrawn()) {
            return;
        }
        currentPlayerIndex++;
        currentPlayerIndex %= players.size();
    }

    private boolean checkGameDrawn() {
        if (moves.size() == board.getDimension() * board.getDimension()) {
            this.gameState = GameState.DRAW;
            return true;
        }
        return false;
    }

    private boolean checkGameWon(Player player, Move move) {
        for (WinningStrategies winningStrategy : winningStrategies) {
            if (winningStrategy.checkWinner(board.getDimension(), move)) {
                this.gameState = GameState.COMPLETED;
                this.winner = player;
                return true;
            }
        }
        return false;
    }

    private boolean validateCell(Cell proposedCell) {
        int row = proposedCell.getRow();
        int column = proposedCell.getColumn();
        if (row < 0 || column < 0 || row > board.getDimension() || column > board.getDimension()) {
            return false;
        }
        return board.getBoard().get(row).get(column).getCellState().equals(CellState.EMPTY);
    }

    public void undoMove() {
        if (moves.isEmpty()) {
            System.out.println("No moves to undo.");
            return;
        }

        Move lastMove = moves.get(moves.size() - 1);
        for (WinningStrategies winningStrategy : winningStrategies) {
            winningStrategy.handleUndo(board.getDimension(),lastMove);
        }

        Cell lastCell = lastMove.getCell();
        lastCell.setCellState(CellState.EMPTY);
        lastCell.setPlayer(null);

        moves.remove(lastMove);

        currentPlayerIndex--;
        currentPlayerIndex += players.size();
        currentPlayerIndex %= players.size();
    }

    public static class Builder {
        private List<Player> players;
        private List<WinningStrategies> winningStrategies;
        private int dimension;

        public Builder setPlayers(List<Player> players) {
            this.players = players;
            return this;
        }

        public Builder setWinningStrategies(List<WinningStrategies> winningStrategies) {
            this.winningStrategies = winningStrategies;
            return this;
        }

        public Builder setDimension(int dimension) {
            this.dimension = dimension;
            return this;
        }

        private void validateTheParameters() {
            if (dimension < 3) {
                throw new InvalidBoardDimensionException("Board size should be at least 3.");
            }
            if (players.size() != dimension - 1) {
                throw new InvalidPlayerCountException("Player count should be equal to (board size - 1).");
            }

            int botCount = 0;
            Set<Symbol> symbolMap = new HashSet<>();
            for (Player player : players) {
                if (player.getPlayerType().equals(PlayerType.BOT)) {
                    botCount++;
                }
                symbolMap.add(player.getSymbol());
            }

            if (botCount > 1) {
                throw new InvalidBotCountException("Game can have a maximum of one bot only.");
            }

            if (symbolMap.size() != players.size()) {
                throw new InvalidSymbolCountException("All players should have unique symbols.");
            }
        }

        public Game build() {
            validateTheParameters();
            return new Game(players, dimension, winningStrategies);
        }
    }

}
