package DistributedBufferCSP;

import java.util.ArrayList;
import java.util.Random;

import org.jcsp.lang.AltingChannelInput;
import org.jcsp.lang.Any2OneChannel;
import org.jcsp.lang.CSProcess;
import org.jcsp.lang.SharedChannelOutput;

public class BufferNode implements CSProcess{

    private Any2OneChannel pipe;
    private AltingChannelInput input;
    private ArrayList<SharedChannelOutput> outputs;
    private Random outputChooser;
    
    public BufferNode(Any2OneChannel pipe){
        this.pipe = pipe;
        this.input = pipe.in();
        this.outputChooser = new Random();
        this.outputs = new ArrayList<>();
    }

    @Override
    public void run(){
        while(true){
            String message = input.read().toString();
            this.chooseOutput().write(message);
        }
    }

    public void setOutputs(ArrayList<BufferNode> nodes){
        for(BufferNode bufferNode : nodes){
            this.outputs.add(bufferNode.getOutput());
        }
    }

    public void setOutputsFromConsumers(ArrayList<Consumer> nodes){
        for(Consumer consumer : nodes){
            this.outputs.add(consumer.getOutput());
        }
    }

    private SharedChannelOutput chooseOutput(){
        return this.outputs.get(this.outputChooser.nextInt(this.outputs.size()));
    }

    public SharedChannelOutput getOutput(){
        return this.pipe.out();
    }

}
