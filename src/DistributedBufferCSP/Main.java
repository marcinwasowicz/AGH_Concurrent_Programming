package DistributedBufferCSP;

import java.util.ArrayList;

import org.jcsp.lang.Parallel;

public class Main{
    public static void main(String[] args) {
        ArrayList<Producer> producers = new ArrayList<>();
        ArrayList<Consumer> consumers = new ArrayList<>();

        for(int i = 0; i<Parameters.numberOfProducers; i++){
            producers.add(new Producer(Parameters.messageConstant + i));
        }

        for(int i = 0; i<Parameters.numberOfConsumers; i++){
           consumers.add(new Consumer());
        }

        DistributedBuffer buffer = new DistributedBuffer(Parameters.layers);

        Parallel parallel = new Parallel(buffer.compile(producers, consumers));
        
        parallel.run();
    }
}