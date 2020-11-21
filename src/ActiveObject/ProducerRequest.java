package ActiveObject;

public class ProducerRequest {

    private long schedulingTimeStamp;
    private int[] items;
    private int producerID;

    public ProducerRequest(int producerID, int[] items){
        this.producerID = producerID;
        this.items = items;
        this.schedulingTimeStamp = System.nanoTime();
    }

    public int[] getItems(){
        return this.items;
    }

    public int getNumberOfItems(){
        return this.items.length;
    }

    public int getProducerID(){
        return this.producerID;
    }

    public long getSchedulingTimeStamp(){
        return this.schedulingTimeStamp;
    }
}
