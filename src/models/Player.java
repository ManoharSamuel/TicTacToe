package models;

import java.util.Scanner;

public class Player {
    private static long playerId;
    private Symbol symbol;
    private String name;
    private PlayerType playerType;
    private Scanner scanner;

    public Player(Symbol symbol, String name, PlayerType playerType) {
        playerId++;
        this.symbol = symbol;
        this.name = name;
        this.playerType = playerType;
        this.scanner = new Scanner(System.in);
    }

    public Move makeMove(Board board) {
        System.out.println("Please enter row for the move :");
        int row = scanner.nextInt();

        System.out.println("Please enter column for the move :");
        int column = scanner.nextInt();

        board.getBoard().get(row).get(column).setPlayer(this);
        board.getBoard().get(row).get(column).setCellState(CellState.FILLED);

        return new Move(new Cell(row, column, this), this);
    }

    public long getPlayerId() {
        return playerId;
    }

    public void setSymbol(Symbol symbol) {
        this.symbol = symbol;
    }

    public Symbol getSymbol() {
        return symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PlayerType getPlayerType() {
        return playerType;
    }

    public void setPlayerType(PlayerType playerType) {
        this.playerType = playerType;
    }

    public Scanner getScanner() {
        return scanner;
    }

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }
}
