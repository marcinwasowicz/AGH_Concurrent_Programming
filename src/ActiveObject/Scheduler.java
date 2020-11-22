package ActiveObject;

import java.util.HashMap;

public class Scheduler implements Runnable{
    private ActivationQueue activationQueue;
    private Servant servant;

    public Scheduler(Servant servant, ActivationQueue activationQueue){
        this.activationQueue = activationQueue;
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
        HashMap<Integer, Integer> items = this.servant.addItems(producerRequest.getItems());
        this.printProducerMessage(producerRequest, items);
        producerRequest.getProducerFuture().confirmRequest();
    }

    private void executeConsumption(ConsumerRequest consumerRequest){
        HashMap<Integer, Integer> items = this.servant.consumeItems(consumerRequest.getNumberOtItems());
        this.printConsumerMessage(consumerRequest, items);
        consumerRequest.getConsumerFuture().confirmRequest();
    }

    private void printConsumerMessage(ConsumerRequest consumerRequest, HashMap<Integer, Integer> items){
        System.out.println("Consumer of ID: " + consumerRequest.getConsumerID() + " consumed: " + items.toString());
    }

    private void printProducerMessage(ProducerRequest producerRequest, HashMap<Integer, Integer> items){
        System.out.println("Producer od ID: " + producerRequest.getProducerID() + " produced: " + items.toString());
    }
}
