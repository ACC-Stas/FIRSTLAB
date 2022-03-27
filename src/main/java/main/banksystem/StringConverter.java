package main.banksystem;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.*;

public class StringConverter<T> {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    static {
        MAPPER.findAndRegisterModules();
    }

    public Queue<String> serialize(Queue<T> objects) {
        Queue<String> output = new LinkedList<>();

        for (T object : objects) {
            output.add(this.serialize(object));
        }

        return output;
    }

    public Queue<T> deserialize(Queue<String> strings, Class type) {
        Queue<T> output = new LinkedList<>();

        if (strings == null) {
            return null;
        }

        for (String string : strings) {
            output.add(this.deserialize(string, type));
        }

        return output;
    }

    public Stack<String> serialize(Stack<T> objects) {
        Stack<String> output = new Stack<>();

        for (T object : objects) {
            output.push(this.serialize(object));
        }
        return output;
    }

    public List<String> serialize(List<T> objects) {
        List<String> output = new LinkedList<>();

        for (T object : objects) {
            output.add(this.serialize(object));
        }
        return output;
    }

    public Stack<T> deserialize(Stack<String> strings, Class type) {
        Stack<T> output = new Stack<>();

        if (strings == null) {
            return null;
        }

        for (String string : strings) {
            output.add(this.deserialize(string, type));
        }

        return output;
    }

    public List<T> deserialize(List<String> strings, Class type) {
        List<T> output = new Stack<>();

        if (strings == null) {
            return null;
        }

        for (String string : strings) {
            output.add(this.deserialize(string, type));
        }

        return output;
    }

    public String serialize(T object) {
        try {
            return MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }

    public T deserialize(String string, Class type) {
        T object = null;
        try {
            object = (T) MAPPER.readValue(string, type);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return object;
    }
}
