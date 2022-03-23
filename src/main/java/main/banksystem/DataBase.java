package main.banksystem;

import java.io.*;
import java.nio.file.Files;
import java.util.*;

import main.banksystem.commands.ICommand;
import main.banksystem.containers.Id;

public class DataBase {

    private DataBase() {
        encoder = new Encoder(encoderKey);
        converter = new StringConverter<>();
    }

    private static DataBase instance;
    private static final char encoderKey = 0;
    private final Encoder encoder;
    private final StringConverter<Id> converter;
    private static final StringConverter<Map<String, String>> dataConverter = new StringConverter<>();

    public static final String USER_PART = "user.txt";
    public static final String STACK_PART = "stack.txt";
    public static final String QUEUE_PART = "queue.txt";
    public static final String COMPANY_PART = "company.txt";
    public static final String INDEXES_PART = "indexes.txt";
    public static final String BILLS_PART = "bills.txt";
    public static final Id INIT_USER_ID = new Id(-1L); // special user to register others
    private static final String BASE_ADDRESS = "database/";

    public static DataBase GetInstance() {
        if (instance == null) {
            instance = new DataBase();
        }
        return instance;
    }

    public void Save(Id id, String dbPart, String object) {
        String fileName = BASE_ADDRESS + dbPart;
        File file = new File(fileName);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }

            Map<String, String> data = new HashMap<>();
            FileWriter fileWriter = new FileWriter(file);
            String rowData = encoder.Decode(Files.readString(file.toPath()));
            if (!Objects.equals(rowData, "")) {
                data = dataConverter.Deserialize(rowData, data.getClass());
            }
            data.put(converter.Serialize(id), object);
            rowData = dataConverter.Serialize(data);
            fileWriter.write(encoder.Encode(rowData));

            fileWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public <T> void SaveT(Id id, String dbPart, T object) {
        String fileName = BASE_ADDRESS + dbPart;
        File file = new File(fileName);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }

            Map<String, String> data = new HashMap<>();
            FileWriter fileWriter = new FileWriter(file);
            String rowData = encoder.Decode(Files.readString(file.toPath()));
            if (!Objects.equals(rowData, "")) {
                data = dataConverter.Deserialize(rowData, data.getClass());
            }
            StringConverter<T> converterT = new StringConverter<>();
            data.put(converter.Serialize(id), converterT.Serialize(object));
            rowData = dataConverter.Serialize(data);
            fileWriter.write(encoder.Encode(rowData));

            fileWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public <T> void SaveQueue(Id id, String dbPart, Queue<T> queue) {
        StringConverter<Queue<String>> queueConverter = new StringConverter<>();
        StringConverter<T> converterT = new StringConverter<>();
        Queue<String> stringQueue = converterT.Serialize(queue);
        String rawData = queueConverter.Serialize(stringQueue);
        this.Save(id, dbPart, rawData);
    }

    public <T> void SaveStack(Id id, String dbPart, Stack<T> stack) {
        StringConverter<Stack<String>> stackConverter = new StringConverter<>();
        StringConverter<T> converterT = new StringConverter<>();

        Stack<String> stringStack = converterT.Serialize(stack);
        String rawData = stackConverter.Serialize(stringStack);
        this.Save(id, dbPart, rawData);
    }

    public String Download(Id id, String dbPart) {
        String filename = BASE_ADDRESS + dbPart;
        File file = new File(filename);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }

            String rowData = encoder.Decode(Files.readString(file.toPath()));
            if (Objects.equals(rowData, "")) {
                return "";
            }
            Map<String, String> data = new HashMap<>();
            data = dataConverter.Deserialize(rowData, data.getClass());
            String idStr = converter.Serialize(id);

            if (!data.containsKey(idStr)) {
                return "";
            }

            return data.get(idStr);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

    public <T> T Download(Id id, String dbPart, Class type) {
        String filename = BASE_ADDRESS + dbPart;
        File file = new File(filename);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }

            String rowData = encoder.Decode(Files.readString(file.toPath()));
            if (Objects.equals(rowData, "")) {
                return null;
            }
            Map<String, String> data = new HashMap<>();
            data = dataConverter.Deserialize(rowData, data.getClass());
            String idStr = converter.Serialize(id);

            if (!data.containsKey(idStr)) {
                return null;
            }

            String rowObject = data.get(idStr);
            StringConverter<T> converterT = new StringConverter<>();
            T object = converterT.Deserialize(rowObject, type);

            return object;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public <T> Map<Id, T> DownloadMap(String dbPart, Class type) { // don't work for T = queue, list, stack, map...
        String filename = BASE_ADDRESS + dbPart;
        File file = new File(filename);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }

            String rowData = encoder.Decode(Files.readString(file.toPath()));
            if (Objects.equals(rowData, "")) {
                return new HashMap<>();
            }
            Map<String, String> data = new HashMap<>();
            data = dataConverter.Deserialize(rowData, data.getClass());

            Map<Id, T> result = new HashMap<>();
            for (Map.Entry<String, String> entry : data.entrySet()) {
                StringConverter<T> converterT = new StringConverter<>();
                result.put(converter.Deserialize(entry.getKey(), Id.class), converterT.Deserialize(entry.getValue(), type));
            }

            return result;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return new HashMap<>();
    }

    public <T> Map<Id, Queue<T>> DownloadQueue(String dbPart, Class type) {
        String filename = BASE_ADDRESS + dbPart;
        File file = new File(filename);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }

            String rowData = encoder.Decode(Files.readString(file.toPath()));
            if (Objects.equals(rowData, "")) {
                return new HashMap<>();
            }

            Map<String, String> data = new HashMap<>();
            data = dataConverter.Deserialize(rowData, data.getClass());

            StringConverter<Queue<String>> queueConverter = new StringConverter<>();
            StringConverter<T> converterT = new StringConverter<>();

            Map<Id, Queue<T>> result = new HashMap<>();
            for (Map.Entry<String, String> entry : data.entrySet()) {
                Queue<String> queue = queueConverter.Deserialize(entry.getValue(), Queue.class);
                Queue<T> queueT = converterT.Deserialize(queue, type);
                result.put(converter.Deserialize(entry.getKey(), Id.class), queueT);
            }

            return result;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return new HashMap<>();
    }

    public <T> Map<Id, Stack<T>> DownloadStack(String dbPart, Class type) {
        String filename = BASE_ADDRESS + dbPart;
        File file = new File(filename);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }

            String rowData = encoder.Decode(Files.readString(file.toPath()));
            if (Objects.equals(rowData, "")) {
                return new HashMap<>();
            }

            Map<String, String> data = new HashMap<>();
            data = dataConverter.Deserialize(rowData, data.getClass());

            StringConverter<Stack<String>> stackConverter = new StringConverter<>();
            StringConverter<T> converterT = new StringConverter<>();

            Map<Id, Stack<T>> result = new HashMap<>();
            for (Map.Entry<String, String> entry : data.entrySet()) {
                Stack<String> stack = stackConverter.Deserialize(entry.getValue(), Stack.class);
                Stack<T> stackT = converterT.Deserialize(stack, type);
                result.put(converter.Deserialize(entry.getKey(), Id.class), stackT);
            }

            return result;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return new HashMap<>();
    }

    public Map<Id, String> DownloadList(String dbPart) {
        String filename = BASE_ADDRESS + dbPart;
        File file = new File(filename);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }

            String rowData = encoder.Decode(Files.readString(file.toPath()));
            if (Objects.equals(rowData, "")) {
                return new HashMap<>();
            }
            Map<String, String> data = new HashMap<>();
            data = dataConverter.Deserialize(rowData, data.getClass());

            Map<Id, String> result = new HashMap<>();
            for (Map.Entry<String, String> entry : data.entrySet()) {
                result.put(converter.Deserialize(entry.getKey(), Id.class), entry.getValue());
            }

            return result;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return new HashMap<>();
    }

    public void Remove(Id id, String dbPart) {
        String filename = BASE_ADDRESS + dbPart;
        File file = new File(filename);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }

            String rowData = encoder.Decode(Files.readString(file.toPath()));
            if (Objects.equals(rowData, "")) {
                return;
            }

            Map<String, String> data = new HashMap<>();
            data = dataConverter.Deserialize(rowData, data.getClass());
            String idStr = converter.Serialize(id);
            if (!data.containsKey(idStr)) {
                return;
            }

            data.remove(idStr);

            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(encoder.Encode(dataConverter.Serialize(data)));
            fileWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
