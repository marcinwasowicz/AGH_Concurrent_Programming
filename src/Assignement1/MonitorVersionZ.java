package Assignement1;

import java.util.HashMap;

public class MonitorVersionZ extends Monitor{

    private boolean isFirstProducer;
    private boolean isFirstConsumer;

    public MonitorVersionZ(int size){
        super(size);

        this.isFirstConsumer = true;
        this.isFirstProducer = true;
    }

    @Override
    public void produce(int producerID, int[] items) throws InterruptedException {
        lock.lock();
        long lockAccessTime = this.getLockAccessTime();

        if(!this.isFirstProducer){
            restProducers.await();
        }
        while(!this.buffer.canWrite(items.length)){
            this.isFirstProducer = false;
            firstProducer.await();
        }

        this.isFirstProducer = true;
        long taskEmbarkTime = this.getTaskEmbarkTime();
        HashMap<Integer, Integer> addedItems = this.buffer.addItem(items);
        this.printProducerMessage(producerID, addedItems, lockAccessTime, taskEmbarkTime);
        this.restProducers.signal();
        this.firstConsumer.signal();
        lock.unlock();
    }

    @Override
    public void consume(int consumerID, int numOfItems) throws InterruptedException {
        lock.lock();
        long lockAccessTime = this.getLockAccessTime();
        if(!this.isFirstConsumer){
            restConsumers.await();
        }
        while(!this.buffer.canRead(numOfItems)){
            this.isFirstConsumer = false;
            firstConsumer.await();
        }

        this.isFirstConsumer = true;
        long taskEmbarkTime = this.getTaskEmbarkTime();
        HashMap<Integer, Integer> readItems = this.buffer.readItems(numOfItems);
        this.printConsumerMessage(consumerID, readItems, lockAccessTime, taskEmbarkTime);
        this.restConsumers.signal();
        this.firstProducer.signal();
        lock.unlock();
    }
}
