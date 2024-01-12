package strategies.BotStrategy;

import models.Board;
import models.Cell;
import models.CellState;

public class EasyBotPlayingStrategy implements BotPlayingStrategy{
    @java.lang.Override
    public Cell makeMove(Board board) {
        for (int i = 0; i < board.getDimension(); ++i) {
            for (int j = 0; j < board.getDimension(); ++j) {
                if (board.getBoard().get(i).get(j).getCellState().equals(CellState.EMPTY)) {
                    return new Cell(i, j);
                }
            }
        }
        return null;
    }
}
