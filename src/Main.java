import controllers.GameController;
import java.util.List;
import java.util.Scanner;
import models.*;
import strategies.WinningStrategy.OrderOneColumnWinningStrategy;
import strategies.WinningStrategy.OrderOneDiagonalWinningStrategy;
import strategies.WinningStrategy.OrderOneRowWinningStrategy;
import strategies.WinningStrategy.WinningStrategies;

public class Main {
    public static void main(String[] args) {
        GameController gameController = new GameController();
        Scanner scanner = new Scanner(System.in);
        int dimension = 3;
        List<Player> players = List.of(
            new Player("HumanPlayer", new Symbol('X'), PlayerType.HUMAN),
            new Bot("BotPlayer", new Symbol('O'), BotDifficultyLevel.EASY)
        );
        List<WinningStrategies> winningStrategies = List.of(
                new OrderOneRowWinningStrategy(players),
                new OrderOneColumnWinningStrategy(players),
                new OrderOneDiagonalWinningStrategy(players)
        );

        Game game = gameController.createGame(players, dimension, winningStrategies);

        while(gameController.getGameState(game).equals(GameState.INPROGRESS)) {
            System.out.println("Here is the current state of the board :");
            gameController.displayBoard(game);
            System.out.println("-----------------------------");
            System.out.println("Do you want to Undo (Y/N) ?");
            String character = scanner.next();
            if (character.equalsIgnoreCase("Y")) {
                gameController.undoMove(game);
            } else if(character.equalsIgnoreCase("N")){
                gameController.makeMove(game);
            } else {
                System.out.println("Please enter a valid value of Y or N");
            }
        }

        gameController.printResult(game);
        gameController.displayBoard(game);
    }
}