package Logic;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Bot extends Player implements Runnable {

    private boolean involved = true;
    private boolean stopped = false;
    private GameHandler gameHandler;
    private int counter;
    Thread thread;


    public Bot(String name, String id, GameHandler gameHandler, int counter) {
        super(name, id);
        this.gameHandler = gameHandler;
        thread = new Thread(this::run);
        thread.start();
        this.counter = counter;
    }


    @Override
    public void run() {
        while (true) {
            if (!stopped) {
                int min = 100;
                for (int a : cards) {
                    if (min > a) {
                        min = a;
                    }
                }
                int length = 10 + counter * (min / 10);

                if (!stopped && cards.size() != 0) {
                    try {
                        TimeUnit.SECONDS.sleep(length);
                        gameHandler.putCard(this, (Integer) min);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public boolean isInvolved() {
        return involved;
    }

    public void setInvolved(boolean involved) {
        this.involved = involved;
    }

    public boolean isStopped() {
        return stopped;
    }

    public void setStopped(boolean stopped) {
        this.stopped = stopped;
    }

    public Thread getThread() {
        return thread;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }
}
