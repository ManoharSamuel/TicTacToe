package strategies.WinningStrategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import models.Move;
import models.Player;
import models.Symbol;

public class OrderOneColumnWinningStrategy implements WinningStrategies{
    private final List<Map<Symbol, Integer>> colMap;
    public OrderOneColumnWinningStrategy(List<Player> players) {
        colMap = new ArrayList<>();
        for (int i = 0; i < players.size()+1; i++) {
            colMap.add(new HashMap<>());
            for (Player player : players) {
                colMap.get(i).put(player.getSymbol(),0);
            }
        }
    }
    @Override
    public boolean checkWinner(int dimension, Move move) {
        int col = move.getCell().getColumn();
        Symbol symbol = move.getPlayer().getSymbol();
        colMap.get(col).put(symbol, colMap.get(col).get(symbol) + 1);
        return colMap.get(col).get(symbol) == dimension;
    }

    @Override
    public void handleUndo(int dimension, Move move) {
        int col = move.getCell().getColumn();
        Symbol symbol = move.getPlayer().getSymbol();
        colMap.get(col).put(symbol, colMap.get(col).get(symbol) - 1);
    }
}
