package Logic;

import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

public class GameState {
    // hashmap<authTaken, Player>
    private final HashMap<String, Player> players;
    private int playersCount;
    private int level;
    private int heart;
    private GameResult gameResult;
    private int ninja;
    private boolean requestedNinja;
    private LinkedList<Boolean> ninjaAnswers;
    private int lastCard;
    private int realPlayerCount;

    public GameState() {
        players = new HashMap<>();
        level = 1;
        ninja = 2;
        gameResult = GameResult.IN;
    }

    // getters and setters


    public void setPlayersCount(int playersCount) {
        this.playersCount = playersCount;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setHeart(int heart) {
        this.heart = heart;
    }

    public void setNinja(int ninja) {
        this.ninja = ninja;
    }

    public void setRequestedNinja(boolean requestedNinja) {
        this.requestedNinja = requestedNinja;
    }

    public void setNinjaAnswers(LinkedList<Boolean> ninjaAnswers) {
        this.ninjaAnswers = ninjaAnswers;
    }

    public void setLastCard(int lastCard) {
        this.lastCard = lastCard;
    }

    public boolean isRequestedNinja() {
        return requestedNinja;
    }

    public LinkedList<Boolean> getNinjaAnswers() {
        return ninjaAnswers;
    }

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

    public GameResult getGameResult() {
        return gameResult;
    }

    public void setGameResult(GameResult gameResult) {
        this.gameResult = gameResult;
    }

    public int getRealPlayerCount() {
        return realPlayerCount;
    }

    public void setRealPlayerCount(int realPlayerCount) {
        this.realPlayerCount = realPlayerCount;
    }
}
