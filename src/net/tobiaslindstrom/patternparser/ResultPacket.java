package net.tobiaslindstrom.patternparser;

import net.tobiaslindstrom.patternparser.exceptions.ResultPacketMismatchException;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Tobias on 10/27/2016.
 */
public class ResultPacket {

    private Map<String, Object> content = new HashMap<>();

    private void addObject(String key, Object object) {
        this.content.put(key, object);
    }

    public void addString(String key, String value) {
        this.addObject(key, value);
    }

    public void addDouble(String key, Double value) {
        this.addObject(key, value);
    }

    public void addInt(String key, int value) { this.addObject(key, value); }

    private Object getObject(String key) throws ResultPacketMismatchException {
        if(this.content.containsKey(key)) {
            return this.content.get(key);
        } else {
            throw new ResultPacketMismatchException("Error: No result with the key \"" + key +"\" was found in the ResultPacket!");
        }
    }

    public String getString(String key) throws ResultPacketMismatchException {
        return (String) this.getObject(key);
    }

    public Double getDouble(String key) throws ResultPacketMismatchException {
        return (Double) this.getObject(key);
    }

    public int getInt(String key) throws ResultPacketMismatchException { return (int) this.getObject(key); }

    public String toString() {
        return this.content.values().toString();
    }

}
