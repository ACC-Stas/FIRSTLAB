package main.banksystem;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;

public class DataBase {
    public void Save(Id id, String dbPart, String object) {
        Remove(id, dbPart);
        String fileName = baseAddress + dbPart;
        File file = new File(fileName);
        try {
            FileWriter fileWriter = new FileWriter(file);
            CSVWriter writer = new CSVWriter(fileWriter);

            String[] data = {id.Serialize(), object};
            writer.writeNext(data);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Download(Id id, String dbPart, IConstructor constructor) {
        String filename = baseAddress + dbPart;
        File file = new File(filename);
        FileReader fileReader;
        try {
            fileReader = new FileReader(file);

            CSVReader reader = new CSVReader(fileReader);
            List<String[]> allElements;
            allElements = reader.readAll();
            reader.close();

            for (String[] element : allElements) {
                if (Objects.equals(element[0], id.Serialize())) {
                    constructor.Construct(element[1]);
                    return;
                }
            }

        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
    }

    public void Remove(Id id, String dbPart) {
        String filename = baseAddress + dbPart;
        File file = new File(filename);
        FileReader fileReader;
        try {
            fileReader = new FileReader(file);

            CSVReader reader = new CSVReader(fileReader);
            List<String[]> allElements;
            allElements = reader.readAll();
            reader.close();

            List<String[]> found = new ArrayList<>();
            for (String[] element : allElements) {
                if (Objects.equals(element[0], id.Serialize())) {
                    found.add(element);
                }
            }
            allElements.removeAll(found);

            FileWriter fileWriter = new FileWriter(file);
            CSVWriter writer = new CSVWriter(fileWriter);
            writer.writeAll(allElements);
            writer.close();

        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
    }

    private final String baseAddress = "../../../../../database/";
}
