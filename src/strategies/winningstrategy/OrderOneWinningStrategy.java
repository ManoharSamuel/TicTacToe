package strategies.winningstrategy;

import exception.GameDrawnException;
import models.Board;
import models.Move;
import models.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderOneWinningStrategy implements WinningStrategy{
    private int dimension;
    private int symbolsAdded;
    private List<Map<Character, Integer>> rowSymbols = new ArrayList<>();
    private List<Map<Character, Integer>> columnSymbols = new ArrayList<>();
    private Map<Character, Integer> topLeftDiagonalSymbols = new HashMap<>();
    private Map<Character, Integer> topRightDiagonalSymbols = new HashMap<>();
    private Map<Character, Integer> cornerSymbols = new HashMap<>();

    public OrderOneWinningStrategy(int dimension) {
        this.dimension = dimension;
        for (int i = 0; i < dimension; i++) {
            rowSymbols.add(new HashMap<>());
            columnSymbols.add(new HashMap<>());
        }
    }

    public boolean isTopLeftDiagonal(int row, int column) {
        return row == column;
    }

    public boolean isTopRightDiagonal(int row, int column) {
        return (row + column) == dimension - 1;
    }

    public boolean isCornerCell(int row, int column) {
        if (row == 0 || row == dimension-1) {
            return column == 0 || column == dimension-1;
        }
        return false;
    }

    private boolean checkForRows(int row, char symbol) {
        rowSymbols.get(row).put(symbol, rowSymbols.get(row).getOrDefault(symbol,0)+1);
        return rowSymbols.get(row).get(symbol) == dimension;
    }

    private boolean checkForColumns(int col, char symbol) {
        columnSymbols.get(col).put(symbol, columnSymbols.get(col).getOrDefault(symbol,0)+1);
        return columnSymbols.get(col).get(symbol) == dimension;
    }

    private boolean checkForTopLeftDiagonal(char symbol) {
        topLeftDiagonalSymbols.put(symbol, topLeftDiagonalSymbols.getOrDefault(symbol,0) + 1);
        return topLeftDiagonalSymbols.get(symbol) == dimension;
    }

    private boolean checkForBottomRightDiagonal(char symbol) {
        topRightDiagonalSymbols.put(symbol, topRightDiagonalSymbols.getOrDefault(symbol,0) + 1);
        return topRightDiagonalSymbols.get(symbol) == dimension;
    }

    private boolean checkForCorner(char symbol) {
        cornerSymbols.put(symbol, cornerSymbols.getOrDefault(symbol, 0) + 1);
        return cornerSymbols.get(symbol) == dimension;
    }

    @Override
    public Player checkWinner(Board board, Move lastMove) {
        symbolsAdded++;
        Player player = lastMove.getPlayer();
        char symbol = player.getSymbol().getSymbolChar();
        int row = lastMove.getCell().getRow();
        int col = lastMove.getCell().getColumn();

        if (checkForRows(row, symbol) || checkForColumns(col, symbol)) {
            return player;
        } else if (isTopLeftDiagonal(row, col) && checkForTopLeftDiagonal(symbol)) {
            return player;
        } else if (isTopRightDiagonal(row, col) && checkForBottomRightDiagonal(symbol)) {
            return player;
        } else if (isCornerCell(row, col) && checkForCorner(symbol)) {
            return player;
        }

        if(symbolsAdded == dimension*dimension) {
            board.printBoard();
            throw new GameDrawnException("Game is drawn as all cells are filled");
        }
        return null;
    }
}
