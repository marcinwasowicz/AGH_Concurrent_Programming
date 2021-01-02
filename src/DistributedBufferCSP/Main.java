package DistributedBufferCSP;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.jcsp.lang.Parallel;
import org.json.simple.parser.ParseException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException, IOException, ParseException {
        ArrayList<Producer> producers = new ArrayList<>();
        ArrayList<Consumer> consumers = new ArrayList<>();

        Parameters parameters = new Parameters(args[0]);

        for(int i = 0; i<parameters.getNumberOfProducers(); i++){
            producers.add(new Producer(parameters.getMessageConstant() + i));
        }

        for(int i = 0; i<parameters.getNumberOfConsumers(); i++){
           consumers.add(new Consumer());
        }

        DistributedBuffer buffer = new DistributedBuffer(parameters.getLayers());

        Parallel parallel = new Parallel(buffer.compile(producers, consumers));
        
        parallel.run();
    }
}