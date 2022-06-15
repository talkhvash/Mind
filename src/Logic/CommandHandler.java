package Logic;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class CommandHandler {
    private GameState gameState;
    private GameHandler gameHandler;
    private String host;

    public CommandHandler(HashMap<String, DataOutputStream> doses, String host) {
        this.host = host;
        gameState = new GameState();
        gameHandler = new GameHandler(gameState, doses);
    }

    public void handle(String str) {
        String[] commands = str.split(" ");
        try {
            Player player = gameHandler.getPlayer(commands[0]);
            if (gameState.getGameResult().equals(GameResult.IN)) {
                switch (commands[1]) {
                    case "start":
                        try {
                            if (commands[0].equals(host)) gameHandler.start(Integer.parseInt(commands[2]));
                        } catch (NumberFormatException e) {e.printStackTrace();}
                        break;
                    default:
                        if (player == null) gameHandler.addPlayer(new Player(commands[1], commands[0]));
                }
            } else {
                switch (commands[1]) {
                    case "ninja": gameHandler.requestNinja(player);
                        break;
                    case "yes": gameHandler.answerNinja(player, true);
                        break;
                    case "no": gameHandler.answerNinja(player, false);
                        break;
                    default:
                        try {gameHandler.putCard(player, Integer.parseInt(commands[1]));
                        } catch (NumberFormatException e) {e.printStackTrace();}
                }
            }
        } catch (NullPointerException | IOException e) {e.printStackTrace();
        }

    }

}
