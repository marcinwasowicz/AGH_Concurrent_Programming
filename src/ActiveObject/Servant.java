package ActiveObject;

import java.util.LinkedList;

public class Servant {
    private int maximumSize;
    private LinkedList<Integer> data;

    public Servant(int maximumSize){
        this.maximumSize = maximumSize;
        this.data = new LinkedList<>();
    }

    public boolean canProduce(int numberOfItems){
        return this.data.size() + numberOfItems <= this.maximumSize;
    }

    public boolean canConsume(int numberOfItems){
        return this.data.size() - numberOfItems >= 0;
    }

    public void addItems(int[] items){
        for(int item : items){
            this.data.addLast(item);
        }
    }

    public LinkedList<Integer> consumeItems(int numberOfItems){
        LinkedList<Integer> items = new LinkedList<>();
        for(int i = 0; i<numberOfItems; i++){
            items.addLast(this.data.removeFirst());
        }
        return items;
    }
}
