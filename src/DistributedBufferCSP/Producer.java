package DistributedBufferCSP;

import java.util.ArrayList;
import java.util.Random;

import org.jcsp.lang.CSProcess;
import org.jcsp.lang.SharedChannelOutput;

public class Producer implements CSProcess{

    private ArrayList<SharedChannelOutput> outputs;
    private Random outputChooser;
    private String message;
    
    public Producer(String message){
        this.message = message;
        this.outputChooser = new Random();
        this.outputs = new ArrayList<>();
    }

    @Override
    public void run(){
        while(true){
            this.chooseOutput().write(message);
            System.out.println("Sent: " + message);
        }
    }

    public void setOutputs(ArrayList<BufferNode> nodes){
        for(BufferNode bufferNode : nodes){
            this.outputs.add(bufferNode.getOutput());
        }
    }

    private SharedChannelOutput chooseOutput(){
        return this.outputs.get(this.outputChooser.nextInt(this.outputs.size()));
    }
}