package main.banksystem;

import java.io.*;
import java.nio.file.Files;
import java.util.*;

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

    public static DataBase GetInstance() {
        if (instance == null) {
            instance = new DataBase();
        }
        return instance;
    }

    public void Save(Id id, String dbPart, String object) {
        Remove(id, dbPart);
        String fileName = baseAddress + dbPart;
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

    public String Download(Id id, String dbPart) {
        String filename = baseAddress + dbPart;
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
        String filename = baseAddress + dbPart;
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

    public Map<Id, String> DownloadList(String dbPart) {
        String filename = baseAddress + dbPart;
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
        String filename = baseAddress + dbPart;
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

    private final String baseAddress = "database/";
}
