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
        long embarkTime = this.getEmbarkTime();
        HashMap<Integer, Integer> items = this.servant.addItems(producerRequest.getItems());
        this.printProducerMessage(producerRequest, items, embarkTime);
        producerRequest.getProducerFuture().confirmRequest();
    }

    private void executeConsumption(ConsumerRequest consumerRequest){
        long embarkTime = this.getEmbarkTime();
        HashMap<Integer, Integer> items = this.servant.consumeItems(consumerRequest.getNumberOtItems());
        this.printConsumerMessage(consumerRequest, items, embarkTime);
        consumerRequest.getConsumerFuture().confirmRequest();
    }

    private void printConsumerMessage(ConsumerRequest consumerRequest, HashMap<Integer, Integer> items, long embarkTime){
        System.out.println("Consumer of ID: " + consumerRequest.getConsumerID() + " consumed: " + items.toString() + 
        " time elapsed: " + Math.abs(embarkTime - consumerRequest.getSchedulingTimeStamp()));
    }

    private void printProducerMessage(ProducerRequest producerRequest, HashMap<Integer, Integer> items, long embarkTime){
        System.out.println("Producer od ID: " + producerRequest.getProducerID() + " produced: " + items.toString() + 
        " time elapsed: " + Math.abs(embarkTime - producerRequest.getSchedulingTimeStamp()));
    }

    private long getEmbarkTime(){
        return System.nanoTime();
    }
}
