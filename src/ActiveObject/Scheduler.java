package ActiveObject;

import java.util.LinkedList;

public class Scheduler implements Runnable{
    private ActivationQueue activationQueue;
    private Servant servant;
    private Future future;

    public Scheduler(Servant servant, Future future, ActivationQueue activationQueue){
        this.activationQueue = activationQueue;
        this.future = future;
        this.servant = servant;
    }

    @Override
    public void run() {
        while(true) {
            Object task = this.getTask();
            if(task == null){
                continue;
            }
            else if (task instanceof ProducerRequest) {
                this.executeProduction((ProducerRequest) task);
            }
            else {
                this.executeConsumption((ConsumerRequest) task);
            }
        }
    }

    private Object getTask(){
        return this.activationQueue.getRequest();
    }

    private void executeProduction(ProducerRequest producerRequest){
        this.servant.addItems(producerRequest.getItems());
        this.future.confirmProducerRequest(producerRequest.getProducerID());
    }

    private void executeConsumption(ConsumerRequest consumerRequest){
        LinkedList<Integer> items = this.servant.consumeItems(consumerRequest.getNumberOtItems());
        this.future.confirmConsumerRequest(consumerRequest.getConsumerID(),items);
    }
}
