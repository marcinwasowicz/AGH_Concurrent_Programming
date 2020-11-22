package ActiveObject;

import java.util.Arrays;
import java.util.Random;

public class Producer implements Runnable{

    private int ID;
    private int maxSize;
    private Random random;
    private ActivationQueue activationQueue;

    public Producer(int ID, int maxSize, ActivationQueue activationQueue){
        this.ID = ID;
        this.maxSize = maxSize;
        this.activationQueue = activationQueue;
        this.random = new Random();
    }


    @Override
    public void run() {
        while(true){
            int[] items = this.createItems();
            ProducerFuture producerFuture = this.createRequest(items);
            while(!this.tryGetResult(producerFuture, items)){}
        }
    }

    private ProducerFuture createRequest(int[] items){
        return this.activationQueue.addProducerRequest(items);
    }

    private void printConfirmationMessage(int[] items){
        System.out.println("Producer of ID: " + this.ID + " produced items: " + Arrays.toString(items));
    }

    private boolean tryGetResult(ProducerFuture producerFuture, int[] items){
        if(!producerFuture.queryProducerRequest()){
            return false;
        }
        this.printConfirmationMessage(items);
        return true;
    }

    private int[] createItems(){
        int numberOfItems = this.random.nextInt(this.maxSize);
        int[] items = new int[numberOfItems];
        for(int i = 0; i<numberOfItems; i++){
            items[i] = this.random.nextInt();
        }
        return items;
    }
}
