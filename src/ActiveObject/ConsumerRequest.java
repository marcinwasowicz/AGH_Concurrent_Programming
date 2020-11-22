package ActiveObject;

public class ConsumerRequest {
    private long schedulingTimeStamp;
    private int numberOtItems;
    private ConsumerFuture consumerFuture;

    public ConsumerRequest(int numberOtItems, ConsumerFuture consumerFuture){
        this.numberOtItems = numberOtItems;
        this.schedulingTimeStamp = System.nanoTime();
        this.consumerFuture = consumerFuture;
    }

    public long getSchedulingTimeStamp() {
        return schedulingTimeStamp;
    }

    public int getNumberOtItems() {
        return numberOtItems;
    }

    public ConsumerFuture getConsumerFuture(){
        return this.consumerFuture;
    }
}
