package ActiveObject;

public class ConsumerRequest {
    private long schedulingTimeStamp;
    private int consumerID;
    private int numberOtItems;

    public ConsumerRequest(int consumerID, int numberOtItems){
        this.consumerID = consumerID;
        this.numberOtItems = numberOtItems;
        this.schedulingTimeStamp = System.nanoTime();
    }

    public long getSchedulingTimeStamp() {
        return schedulingTimeStamp;
    }

    public int getConsumerID() {
        return consumerID;
    }

    public int getNumberOtItems() {
        return numberOtItems;
    }
}
