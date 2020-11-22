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
            while(!this.tryGetResult(future)){}
        }
    }

    private Future createRequest(){
        return this.activationQueue.addConsumerRequest(this.ID,this.random.nextInt(this.maxSize));
    }

    private boolean tryGetResult(Future future){
       return future.queryRequest();
    }
}

