package Monitor;

import java.util.Random;

public class Producer implements Runnable {

    private int producerID;
    private int maxNumItems;
    private Monitor monitor;
    private Random random;

    public Producer(int producerID, int maxNumItems, Monitor monitor){
        this.producerID = producerID;
        this.maxNumItems = maxNumItems;
        this.monitor = monitor;
        this.random = new Random();
    }

    private void produce() throws InterruptedException {
        int[] items = this.getRandomArray();
        this.monitor.produce(this.producerID, items);
    }

    private int[] getRandomArray(){
        int numItems = this.random.nextInt(this.maxNumItems);
        int[] items = new int[numItems];
        for(int i = 0; i<numItems; i++){
            items[i] = this.random.nextInt();
        }
        return items;
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
