package ActiveObject;

import java.util.LinkedList;
import java.util.Random;

public class Consumer implements Runnable{
    private int ID;
    private int maxSize;
    private Random random;
    private ActivationQueue activationQueue;

    public Consumer(int ID, int maxSize, ActivationQueue activationQueue){
        this.ID = ID;
        this.maxSize = maxSize;
        this.activationQueue = activationQueue;
        this.random = new Random();
    }


    @Override
    public void run() {
        while(true){
            Future future = this.createRequest();
            while(!this.tryGetResult(future)){}
            future.closeConsumerRequest(this.ID);
        }
    }

    private Future createRequest(){
        return this.activationQueue.addConsumerRequest(this.ID, this.random.nextInt(this.maxSize));
    }

    private void printConfirmationMessage(LinkedList<Integer> items){
        System.out.println("Consumer of ID: " + this.ID + " consumed items: " + items.toString());
    }

    private boolean tryGetResult(Future future){
        LinkedList<Integer> items = future.queryConsumerRequest(this.ID);
        if(items == null){
            return false;
        }
        this.printConfirmationMessage(items);
        return true;
    }
}

