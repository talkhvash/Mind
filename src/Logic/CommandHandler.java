package Logic;

import java.util.LinkedList;

public class CommandHandler {
    private Game game;
    private String playerName;

    public CommandHandler() {
        game = new Game();
    }

    public String handle(String str) {
        String[] commands = str.split(" ");
        Player player = game.getPlayer(commands[0]);

        try {
            if (!game.isStarted()) {
                switch (commands[1]) {
                    case "start":
                        try {
                            game.initializePlayers(Integer.parseInt(commands[2]));
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                        break;
                    default:
                        if (player != null) game.addPlayer(new Player(commands[1], commands[0]));
                }
            } else {
                switch (commands[1]) {
                    case "ninja":
                        break;
                    case "yes":
                    case "no":

                }
            }
        } catch (NullPointerException e) {

            e.printStackTrace();
        }
        return "";
    }
}
