package Logic;

import java.util.LinkedList;

public class Player {
    private final String id;
    private final String name;
    protected final LinkedList<Integer> cards;

    public Player(String name, String id) {
        this.name = name;
        this.id = id;
        cards = new LinkedList<>();
    }

    // setters and getters
    public String getName() {
        return name;
    }

    public LinkedList<Integer> getCards() {
        return cards;
    }

    public String getId(){
        return id;
    }

    public void removeCard(Integer number) {
        cards.remove(number);
    }

    public void addCard(Integer number) {
        cards.add(number);
    }

}
