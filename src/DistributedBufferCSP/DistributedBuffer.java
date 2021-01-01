package DistributedBufferCSP;
import org.jcsp.lang.CSProcess;
import org.jcsp.lang.Channel;
import org.jcsp.util.InfiniteBuffer;

import java.util.ArrayList;

public class DistributedBuffer {

    private ArrayList<ArrayList<BufferNode>> network;

    public DistributedBuffer(int[] layers){
        this.network = new ArrayList<>();
        for(int layer : layers){
            ArrayList<BufferNode> nodesLayer = new ArrayList<>();
            for(int i = 0; i<layer; i++){
                nodesLayer.add(new BufferNode(Channel.any2one(new InfiniteBuffer())));
            }
            this.network.add(nodesLayer);
        }
    }

    public CSProcess[] compile(ArrayList<Producer> producers, ArrayList<Consumer> consumers){
        ArrayList<CSProcess> compiled = new ArrayList<>();

        for(Producer producer : producers){
            producer.setOutputs(this.network.get(0));
        }

        for(int layer = 0; layer < this.network.size() - 1; layer++){
            for(BufferNode node : this.network.get(layer)){
                node.setOutputs(this.network.get(layer + 1));
            }
        }

        for(BufferNode node : this.network.get(this.network.size() - 1)){
            node.setOutputsFromConsumers(consumers);
        }

        for(ArrayList<BufferNode> layer : this.network){
            compiled.addAll(layer);
        }

        compiled.addAll(producers);
        compiled.addAll(consumers);

        return compiled.toArray(new CSProcess[compiled.size()]);
    }
    
}
