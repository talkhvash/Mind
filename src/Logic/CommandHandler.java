package Logic;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class CommandHandler {
    private GameState gameState;
    private GameHandler gameHandler;
    private HashMap<String, DataOutputStream> doses;
    private String playerName;
    private String host;

    public CommandHandler(HashMap<String, DataOutputStream> doses, String host) {
        this.doses = doses;
        this.host = host;
    }

    public String handle(String str) {
        String[] commands = str.split(" ");
        gameState = new GameState();
        Player player = gameHandler.getPlayer(commands[0]);
        gameHandler = new GameHandler(gameState,doses);


        try {
            if (gameState.getGameResult().equals(GameResult.IN)) {
                switch (commands[1]) {
                    case "start":
                        try {
                            if (commands[0].equals(host)){
                                gameHandler.initializePlayers(Integer.parseInt(commands[2]));
                            }
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                        break;
                    default:
                        if (player != null){
                            gameHandler.addPlayer(new Player(commands[1], commands[0]));
                        }
                }
            } else {
                switch (commands[1]) {
                    case "ninja":
                        gameHandler.requestNinja(player);
                        for (Player player1 : gameState.getPlayers().values()){
                            gameHandler.printRequest(player1,player);
                        }
                        break;
                    case "yes":
                        gameHandler.answerNinja(player,true);
                        for (Player player1 : gameState.getPlayers().values()){
                            gameHandler.printAnswerToRequest(player1,player,true);
                        }
                        break;
                    case "no":
                        gameHandler.answerNinja(player,false);
                        for (Player player1 : gameState.getPlayers().values()){
                            gameHandler.printAnswerToRequest(player1,player,false);
                        }
                        break;
                    default:
                        try {
                            if (gameHandler.putCard(player, Integer.parseInt(commands[1]))) {
                                for (Player player1 : gameState.getPlayers().values()){
                                    gameHandler.print(player1);
                                }
                            } else if (gameHandler.putCard(player, Integer.parseInt(commands[1])) == null) {
                                gameHandler.printDoNotHave(player);
                            } else if (!gameHandler.putCard(player, Integer.parseInt(commands[1]))) {
                                for (Player player1 : gameState.getPlayers().values()){
                                    gameHandler.printWrongPlayed(player1,player);
                                }
                            }
                        } catch (NumberFormatException | IOException e) { }
                }
            }
        } catch (NullPointerException | IOException e) {
            e.printStackTrace();
        }
        return "";
    }

}
