package Logic;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

public class Game {
    private final HashMap<String, Player> players;
    private boolean isStarted;

    private int level;
    private int ninja;
    private int playersCount;
    private int heart;
    private int lastCard;

    public Game() {
        players = new HashMap<>();
        level = 1;
        ninja = 2;
    }

    public void initializePlayers(int nPlayer) {
        heart = nPlayer;
        playersCount = nPlayer;

        for (int i = 0; i < playersCount - players.size(); i++) {
            players.put("bot " + i, new Player("bot " + i, " bot " + i));
        }

        isStarted = true;
    }

    private void shuffleCards() {
        Random random = new Random();
        LinkedList<Integer> cards = new LinkedList<>();
        for (int i = 1; i < 100; i++) {
            if (random.nextBoolean()) cards.addLast(i);
            else cards.addFirst(i);
        }
        for (int i = 0; i < playersCount; i++) {
            Player player = players.get(i);
            for (int j = 0; j < level; j++) {
                player.addCard(cards.getFirst());
                cards.removeFirst();
            }
        }
    }

    private boolean putCard(Player player, int number) {
        if (player.getCards().contains(number)) {
            player.removeCard(number);
            lastCard = number;
            checkPlayedCard(number);
            checkPlayedTurn();
            return true;
        } else return false;
    }

    private void checkPlayedCard(int number) {
        boolean bool = false;
        for (Player player : players.values()) {
            for (Integer card : player.getCards()) {
                if (card < number) {
                    bool = true;
                    player.removeCard(card);
                }
            }
        }
        if (bool) heart--;
    }

    private void checkPlayedTurn() {
        for (Player player : players.values()) {
            if (player.getCards().size() > 0) return;
        }
        level++;
    }

    private Boolean checkGameState() {
        // null -> game is not finished
        // true -> players wined
        // false -> players lost
        if (heart < 1) return false;
        else if (level > 12) return true;
        else return null;
    }

    public String print(Player player) {
        String output = player.getName() + "\n";
        output += player.getCards();
        for (Player item : players.values()) {
            if (player != item) {
                output += player.getName() + " " + player.getCards().size() + "\n";
            }
        }
        output += "ninja: " + ninja + "\n";
        output += "heart: " + heart + "\n";
        output += "last played card: " + lastCard;
        return output;
    }

    // get Player
    public Player getPlayer(String id) {
        return players.get(id);
    }

    public void addPlayer(Player player) {
        this.players.put(player.getId(), player);
    }

    // getters and setters
    public HashMap<String, Player> getPlayers() {
        return players;
    }

    public int getLastCard() {
        return lastCard;
    }

    public int getHeart() {
        return heart;
    }

    public int getNinja() {
        return ninja;
    }

    public int getPlayersCount() {
        return playersCount;
    }

    public int getLevel() {
        return level;
    }

    public boolean isStarted() {
        return isStarted;
    }
}
