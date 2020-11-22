package ActiveObject;

public class ProducerFuture {
    private volatile boolean isDone;

    public ProducerFuture(){
        this.isDone = false;
    }

    public boolean queryProducerRequest(){
        return this.isDone;
    }

    public void confirmProducerRequest(){
        this.isDone = true;
    }
}
