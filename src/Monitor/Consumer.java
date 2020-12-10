package Monitor;

import java.util.Random;

public class Consumer implements Runnable{
    private int consumerID;
    private int maxNumItems;
    private Monitor monitor;
    private Random random;

    public Consumer(int consumerID, int maxNumItems, Monitor monitor){
        this.consumerID = consumerID;
        this.maxNumItems = maxNumItems;
        this.monitor = monitor;
        this.random = new Random();
    }

    private void consume() throws InterruptedException {
        int numItems = random.nextInt(this.maxNumItems);
        this.monitor.consume(consumerID, numItems);
    }

    @Override
    public void run() {
        while (true){
            try {
                this.consume();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
