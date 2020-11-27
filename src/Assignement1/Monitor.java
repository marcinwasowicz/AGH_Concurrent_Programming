package Assignement1;

import java.util.HashMap;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public abstract class Monitor {
    protected Buffer buffer;
    protected ReentrantLock lock;

    protected Condition firstProducer;
    protected Condition firstConsumer;
    protected Condition restProducers;
    protected Condition restConsumers;

    public Monitor(int size){
        this.buffer = new Buffer(size);
        this.lock = new ReentrantLock();

        this.firstConsumer = lock.newCondition();
        this.firstProducer = lock.newCondition();
        this.restConsumers = lock.newCondition();
        this.restProducers = lock.newCondition();
    }

    public abstract void produce(int producerID, int[] items) throws InterruptedException;

    public abstract void consume(int consumerID, int numOfItems) throws  InterruptedException;

    protected void printProducerMessage(int producerID, HashMap<Integer, Integer> addedItems){
        System.out.println("Producer of ID: " + producerID + " produced: " + addedItems);
    }

    protected void printConsumerMessage(int consumerID, HashMap<Integer, Integer> readItems){
        System.out.println("Consumer of ID: " + consumerID + " consumed: " + readItems);
    }
}
