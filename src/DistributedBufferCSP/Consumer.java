package DistributedBufferCSP;

import org.jcsp.lang.Channel;
import org.jcsp.lang.AltingChannelInput;
import org.jcsp.lang.Any2OneChannel;
import org.jcsp.lang.CSProcess;
import org.jcsp.lang.SharedChannelOutput;
import org.jcsp.util.InfiniteBuffer;

public class Consumer implements CSProcess{

    private Any2OneChannel pipe;
    private AltingChannelInput input;
   
    public Consumer(){
        this.pipe = Channel.any2one(new InfiniteBuffer());
        this.input = pipe.in();
    }

    @Override
    public void run(){
        while(true){
            String message = input.read().toString();
            System.out.println("Received: " + message);
        }
    }

    public SharedChannelOutput getOutput(){
        return this.pipe.out();
    }

}
