package ActiveObject;

import java.util.HashMap;

public class Servant {
    private int maximumSize;
    private int[] data;
    private int writingPoint;
    private int readingPoint;
    private int numberOfItems;

    public Servant(int maximumSize){
        this.maximumSize = maximumSize;
        this.data = new int[maximumSize];
        this.writingPoint = 0;
        this.readingPoint = 0;
        this.numberOfItems = 0;
    }

    public boolean canProduce(int numberOfItems){
        return this.numberOfItems + numberOfItems <= this.maximumSize;
    }

    public boolean canConsume(int numberOfItems){
        return this.numberOfItems - numberOfItems >= 0;
    }

    public HashMap<Integer, Integer> addItems(int[] items){
        HashMap<Integer, Integer> indexes = new HashMap<>();
        for(int item : items){
            this.data[this.writingPoint] = item;
            indexes.put(writingPoint, item);
            this.writingPoint++;
            this.writingPoint %= this.maximumSize;
            this.numberOfItems++;
        }
        return indexes;
    }

    public HashMap<Integer, Integer> consumeItems(int numberOfItems){
        HashMap<Integer, Integer> indexes = new HashMap<>();
        for(int i = 0; i<numberOfItems; i++){
            indexes.put(this.readingPoint, this.data[readingPoint]);
            this.readingPoint++;
            this.readingPoint %= this.maximumSize;
            this.numberOfItems--;
        }
        return indexes;
    }
}
