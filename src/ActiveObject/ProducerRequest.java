package ActiveObject;

public class ProducerRequest {

    private long schedulingTimeStamp;
    private int[] items;
    private ProducerFuture producerFuture;

    public ProducerRequest(int[] items, ProducerFuture producerFuture){
        this.items = items;
        this.schedulingTimeStamp = System.nanoTime();
        this.producerFuture = producerFuture;
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

    public ProducerFuture getProducerFuture(){
        return this.producerFuture;
    }
}
