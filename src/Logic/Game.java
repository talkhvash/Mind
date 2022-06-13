package Logic;

import java.util.LinkedList;
import java.util.Random;

public class Game {
    private LinkedList<Integer> cards;
    private LinkedList<Player> players;
    private int lastCard;
    private int heart;
    private int ninja;
    private int playersCount;
    private int level;

    public Game(int nPlayer, LinkedList<String> playerName) {
        cards = new LinkedList<>();
        players = new LinkedList<>();
        heart = nPlayer;
        level = 1;
        ninja = 2;
        playersCount = nPlayer;

        int a = playersCount;
        for (String name : playerName) {
            Player player = new Player(name);

        }

    }

    public void shuffleOutCards() {
        Random random = new Random();
        for (int i = 1; i < 100; i++) {
            if (random.nextBoolean()) cards.addLast(i);
            else cards.addFirst(i);
        }
        for (int i = 0; i < playersCount; i++) {
            Player player = players.get(i);
            for (int j = 0; j < level; j++) {
                player.addCard(cards.get(i));

            }

        }


    }

    public void putCard(Player player, int number) {
        LinkedList<Integer> playerCards = player.getCards();
        if (playerCards.contains(number)) {
            lastCard = number;

        }

    }

}
