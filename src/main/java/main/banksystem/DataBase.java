package main.banksystem;

import java.io.*;
import java.nio.file.Files;
import java.util.*;

import main.banksystem.entities.Id;

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
    public static final String CREDITS_PART = "credits.txt";
    public static final String BILLS_PART = "bills.txt";
    public static final String CREDIT_PART = "credits.txt";
    public static final String INSTALLMENT_PART = "installments.txt";
    public static final String DEPOSIT_PART = "deposits.txt";
    public static final String SALARY_PART = "salary.txt";
    public static final Id INIT_USER_ID = new Id(-1L); // special user to register others
    public static final Id INIT_COMPANY_ID = new Id(-2L); // special user to register companies
    private static final String BASE_ADDRESS = "database/";

    public static DataBase getInstance() {
        if (instance == null) {
            instance = new DataBase();
        }
        return instance;
    }

    public void save(Id id, String dbPart, String object) {
        String fileName = BASE_ADDRESS + dbPart;
        File file = new File(fileName);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }

            Map<String, String> data = new HashMap<>();
            String rowData = encoder.decode(Files.readString(file.toPath()));
            if (!Objects.equals(rowData, "")) {
                data = dataConverter.deserialize(rowData, data.getClass());
            }
            data.put(converter.serialize(id), object);
            rowData = dataConverter.serialize(data);

            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(encoder.encode(rowData));
            fileWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public <T> void save(Id id, String dbPart, T object) {
        String fileName = BASE_ADDRESS + dbPart;
        File file = new File(fileName);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }

            Map<String, String> data = new HashMap<>();
            String rowData = encoder.decode(Files.readString(file.toPath()));
            if (!Objects.equals(rowData, "")) {
                data = dataConverter.deserialize(rowData, data.getClass());
            }
            StringConverter<T> converterT = new StringConverter<>();
            data.put(converter.serialize(id), converterT.serialize(object));
            rowData = dataConverter.serialize(data);
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(encoder.encode(rowData));

            fileWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public <T> void saveQueue(Id id, String dbPart, Queue<T> queue) {
        StringConverter<Queue<String>> queueConverter = new StringConverter<>();
        StringConverter<T> converterT = new StringConverter<>();
        Queue<String> stringQueue = converterT.serialize(queue);
        String rawData = queueConverter.serialize(stringQueue);
        this.save(id, dbPart, rawData);
    }

    public <T> void saveStack(Id id, String dbPart, Stack<T> stack) {
        StringConverter<Stack<String>> stackConverter = new StringConverter<>();
        StringConverter<T> converterT = new StringConverter<>();

        Stack<String> stringStack = converterT.serialize(stack);
        String rawData = stackConverter.serialize(stringStack);
        this.save(id, dbPart, rawData);
    }

    public <T> void saveList(Id id, String dbPart, List<T> list) {
        StringConverter<List<String>> stackConverter = new StringConverter<>();
        StringConverter<T> converterT = new StringConverter<>();

        List<String> stringStack = converterT.serialize(list);
        String rawData = stackConverter.serialize(stringStack);
        this.save(id, dbPart, rawData);
    }

    public String download(Id id, String dbPart) {
        String filename = BASE_ADDRESS + dbPart;
        File file = new File(filename);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }

            String rowData = encoder.decode(Files.readString(file.toPath()));
            if (Objects.equals(rowData, "")) {
                return "";
            }
            Map<String, String> data = new HashMap<>();
            data = dataConverter.deserialize(rowData, data.getClass());
            String idStr = converter.serialize(id);

            if (!data.containsKey(idStr)) {
                return "";
            }

            return data.get(idStr);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

    public <T> T download(Id id, String dbPart, Class type) {
        String filename = BASE_ADDRESS + dbPart;
        File file = new File(filename);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }

            String rowData = encoder.decode(Files.readString(file.toPath()));
            if (Objects.equals(rowData, "")) {
                return null;
            }
            Map<String, String> data = new HashMap<>();
            data = dataConverter.deserialize(rowData, data.getClass());
            String idStr = converter.serialize(id);

            if (!data.containsKey(idStr)) {
                return null;
            }

            String rowObject = data.get(idStr);
            StringConverter<T> converterT = new StringConverter<>();
            T object = converterT.deserialize(rowObject, type);

            return object;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public <T> Map<Id, T> downloadMap(String dbPart, Class type) { // don't work for T = queue, list, stack, map...
        String filename = BASE_ADDRESS + dbPart;
        File file = new File(filename);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }

            String rowData = encoder.decode(Files.readString(file.toPath()));
            if (Objects.equals(rowData, "")) {
                return new HashMap<>();
            }
            Map<String, String> data = new HashMap<>();
            data = dataConverter.deserialize(rowData, data.getClass());

            Map<Id, T> result = new HashMap<>();
            for (Map.Entry<String, String> entry : data.entrySet()) {
                StringConverter<T> converterT = new StringConverter<>();
                result.put(converter.deserialize(entry.getKey(), Id.class), converterT.deserialize(entry.getValue(), type));
            }

            return result;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return new HashMap<>();
    }

    public <T> Map<Id, Queue<T>> downloadQueue(String dbPart, Class type) {
        String filename = BASE_ADDRESS + dbPart;
        File file = new File(filename);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }

            String rowData = encoder.decode(Files.readString(file.toPath()));
            if (Objects.equals(rowData, "")) {
                return new HashMap<>();
            }

            Map<String, String> data = new HashMap<>();
            data = dataConverter.deserialize(rowData, data.getClass());

            StringConverter<Queue<String>> queueConverter = new StringConverter<>();
            StringConverter<T> converterT = new StringConverter<>();

            Map<Id, Queue<T>> result = new HashMap<>();
            for (Map.Entry<String, String> entry : data.entrySet()) {
                Queue<String> queue = queueConverter.deserialize(entry.getValue(), Queue.class);
                Queue<T> queueT = converterT.deserialize(queue, type);
                result.put(converter.deserialize(entry.getKey(), Id.class), queueT);
            }

            return result;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return new HashMap<>();
    }

    public <T> Map<Id, Stack<T>> downloadStack(String dbPart, Class type) {
        String filename = BASE_ADDRESS + dbPart;
        File file = new File(filename);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }

            String rowData = encoder.decode(Files.readString(file.toPath()));
            if (Objects.equals(rowData, "")) {
                return new HashMap<>();
            }

            Map<String, String> data = new HashMap<>();
            data = dataConverter.deserialize(rowData, data.getClass());

            StringConverter<Stack<String>> stackConverter = new StringConverter<>();
            StringConverter<T> converterT = new StringConverter<>();

            Map<Id, Stack<T>> result = new HashMap<>();
            for (Map.Entry<String, String> entry : data.entrySet()) {
                Stack<String> stack = stackConverter.deserialize(entry.getValue(), Stack.class);
                Stack<T> stackT = converterT.deserialize(stack, type);
                result.put(converter.deserialize(entry.getKey(), Id.class), stackT);
            }

            return result;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return new HashMap<>();
    }

    public <T> Map<Id, List<T>> downloadList(String dbPart, Class type) {
        String filename = BASE_ADDRESS + dbPart;
        File file = new File(filename);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }

            String rowData = encoder.decode(Files.readString(file.toPath()));
            if (Objects.equals(rowData, "")) {
                return new HashMap<>();
            }

            Map<String, String> data = new HashMap<>();
            data = dataConverter.deserialize(rowData, data.getClass());

            StringConverter<List<String>> listConverter = new StringConverter<>();
            StringConverter<T> converterT = new StringConverter<>();

            Map<Id, List<T>> result = new HashMap<>();
            for (Map.Entry<String, String> entry : data.entrySet()) {
                List<String> list = listConverter.deserialize(entry.getValue(), List.class);
                List<T> listT = converterT.deserialize(list, type);
                result.put(converter.deserialize(entry.getKey(), Id.class), listT);
            }

            return result;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return new HashMap<>();
    }

    public void remove(Id id, String dbPart) {
        String filename = BASE_ADDRESS + dbPart;
        File file = new File(filename);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }

            String rowData = encoder.decode(Files.readString(file.toPath()));
            if (Objects.equals(rowData, "")) {
                return;
            }

            Map<String, String> data = new HashMap<>();
            data = dataConverter.deserialize(rowData, data.getClass());
            String idStr = converter.serialize(id);
            if (!data.containsKey(idStr)) {
                return;
            }

            data.remove(idStr);

            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(encoder.encode(dataConverter.serialize(data)));
            fileWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
