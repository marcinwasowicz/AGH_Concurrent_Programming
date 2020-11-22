package ActiveObject;

public class World {
    public static void main(String[] args) throws InterruptedException {
        Servant servant = new Servant(Parameters.servantSize);
        Future future = new Future(Parameters.numberOfProducers, Parameters.numberOfConsumers);

        ActivationQueue activationQueue = new ActivationQueue(future, servant);
        Thread scheduler = new Thread(new Scheduler(servant, future, activationQueue));

        Thread[] producers = new Thread[Parameters.numberOfProducers];
        Thread[] consumers = new Thread[Parameters.numberOfConsumers];

        for(int i = 0; i<producers.length; i++){
            producers[i] = new Thread(new Producer(i, Parameters.maximumSize, activationQueue));
        }

        for(int i = 0; i<consumers.length; i++){
            consumers[i] = new Thread(new Consumer(i, Parameters.maximumSize, activationQueue));
        }

        scheduler.start();

        for(Thread producer : producers){
            producer.start();
        }

        for(Thread consumer : consumers){
            consumer.start();
        }

        scheduler.join();

        for(Thread producer : producers){
            producer.join();
        }

        for(Thread consumer : consumers){
            consumer.join();
        }
    }
}
