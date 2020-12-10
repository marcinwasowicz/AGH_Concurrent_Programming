package Monitor;

import java.util.HashMap;

public class MonitorVersionW extends Monitor {

    public MonitorVersionW(int size){
        super(size);
    }

    @Override
    public void produce(int producerID, int[] items) throws InterruptedException {
        lock.lock();
        long lockAccesTime = this.getLockAccessTime();
        if(this.lock.hasWaiters(this.firstProducer)){
            restProducers.await();
        }
        while(!this.buffer.canWrite(items.length)){
            firstProducer.await();
        }
        long taskEmbarkTime = this.getTaskEmbarkTime();
        HashMap<Integer, Integer> addedItems = this.buffer.addItem(items);
        this.printProducerMessage(producerID, addedItems, lockAccesTime, taskEmbarkTime);
        this.restProducers.signal();
        this.firstConsumer.signal();
        lock.unlock();
    }

    @Override
    public void consume(int consumerID, int numOfItems) throws InterruptedException {
        lock.lock();
        long lockAccessTime = this.getLockAccessTime();
        if(this.lock.hasWaiters(this.firstConsumer)){
            restConsumers.await();
        }
        while(!this.buffer.canRead(numOfItems)){
            firstConsumer.await();
        }
        long taskEmbarkTime = this.getTaskEmbarkTime();
        HashMap<Integer, Integer> readItems = this.buffer.readItems(numOfItems);
        this.printConsumerMessage(consumerID, readItems, lockAccessTime, taskEmbarkTime);
        this.restConsumers.signal();
        this.firstProducer.signal();
        lock.unlock();
    }
}
