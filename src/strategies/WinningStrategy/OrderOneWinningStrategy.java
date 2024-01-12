package strategies.WinningStrategy;

import models.Board;
import models.Move;

public interface OrderOneWinningStrategy {
    boolean checkWinner(Board board, Move move);
    void handleUndo(Board board);
}
