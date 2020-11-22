package ActiveObject;

public class ConsumerRequest {
    private long schedulingTimeStamp;
    private int numberOtItems;
    private Future consumerFuture;
    private int consumerID;

    public ConsumerRequest(int consumerID, int numberOtItems, Future consumerFuture){
        this.numberOtItems = numberOtItems;
        this.schedulingTimeStamp = System.nanoTime();
        this.consumerFuture = consumerFuture;
        this.consumerID = consumerID;
    }

    public long getSchedulingTimeStamp() {
        return schedulingTimeStamp;
    }

    public int getNumberOtItems() {
        return numberOtItems;
    }

    public Future getConsumerFuture(){
        return this.consumerFuture;
    }

    public int getConsumerID(){
        return this.consumerID;
    }
}
