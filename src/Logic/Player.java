package Logic;

import java.util.LinkedList;

public class Player {

    private final String name;
    private final LinkedList<Integer> cards;

    public Player(String name) {
        this.name = name;
        cards = new LinkedList<>();
    }

    public String getName() {
        return name;
    }

    public LinkedList<Integer> getCards() {
        return cards;
    }

    public void removeCard(Integer number) {
        cards.remove(number);
    }

    public void addCard(Integer number) {
        cards.add(number);
    }
}
