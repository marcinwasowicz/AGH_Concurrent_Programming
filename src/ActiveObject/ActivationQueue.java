package ActiveObject;

import java.util.LinkedList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ActivationQueue {
    private Future future;
    private Servant servant;
    private LinkedList<ProducerRequest> producerRequests;
    private LinkedList<ConsumerRequest> consumerRequests;
    private Lock lock;

    public ActivationQueue(Future future, Servant servant) {
        this.future = future;
        this.servant = servant;
        this.consumerRequests = new LinkedList<>();
        this.producerRequests = new LinkedList<>();
        this.lock = new ReentrantLock();
    }

    public Future addProducerRequest(int producerID, int[] items) {
        this.lock.lock();
        this.producerRequests.addLast(new ProducerRequest(producerID, items));
        this.lock.unlock();
        return this.future;
    }

    public Future addConsumerRequest(int consumerID, int numberOfItems) {
        this.lock.lock();
        this.consumerRequests.addLast(new ConsumerRequest(consumerID, numberOfItems));
        this.lock.unlock();
        return this.future;
    }

    public Object getRequest() {
        this.lock.lock();
        boolean canProduce = this.canProduce();
        boolean canConsume = this.canConsume();
        Object result = null;
        if (canConsume && canProduce) {
            result = this.getEarlierRequest();
        } else if (canConsume) {
            result = this.getFirstConsumerRequest();
        } else if (canProduce) {
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
