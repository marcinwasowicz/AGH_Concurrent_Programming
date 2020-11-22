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
            Future future = this.createRequest(items);
            while(!this.tryGetResult(future, items)){}
            future.closeProducerRequest(this.ID);
        }
    }

    private Future createRequest(int[] items){
        return this.activationQueue.addProducerRequest(this.ID, items);
    }

    private void printConfirmationMessage(int[] items){
        System.out.println("Producer of ID: " + this.ID + " produced items: " + Arrays.toString(items));
    }

    private boolean tryGetResult(Future future, int[] items){
        if(!future.queryProducerRequest(this.ID)){
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
