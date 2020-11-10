package Assignement1;

import java.util.concurrent.locks.ReentrantLock;

public class MonitorVersionZ extends Monitor{

    private boolean isFirstProducer;
    private boolean isFirstConsumer;

    public MonitorVersionZ(int size){
        super(size);

        this.isFirstConsumer = true;
        this.isFirstProducer = true;
    }

    @Override
    public void produce(int producerID, int numOfItems, int val) throws InterruptedException {
        lock.lock();
        if(!this.isFirstProducer){
            restProducers.await();
        }
        while(!this.buffer.canWrite(numOfItems)){
            this.isFirstProducer = false;
            firstProducer.await();
        }

        this.isFirstProducer = true;

        System.out.println("Producer of ID: " + producerID + " is producing:");
        this.buffer.addItem(numOfItems, val);
        this.restProducers.signal();
        this.firstConsumer.signal();
        lock.unlock();
    }

    @Override
    public void consume(int consumerID, int numOfItems) throws InterruptedException {
        lock.lock();
        if(!this.isFirstConsumer){
            restConsumers.await();
        }
        while(!this.buffer.canRead(numOfItems)){
            this.isFirstConsumer = false;
            firstConsumer.await();
        }

        this.isFirstConsumer = true;

        System.out.println("Consumer of ID: " + consumerID + " is consuming:");
        this.buffer.readItems(numOfItems);
        this.restConsumers.signal();
        this.firstProducer.signal();
        lock.unlock();
    }
}
