package ActiveObject;

import java.util.HashMap;
import java.util.LinkedList;

public class Future {
    private HashMap<Integer, Boolean> producerRequests;
    private HashMap<Integer, LinkedList<Integer>> consumerRequests;

    public Future(){
        this.producerRequests = new HashMap<>();
        this.consumerRequests = new HashMap<>();
    }

    public void initProducerRequest(int producerID){
        this.producerRequests.put(producerID, false);
    }

    public void initConsumerRequest(int consumerID){
        this.consumerRequests.put(consumerID, null);
    }

    public void confirmProducerRequest(int producerID){
        this.producerRequests.put(producerID, true);
    }

    public void confirmConsumerRequest(int consumerID, LinkedList<Integer> items){
        this.consumerRequests.put(consumerID, items);
    }

    public boolean queryProducerRequest(int producerID){
        return this.producerRequests.get(producerID);
    }

    public LinkedList<Integer> queryConsumerRequest(int consumerID){
       return this.consumerRequests.get(consumerID);
    }

    public void closeProducerRequest(int producerID){
        this.producerRequests.remove(producerID);
    }

    public void closeConsumerRequest(int consumerID){
        this.consumerRequests.remove(consumerID);
    }

}
