package DistributedBufferCSP;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Parameters {
    
    private JSONObject config;

    public Parameters(String configPath) throws FileNotFoundException, IOException, ParseException {
        this.config = (JSONObject) new JSONParser().parse(new FileReader(configPath));
    }

    public int getNumberOfProducers(){
        return (int) (long) this.config.get("numberOfProducers");
    }

    public int getNumberOfConsumers(){
        return (int) (long) this.config.get("numberOfConsumers");
    }

    public String getMessageConstant(){
        return (String) this.config.get("messageConstant");
    }

    public int[] getLayers(){
       Object[] layers = ((JSONArray) this.config.get("layers")).toArray();
       return Arrays.stream(layers).mapToInt(o -> (int)(long)o).toArray();
    }

}