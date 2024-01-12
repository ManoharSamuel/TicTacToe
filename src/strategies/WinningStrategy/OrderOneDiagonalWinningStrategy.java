package strategies.WinningStrategy;

import models.Board;
import models.Move;

public class OrderOneDiagonalWinningStrategy implements OrderOneWinningStrategy{
    @Override
    public boolean checkWinner(Board board, Move move) {
        return false;
    }

    @Override
    public void handleUndo(Board board) {

    }
}
