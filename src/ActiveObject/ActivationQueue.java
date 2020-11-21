package ActiveObject;

import java.util.LinkedList;

public class ActivationQueue {
    private Future future;
    private Servant servant;
    private LinkedList<ProducerRequest> producerRequests;
    private LinkedList<ConsumerRequest> consumerRequests;

    public ActivationQueue(Future future, Servant servant){
        this.future = future;
        this.servant = servant;
        this.consumerRequests = new LinkedList<>();
        this.producerRequests = new LinkedList<>();
    }

    public synchronized Future addProducerRequest(int producerID, int[] items){
        this.producerRequests.addLast(new ProducerRequest(producerID, items));
        this.future.initProducerRequest(producerID);
        return this.future;
    }

    public synchronized Future addConsumerRequest(int consumerID, int numberOfItems){
        this.consumerRequests.addLast(new ConsumerRequest(consumerID, numberOfItems));
        this.future.initConsumerRequest(consumerID);
        return this.future;
    }

    public synchronized Object getRequest(){
        boolean canProduce = false;
        boolean canConsume = false;
        if(this.consumerRequests.size() != 0) {
            canConsume = this.servant.canConsume(this.consumerRequests.getFirst().getNumberOtItems());
        }
        if(this.producerRequests.size() != 0) {
            canProduce = this.servant.canProduce(this.producerRequests.getFirst().getNumberOfItems());
        }
        if(canConsume && canProduce){
            long productionRequestTimeStamp = this.producerRequests.getFirst().getSchedulingTimeStamp();
            long consumptionRequestTimeStamp = this.consumerRequests.getFirst().getSchedulingTimeStamp();

            if(productionRequestTimeStamp < consumptionRequestTimeStamp){
                return this.producerRequests.removeFirst();
            }
            return this.consumerRequests.removeFirst();
        }
        else if(canConsume){
            return this.consumerRequests.removeFirst();
        }
        else if(canProduce) {
            return this.producerRequests.removeFirst();
        }
        else{
            return null;
        }
    }

}
