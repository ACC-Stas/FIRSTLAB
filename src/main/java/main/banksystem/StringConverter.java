package main.banksystem;

import java.io.*;
import java.util.Base64;

public class StringConverter<T> {
    public String Serialize(T object) {
        String serializedObject = "";

        try (ByteArrayOutputStream bos = new ByteArrayOutputStream(); ObjectOutput out = new ObjectOutputStream(bos)) {
            out.writeObject(object);
            final byte[] byteArray = bos.toByteArray();
            return Base64.getEncoder().encodeToString(byteArray);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return serializedObject;
    }

    public T Deserialize(String string) {

        final byte[] bytes = Base64.getDecoder().decode(string);
        try (ByteArrayInputStream bis = new ByteArrayInputStream(bytes); ObjectInput in = new ObjectInputStream(bis)) {
            return (T) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
