package strategies.WinningStrategy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import models.Move;
import models.Player;
import models.Symbol;

public class OrderOneDiagonalWinningStrategy implements WinningStrategies{
    private final Map<Symbol, Integer> diagonalMapOne;
    private final Map<Symbol, Integer> diagonalMapTwo;
    public OrderOneDiagonalWinningStrategy(List<Player> players) {
        diagonalMapOne = new HashMap<>();
        diagonalMapTwo = new HashMap<>();
        for (Player player : players) {
            diagonalMapOne.put(player.getSymbol(),0);
            diagonalMapTwo.put(player.getSymbol(),0);
        }
    }
    @Override
    public boolean checkWinner(int dimension, Move move) {
        int row = move.getCell().getRow();
        int col = move.getCell().getColumn();
        Symbol symbol = move.getPlayer().getSymbol();
        if (row == col) {
            diagonalMapOne.put(symbol, diagonalMapOne.get(symbol) + 1);
        }
        if (row + col == dimension - 1) {
            diagonalMapTwo.put(symbol, diagonalMapTwo.get(symbol) + 1);
        }

        if (row == col) {
            if (diagonalMapOne.get(symbol) == dimension) {
                return true;
            }
        }
        if (row + col == dimension - 1) {
            if (diagonalMapTwo.get(symbol) == dimension) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void handleUndo(int dimension, Move move) {
        int row = move.getCell().getRow();
        int col = move.getCell().getColumn();
        Symbol symbol = move.getPlayer().getSymbol();
        if (row == col) {
            diagonalMapOne.put(symbol, diagonalMapOne.get(symbol) - 1);
        }
        if (row + col == dimension - 1) {
            diagonalMapTwo.put(symbol, diagonalMapTwo.get(symbol) - 1);
        }
    }
}
