package Assignement1;

import java.util.Random;

public class Producer implements Runnable {

    private int producerID;
    private int maxNumItems;
    private int value;
    private Monitor monitor;
    private Random random;

    public Producer(int producerID, int maxNumItems, int value, Monitor monitor){
        this.producerID = producerID;
        this.maxNumItems = maxNumItems;
        this.value = value;
        this.monitor = monitor;
        this.random = new Random();
    }

    private void produce() throws InterruptedException {
        int numItems = this.random.nextInt(this.maxNumItems) + 1;
        this.monitor.produce(this.producerID, numItems, this.value);
    }

    @Override
    public void run() {
        while(true){
            try {
                this.produce();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
