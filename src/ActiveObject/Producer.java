package ActiveObject;

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
            Future producerFuture = this.createRequest(items);
            for(int taskCount = 0; taskCount<Parameters.additionalWork;){
                while(!this.tryGetResult(producerFuture)){
                    for(int i = 0; taskCount<Parameters.additionalWork && i<Parameters.batchSize; i++){
                        Math.sin(Parameters.task);
                        taskCount++;
                    }
                }
                for(;taskCount<Parameters.additionalWork; taskCount++){
                    Math.sin(Parameters.task);
                }
            }
        }
    }

    private Future createRequest(int[] items){
        return this.activationQueue.addProducerRequest(this.ID, items);
    }

    private boolean tryGetResult(Future producerFuture){
       return producerFuture.queryRequest();
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
