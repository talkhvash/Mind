import java.util.LinkedList;
import java.util.Random;

public class Game {
    private LinkedList<Integer> cards = new LinkedList<>();
    private Integer lastCard;

    public Game(int nPlayer) {




    }

    public void shuffleOutCards() {
        Random random = new Random();
        for (int i = 1; i < 100; i++) {
            if (random.nextBoolean()) cards.addLast(i);
            else cards.addFirst(i);
        }
    }
}
