package main.banksystem;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class StringConverter<T> {
    public String Serialize(T object) {
        String serializedObject = "";

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
    }

    public T Deserialize(String string) {
        T obj = null;
        try {
            byte[] b = string.getBytes();
            ByteArrayInputStream bi = new ByteArrayInputStream(b);
            ObjectInputStream si = new ObjectInputStream(bi);
            obj = (T) si.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return obj;
    }
}
