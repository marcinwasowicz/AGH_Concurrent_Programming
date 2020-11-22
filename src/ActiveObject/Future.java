package ActiveObject;

import java.util.Arrays;
import java.util.LinkedList;

public class Future {
    private volatile boolean[] producerRequests;
    private volatile LinkedList<Integer>[] consumerRequests;

    public Future(int numberOfProducers, int numberOfConsumers) {
        this.producerRequests = new boolean[numberOfProducers];
        Arrays.fill(this.producerRequests, false);
        this.consumerRequests = new LinkedList[numberOfConsumers];
        Arrays.fill(this.consumerRequests, null);
    }

    public void confirmProducerRequest(int producerID){
        this.producerRequests[producerID] = true;
    }

    public void confirmConsumerRequest(int consumerID, LinkedList<Integer> items){
        this.consumerRequests[consumerID] = items;
    }

    public boolean queryProducerRequest(int producerID){
        return this.producerRequests[producerID];
    }

    public LinkedList<Integer> queryConsumerRequest(int consumerID){
        return this.consumerRequests[consumerID];
    }

    public void closeProducerRequest(int producerID){
        this.producerRequests[producerID] = false;
    }

    public void closeConsumerRequest(int consumerID){
        this.consumerRequests[consumerID] = null;
    }
}
