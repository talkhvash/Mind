import java.util.LinkedList;

public class Player {
    Game game;
    LinkedList<Integer> cards;

    public Player(Game game) {
        cards = new LinkedList<>();
        this.game = game;

    }

    public boolean putCard(Integer cardNumber) {
        if (cards.contains(cardNumber)) {
            cards.remove(cardNumber);

            return true;
        } else return false;

    }
}
