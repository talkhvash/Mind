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

    public Game(int nPlayer) {
        level = 1;
        playersCount = nPlayer;

    }

    public void shuffleOutCards() {
        Random random = new Random();
        for (int i = 1; i < 100; i++) {
            if (random.nextBoolean()) cards.addLast(i);
            else cards.addFirst(i);
        }
        for (int i = 0; i < level * playersCount; i++) {

        }


    }

    public void putCard(Player player) {


    }

}
