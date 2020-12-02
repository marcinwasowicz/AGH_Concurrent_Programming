package ActiveObject;

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
            for(int taskCount = 0; taskCount<Parameters.additionalWork;){
                while(!this.tryGetResult(future)){
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

    private Future createRequest(){
        return this.activationQueue.addConsumerRequest(this.ID,this.random.nextInt(this.maxSize));
    }

    private boolean tryGetResult(Future future){
       return future.queryRequest();
    }
}

