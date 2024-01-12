package models;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private int dimension;
    private List<List<Cell>> board;

    public Board(int dimension) {
        this.dimension = dimension;
        for (int i = 0; i < dimension; ++i) {
            board.add(new ArrayList<>());
            for (int j = 0; j < dimension; ++j) {
                board.get(i).add(new Cell(i,j));
            }
        }
    }

    public void displayBoard() {
        for (int i = 0; i < dimension; ++i) {
            System.out.print("| ");
            for (int j = 0; j < dimension; ++j) {
                if (board.get(i).get(j).getCellState().equals(CellState.FILLED)) {
                    System.out.print(board.get(i).get(j).getPlayer().getSymbol().getSymbol()+" |");
                } else {
                    System.out.println(" |");
                }
            }
        }
    }

    public int getDimension() {
        return dimension;
    }

    public void setDimension(int dimension) {
        this.dimension = dimension;
    }

    public List<List<Cell>> getBoard() {
        return board;
    }

    public void setBoard(List<List<Cell>> board) {
        this.board = board;
    }
}
