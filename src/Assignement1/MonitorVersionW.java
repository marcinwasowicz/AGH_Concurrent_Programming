package Assignement1;

public class MonitorVersionW extends Monitor{

    public MonitorVersionW(int size){
        super(size);
    }

    @Override
    public void produce(int producerID, int numOfItems, int val) throws InterruptedException {
        lock.lock();
        if(this.lock.hasWaiters(this.firstProducer)){
            restProducers.await();
        }
        while(!this.buffer.canWrite(numOfItems)){
            firstProducer.await();
        }
        System.out.println("Producer of ID: " + producerID + " is producing:");
        this.buffer.addItem(numOfItems, val);
        this.restProducers.signal();
        this.firstConsumer.signal();
        lock.unlock();
    }

    @Override
    public void consume(int consumerID, int numOfItems) throws InterruptedException {
        lock.lock();
        if(this.lock.hasWaiters(this.firstConsumer)){
            restConsumers.await();
        }
        while(!this.buffer.canRead(numOfItems)){
            firstConsumer.await();
        }
        System.out.println("Consumer of ID: " + consumerID + " is consuming:");
        this.buffer.readItems(numOfItems);
        this.restConsumers.signal();
        this.firstProducer.signal();
        lock.unlock();
    }
}
