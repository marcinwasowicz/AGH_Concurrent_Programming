package ActiveObject;

public class ProducerRequest {

    private long schedulingTimeStamp;
    private int[] items;
    private Future producerFuture;
    private int producerID;

    public ProducerRequest(int producerID, int[] items, Future producerFuture){
        this.items = items;
        this.schedulingTimeStamp = System.nanoTime();
        this.producerFuture = producerFuture;
        this.producerID = producerID;
    }

    public int[] getItems(){
        return this.items;
    }

    public int getNumberOfItems(){
        return this.items.length;
    }

    public long getSchedulingTimeStamp(){
        return this.schedulingTimeStamp;
    }

    public Future getProducerFuture(){
        return this.producerFuture;
    }

    public int getProducerID() {
        return producerID;
    }
}
