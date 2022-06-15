package Logic;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

public class GameHandler {
    private final GameState gameState;
    private final HashMap<String, DataOutputStream> dos;

    public GameHandler(GameState gameState, HashMap<String, DataOutputStream> dos) {
        this.gameState = gameState;
        this.dos = dos;
    }

    public void start(int playersCount) {
        initializeFields(playersCount);
        initializePlayers(playersCount);
        shuffleCards();
        printState();
    }

    private void initializeFields(int playersCount) {
        gameState.setHeart(playersCount);
        gameState.setPlayersCount(playersCount);
        gameState.setRealPlayerCount(gameState.getPlayers().size());
        gameState.setGameResult(GameResult.STARTED);
    }

    private void initializePlayers(int playersCount) {
        int botCounts = playersCount - gameState.getPlayers().size();
        for (int i = 0; i < botCounts; i++) {
            Bot bot = new Bot("bot" + i," bot " + i,this, i+1);
            gameState.getPlayers().put(bot.getId(), bot);
            gameState.getBots().add(bot);
        }
    }

    //
    private void shuffleCards() {
        Random random = new Random();
        LinkedList<Integer> cards = new LinkedList<>();
        for (int i = 1; i < 100; i++) {
            int index = random.nextInt(cards.size() + 1);
            cards.add(index, i);
        }
        for (Player player : gameState.getPlayers().values()) {
            for (int j = 0; j < gameState.getLevel(); j++) {
                player.addCard(cards.getFirst());
                cards.removeFirst();
            }
        }
        gameState.setLastCard(0);
    }

    public synchronized void putCard(Player player, Integer number) {
        for (Bot bot : gameState.getBots()) {
            if (player != bot) {
                try {
                    bot.getThread().wait();
                } catch (Exception e) {}
            }
        }

        if (player.getCards().contains(number)) {
            gameState.stopBots();
            //System.out.println("stopped");
            player.removeCard(number);
            gameState.setLastCard(number);
            checkPlayedCard(player, number);
            checkPlayedTurn();
            GameResult gameResult = checkGameState();
            if (gameResult.equals(GameResult.WINED) || gameResult.equals(GameResult.LOOSED)) {
                // do nothing here
            } else {
                printState();
                gameState.unstopBots();
                //System.out.println("unstopped");
            }

        } else {
            print(player, "you don't have this card");
        }

        for (Bot bot : gameState.getBots()) {
            if (player != bot) {
                bot.getThread().notify();
            }
        }
    }

    private void checkPlayedCard(Player player, int number) {
        boolean bool = false;

        for (Player item : gameState.getPlayers().values()) {
            int size = item.getCards().size();
            int counter = 0;
            while (counter < size) {
                int card = item.getCards().get(counter);
                if (card < number) {
                    size--;
                    bool = true;
                    item.removeCard(card);
                } else {
                    counter++;
                }
            }
        }

        if (bool) {
            gameState.setHeart(gameState.getHeart() - 1);
            printToAll("player " + player.getName() + " played a wrong card" + "\n");
        }

    }

    private void checkPlayedTurn() {
        for (Player player : gameState.getPlayers().values()) {
            if (player.getCards().size() > 0) return;
        }
        gameState.setLevel(gameState.getLevel() + 1);
        shuffleCards();
    }

    private GameResult checkGameState() {
        // null -> game is not finished
        // true -> players wined
        // false -> players lost
        if (gameState.getHeart() < 1) {
            gameState.setGameResult(GameResult.LOOSED);
            printToAll("game is finished and you loosed");
            return GameResult.LOOSED;
        } else if (gameState.getLevel() > 12) {
            gameState.setGameResult(GameResult.WINED);
            printToAll("game is finished and you wined");
            return GameResult.WINED;
        } else return gameState.getGameResult();
    }

    // ninja
    public void requestNinja(Player player) {
        gameState.stopBots();
        //System.out.println("stopped");
        gameState.setRequestedNinja(true);
        printToAll("player " + player.getName() + " requested using ninja cart");
    }

    public void answerNinja(Player player, boolean answer) throws IOException {
        if (answer) {
            printToAll("player " + player.getName() + "answered yes to ninja request");
            gameState.getNinjaAnswers().add(true);
            if (gameState.getNinjaAnswers().size() == gameState.getRealPlayerCount() - 1) {
                printToAll("ninja accepted");
                ninjaProcess();
                gameState.getNinjaAnswers().clear();
                checkPlayedTurn();
                gameState.unstopBots();
                //System.out.println("unstopped");
            }
        } else {
            printToAll("player " + player.getName() + " denied the request for using ninja cart");
            gameState.getNinjaAnswers().clear();
            gameState.setRequestedNinja(false);
        }
    }

    private void ninjaProcess() {
        printToAll("ninja has been used and");

        Integer max = Integer.MIN_VALUE;
        for (Player player : gameState.getPlayers().values()) {
            Integer min = Integer.MAX_VALUE;
            for (Integer a : player.getCards()) {
                min = Math.min(a, min);
            }
            printToAll(player.getName() + " put " + min);
            player.getCards().remove(min);
            max = Math.max(min, max);
        }
        gameState.setLastCard(max);

        gameState.setNinja(gameState.getNinja() - 1);
        gameState.setRequestedNinja(false);

        printState();
    }

    // get Player
    public Player getPlayer(String id) {
        return gameState.getPlayers().get(id);
    }

    public void addPlayer(Player player) {
        gameState.getPlayers().put(player.getId(), player);
        gameState.getRealPlayers().put(player.getId(), player);
    }


    // print
    public void print(Player player, String message) {
        try {
            dos.get(player.getId()).writeUTF(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void printToAll(String message) {
        for (Player player : gameState.getRealPlayers().values()) {
            print(player, message);
        }
    }

    public void printState() {
        for (Player player : gameState.getRealPlayers().values()) {
            String output = player.getName() + " " + player.getCards().toString() + "\n";
            for (Player item : gameState.getPlayers().values()) {
                if (player != item) {
                    output += item.getName() + " " + item.getCards().size() + "\n";
                }
            }
            output += "level: " + gameState.getLevel() + "\n";
            output += "ninja: " + gameState.getNinja() + "\n";
            output += "heart: " + gameState.getHeart() + "\n";
            output += "last played card: " + gameState.getLastCard();
            print(player, output);
        }
    }

}
