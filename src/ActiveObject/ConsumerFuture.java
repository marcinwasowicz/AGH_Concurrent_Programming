package ActiveObject;

import java.util.LinkedList;

public class ConsumerFuture {
    private volatile LinkedList<Integer> items;

    public ConsumerFuture(){
        this.items = null;
    }

    public LinkedList<Integer> queryConsumerRequest(){
        return this.items;
    }

    public void confirmConsumerRequest(LinkedList<Integer> items){
        this.items = items;
    }
}
