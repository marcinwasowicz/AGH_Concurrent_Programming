package Assignement1;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        int size = Parameters.size;
        int maxNumItems = Parameters.maxNumItems;
        int numberProducers = Parameters.numberProducers;
        int numberConsumers = Parameters.numberConsumers;

        Thread[] producers = new Thread[numberProducers];
        Thread[] consumers = new Thread[numberConsumers];

        Monitor monitor;

        if(Parameters.hasWaitersMethod){
            monitor = new MonitorVersionW(size);
        }
        else{
            monitor = new MonitorVersionZ(size);
        }

        for(int i = 0; i<producers.length; i++){
            producers[i] = new Thread(new Producer(i, maxNumItems, i, monitor));
            producers[i].start();
        }

        for(int i = 0; i<consumers.length; i++){
            consumers[i] = new Thread(new Consumer(i, maxNumItems, monitor));
            consumers[i].start();
        }

        for (Thread producer : producers) {
            producer.join();
        }

        for (Thread consumer : consumers) {
            consumer.start();
        }
    }
}
