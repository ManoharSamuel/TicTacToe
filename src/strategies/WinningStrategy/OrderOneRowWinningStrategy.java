package strategies.WinningStrategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import models.Move;
import models.Player;
import models.Symbol;

public class OrderOneRowWinningStrategy implements WinningStrategies{
    private final List<Map<Symbol, Integer>> rowMap;
    public OrderOneRowWinningStrategy(List<Player> players) {
        rowMap = new ArrayList<>();
        for (int i = 0; i < players.size()+1; i++) {
            rowMap.add(new HashMap<>());
            for (Player player : players) {
                rowMap.get(i).put(player.getSymbol(),0);
            }
        }
    }
    @Override
    public boolean checkWinner(int dimension, Move move) {
        int row = move.getCell().getRow();
        Symbol symbol = move.getPlayer().getSymbol();
        rowMap.get(row).put(symbol, rowMap.get(row).get(symbol) + 1);
        return rowMap.get(row).get(symbol) == dimension;
    }

    @Override
    public void handleUndo(int dimension, Move move) {
        int row = move.getCell().getRow();
        Symbol symbol = move.getPlayer().getSymbol();
        rowMap.get(row).put(symbol, rowMap.get(row).get(symbol) - 1);
    }
}
