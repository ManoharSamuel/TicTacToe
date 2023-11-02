import controller.GameController;
import models.*;
import strategies.botplayingstrategy.BotPlayingStrategy;
import strategies.botplayingstrategy.BotPlayingStrategyFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class TicTacToeGame {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        GameController gameController = new GameController();

        System.out.println("Please enter dimension of the board :");
        int dimension = sc.nextInt();

        System.out.println("Will there be a bot in the game ? Y/N");
        String isBotPresent = sc.next();

        List<Player> players = new ArrayList<>();
        int iteratorCount = dimension-1;

        if(isBotPresent.equals("Y")) {
            iteratorCount = dimension-2;
        }

        for (int i = 0; i < iteratorCount; i++) {
            System.out.println("Enter the name of player "+(i+1)+" :");
            String playerName = sc.next();

            System.out.println("Enter the symbol for player "+(i+1)+" :");
            char playerSymbol = sc.next().charAt(0);

            players.add(new Player(new Symbol(playerSymbol), playerName, PlayerType.HUMAN));
        }

        if (isBotPresent.equals("Y")) {
            System.out.println("Enter the name of bot ");
            String botName = sc.next();

            System.out.println("Enter the symbol for bot ");
            char playerSymbol = sc.next().charAt(0);

            BotDifficultyLevel botDifficultyLevel = BotDifficultyLevel.EASY;

            Bot bot = new Bot(new Symbol(playerSymbol), botName, PlayerType.BOT,
                              BotPlayingStrategyFactory.getBotPlayingStrategy(botDifficultyLevel),botDifficultyLevel);

            players.add(bot);
        }

        Collections.shuffle(players);

        Game game = gameController.createGame(dimension, players);
        int playerIndex = 0;

        while (game.getGameState().equals(GameState.IN_PROGRESS)) {
            System.out.println("Current Board Status");
            gameController.displayBoard(game);
            playerIndex++;
            playerIndex = playerIndex%players.size();
            Move movePlayed = gameController.executeMove(game, players.get(playerIndex));
            Player winner = gameController.checkWinner(game, movePlayed);
            if (winner != null) {
                gameController.displayBoard(game);
                System.out.println("Winner is "+winner.getName());
                break;
            }
        }
    }
}
