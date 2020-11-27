package Assignement1;

import java.util.HashMap;

public class Buffer {
    private final int size;
    private final int[] data;

    private int writingPoint;
    private int readingPoint;
    private int numberOfItems;

    public Buffer(int size){
        this.size = size;
        this.data = new int[this.size];

        this.writingPoint = 0;
        this.readingPoint = 0;
        this.numberOfItems = 0;
    }

    public HashMap<Integer, Integer> addItem(int[] items){
        HashMap<Integer, Integer> result = new HashMap<>();
        for(int item : items){
            result.put(this.writingPoint, item);
            this.data[this.writingPoint] = item;
            this.writingPoint++;
            this.writingPoint %= this.size;
            this.numberOfItems++;
        }
        return result;
    }

    public HashMap<Integer, Integer> readItems(int numItems){
        HashMap<Integer, Integer> result = new HashMap<>();
        for(int i = 0; i<numItems; i++){
            result.put(this.readingPoint, this.data[this.readingPoint]);
            this.readingPoint++;
            this.readingPoint %= this.size;
            this.numberOfItems--;
        }
        return result;
    }

    public boolean canWrite(int numItems){
        return this.numberOfItems + numItems <= this.size;
    }

    public boolean canRead(int numItems){
        return this.numberOfItems - numItems >= 0;
    }
}
