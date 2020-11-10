package Assignement1;

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

    public void addItem(int numItems, int item){
        for(int i = 0; i<numItems; i++){
            System.out.println("Wrote " + item + " at index: " + this.writingPoint);
            this.data[this.writingPoint] = item;
            this.writingPoint++;
            this.writingPoint %= this.size;
            this.numberOfItems++;
        }
    }

    public void readItems(int numItems){
        for(int i = 0; i<numItems; i++){
            System.out.println("Read " + this.data[this.readingPoint] + " at index " + this.readingPoint);
            this.readingPoint++;
            this.readingPoint %= this.size;
            this.numberOfItems--;
        }
    }

    public boolean canWrite(int numItems){
        return this.numberOfItems + numItems <= this.size;
    }

    public boolean canRead(int numItems){
        return this.numberOfItems - numItems >= 0;
    }
}
