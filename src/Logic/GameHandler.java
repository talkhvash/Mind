package Logic;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

public class GameHandler {
    GameState gameState;
    HashMap<String, DataOutputStream> dos;

    public GameHandler(GameState gameState, HashMap<String, DataOutputStream> dos) {
        this.dos = dos;
        this.gameState = gameState;
    }

    public void initializePlayers(int playersCount) {
        gameState.setHeart(playersCount);
        gameState.setPlayersCount(playersCount);
        gameState.setRealPlayerCount(gameState.getPlayers().size());
        // add bot
        int botCounts = playersCount - gameState.getPlayers().size();
        for (int i = 0; i < playersCount - botCounts; i++) {
            gameState.getPlayers().put("bot " + i, new Player("bot " + i, " bot " + i));
        }

        gameState.setGameResult(GameResult.STARTED);
    }

    private void shuffleCards() {
        Random random = new Random();
        LinkedList<Integer> cards = new LinkedList<>();
        for (int i = 1; i < 100; i++) {
            if (random.nextBoolean()) cards.addLast(i);
            else cards.addFirst(i);
        }
        for (int i = 0; i < gameState.getPlayersCount(); i++) {
            Player player = gameState.getPlayers().get(i);
            for (int j = 0; j < gameState.getLevel(); j++) {
                player.addCard(cards.getFirst());
                cards.removeFirst();
            }
        }
        gameState.setLastCard(0);
    }

    public Boolean putCard(Player player, int number) {
        boolean output = true;
        if (player.getCards().contains(number)) {
            player.removeCard(number);
            gameState.setLastCard(number);
            if (checkPlayedCard(number)) output = false;
            checkPlayedTurn();
            checkGameState();
            return output;
        } else return null;
    }

    private boolean checkPlayedCard(int number) {
        boolean bool = false;

        for (Player player : gameState.getPlayers().values()) {
            for (Integer card : player.getCards()) {
                if (card < number) {
                    bool = true;
                    player.removeCard(card);
                }
            }
        }

        if (bool) gameState.setHeart(gameState.getHeart() - 1);
        return !bool;
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
        if (gameState.getHeart() < 1){
            gameState.setGameResult(GameResult.LOOSED);
            return GameResult.LOOSED;
        }
        else if (gameState.getLevel() > 12){
            gameState.setGameResult(GameResult.WINED);
            return GameResult.WINED;
        }
        else return gameState.getGameResult();
    }

    public void print(Player player) throws IOException {
        String output = player.getName() + "\n";
        output += player.getCards();
        for (Player item : gameState.getPlayers().values()) {
            if (player != item) {
                output += player.getName() + " " + player.getCards().size() + "\n";
            }
        }
        output += "ninja: " + gameState.getNinja() + "\n";
        output += "heart: " + gameState.getHeart() + "\n";
        output += "last played card: " + gameState.getLastCard();
        dos.get(player.getId()).writeUTF(output);
    }

    public void printDoNotHave(Player player) throws IOException {
        String output = "you don't have this card";
        dos.get(player.getId()).writeUTF(output);
    }

    public void printWrongPlayed(Player player, Player wrongPlayer) throws IOException {
        String output = player.getName() + "\n";
        output += "player " + wrongPlayer.getName() + " played a wrong card";
        output += player.getCards();
        for (Player item : gameState.getPlayers().values()) {
            if (player != item) {
                output += player.getName() + " " + player.getCards().size() + "\n";
            }
        }
        output += "ninja: " + gameState.getNinja() + "\n";
        output += "heart: " + gameState.getHeart() + "\n";
        output += "last played card: " + gameState.getLastCard();
        dos.get(player.getId()).writeUTF(output);
    }

    public void printRequest (Player player, Player requestedPlayer) throws IOException {
        String output = "player " + requestedPlayer.getName() + "requested using ninja cart";
        dos.get(player.getId()).writeUTF(output);
    }

    public void printAnswerToRequest (Player player, Player answeredPlayer, boolean bool) throws IOException {
        String output;
        if (bool){
            output = "player " + answeredPlayer.getName() + "answered yes to ninja request";
        } else {
            output = "player " + answeredPlayer.getName() + "answered no to ninja request";
        }
        dos.get(player.getId()).writeUTF(output);
    }

    public void requestNinja(Player player) {
        gameState.setRequestedNinja(true);
    }

    public void answerNinja(Player player, boolean answer) {
        if (answer){
            gameState.getNinjaAnswers().add(true);
            if (gameState.getNinjaAnswers().size()==gameState.getRealPlayerCount()){

            }
        } else {
            //todo
        }
    }

    private void ninjaProcess (){
        int max = 0;
        String output = "ninja has been used and";
        for (Player player : gameState.getPlayers().values()){
            int min = 101;
            for (int a : player.getCards()){
                if (a<min){
                    min = a;
                }
            }
            output += player.getName() + " put " + min;
            player.getCards().remove(min);
            if (min>max){
                max = min;
            }
        }
        gameState.setLastCard(max);

    }

    // get Player
    public Player getPlayer(String id) {
        return gameState.getPlayers().get(id);
    }

    public void addPlayer(Player player) {
        this.gameState.getPlayers().put(player.getId(), player);
    }


}
