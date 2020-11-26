package ActiveObject;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ActivationQueue {
    private Servant servant;
    private LinkedList<ProducerRequest> producerRequests;
    private LinkedList<ConsumerRequest> consumerRequests;
    private Lock lock;
    private Condition condition;

    public ActivationQueue(Servant servant) {
        this.servant = servant;
        this.consumerRequests = new LinkedList<>();
        this.producerRequests = new LinkedList<>();
        this.lock = new ReentrantLock();
        this.condition = this.lock.newCondition();
    }

    public Future addProducerRequest(int producerID, int[] items) {
        this.lock.lock();
        Future producerFuture = new Future();
        this.producerRequests.addLast(new ProducerRequest(producerID, items, producerFuture));
        this.condition.signal();
        this.lock.unlock();
        return producerFuture;
    }

    public Future addConsumerRequest(int consumerID, int numberOfItems) {
        this.lock.lock();
        Future consumerFuture = new Future();
        this.consumerRequests.addLast(new ConsumerRequest(consumerID, numberOfItems, consumerFuture));
        this.condition.signal();
        this.lock.unlock();
        return consumerFuture;
    }

    public Object getRequest() throws InterruptedException {
        this.lock.lock();

        while(!this.canProduce() && !this.canConsume()){
            this.condition.await();
        }
        
        boolean canProduce = this.canProduce();
        boolean canConsume = this.canConsume();

        Object result = null;
        if (canConsume && canProduce) {
            result = this.getEarlierRequest();
        } 
        else if (canConsume) {
            result = this.getFirstConsumerRequest();
        } 
        else {
            result = this.getFirstProducerRequest();
        }

        this.lock.unlock();

        return result;
    }

    private boolean canProduce() {
        if (this.producerRequests.size() != 0) {
            return this.servant.canProduce(this.producerRequests.getFirst().getNumberOfItems());
        }
        return false;
    }

    private boolean canConsume() {
        if (this.consumerRequests.size() != 0) {
            return this.servant.canConsume(this.consumerRequests.getFirst().getNumberOtItems());
        }
        return false;
    }

    private Object getEarlierRequest() {
        long productionRequestTimeStamp = this.producerRequests.getFirst().getSchedulingTimeStamp();
        long consumptionRequestTimeStamp = this.consumerRequests.getFirst().getSchedulingTimeStamp();

        if (productionRequestTimeStamp < consumptionRequestTimeStamp) {
            return this.getFirstProducerRequest();
        }

        return this.getFirstConsumerRequest();
    }

    private ProducerRequest getFirstProducerRequest() {
        return this.producerRequests.removeFirst();
    }

    private ConsumerRequest getFirstConsumerRequest() {
        return this.consumerRequests.removeFirst();
    }
}
