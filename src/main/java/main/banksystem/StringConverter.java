package main.banksystem;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import main.banksystem.commands.BuildBillCommand;
import main.banksystem.commands.RegistryCompanyCommand;
import java.util.*;

public class StringConverter<T> {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    static {
        MAPPER.registerSubtypes(new NamedType(BuildBillCommand.class, "RegistryCommand"));
        MAPPER.registerSubtypes(new NamedType(RegistryCompanyCommand.class, "RegistryCompanyCommand"));
        MAPPER.findAndRegisterModules();
    }

    public Queue<String> Serialize(Queue<T> objects) {
        Queue<String> output = new LinkedList<>();

        for (T object : objects) {
            output.add(this.Serialize(object));
        }

        return output;
    }

    public Queue<T> Deserialize(Queue<String> strings, Class type) {
        Queue<T> output = new LinkedList<>();

        if (strings == null) {
            return null;
        }

        for (String string : strings) {
            output.add(this.Deserialize(string, type));
        }

        return output;
    }

    public Stack<String> Serialize(Stack<T> objects) {
        Stack<String> output = new Stack<>();

        for (T object : objects) {
            output.push(this.Serialize(object));
        }
        return output;
    }

    public Stack<T> Deserialize(Stack<String> strings, Class type) {
        Stack<T> output = new Stack<>();

        if (strings == null) {
            return null;
        }

        for (String string : strings) {
            output.add(this.Deserialize(string, type));
        }

        return output;
    }

    public String Serialize(T object) {
        try {
            return MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }

    public T Deserialize(String string, Class type) {
        T object = null;
        try {
            object = (T) MAPPER.readValue(string, type);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return object;
    }
}
