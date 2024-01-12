package models;

import java.util.Scanner;

public class Human extends Player{
    Scanner scanner;

    public Human(String name, Symbol symbol) {
        super(name, symbol, PlayerType.HUMAN);
        scanner = new Scanner(System.in);

    }

    @Override
    public Move makeMove(Board board) {
        System.out.println("Enter the value for row(starting from 0) :");
        int row = scanner.nextInt();

        System.out.println("Enter the value for column(starting from 0) :");
        int column = scanner.nextInt();

        if(row < 0 || column < 0 || row >= board.getDimension() || column >= board.getDimension()) {
            System.out.println(" Value are out of bounds. Please try again with valid values");
        }
        return new Move(new Human(name,symbol) ,new Cell(row, column));
    }


}
