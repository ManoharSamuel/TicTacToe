package strategies.WinningStrategy;

import models.Move;

public interface WinningStrategies {
    boolean checkWinner(int dimension, Move move);
    void handleUndo(int dimension, Move move);
}
