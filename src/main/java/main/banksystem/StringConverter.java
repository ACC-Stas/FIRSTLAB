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
        /*
        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            ObjectOutputStream so = new ObjectOutputStream(bo);
            so.writeObject(object);
            so.flush();
            serializedObject = bo.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return serializedObject;
         */
        return serializedObject;
    }

    public T Deserialize(String string) {

        final byte[] bytes = Base64.getDecoder().decode(string);
        try (ByteArrayInputStream bis = new ByteArrayInputStream(bytes); ObjectInput in = new ObjectInputStream(bis)) {
            return (T) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        /*
        T obj = null;
        try {
            byte[] b = string.getBytes();
            ByteArrayInputStream bi = new ByteArrayInputStream(b);
            ObjectInputStream si = new ObjectInputStream(bi);
            obj = (T) si.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
         */

        return null;
    }
}
