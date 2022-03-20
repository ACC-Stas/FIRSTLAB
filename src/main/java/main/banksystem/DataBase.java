package main.banksystem;

import java.io.*;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;

public class DataBase {
    public void Save(Id id, String dbPart, String object) {
        Remove(id, dbPart);
        String fileName = baseAddress + dbPart;
        File file = new File(fileName);
        try {
            // create FileWriter object with file as parameter
            FileWriter fileWriter = new FileWriter(file);

            // create CSVWriter object filewriter object as parameter
            CSVWriter writer = new CSVWriter(fileWriter);

            // adding header to csv
            String[] header = { "Name", "Class", "Marks" };
            writer.writeNext(header);

            // add data to csv
            String[] data1 = { "Aman", "10", "620" };
            writer.writeNext(data1);

            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Remove(Id id, String dbPart) {
        String filename = baseAddress + dbPart;
        File file = new File(filename);
        FileReader fileReader;
        try {
            fileReader = new FileReader(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }

        CSVReader reader = new CSVReader(fileReader);
        try {
            List<String[]> allElements = reader.readAll();
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
    }

    private final String baseAddress = "../../../../../database/";
}
