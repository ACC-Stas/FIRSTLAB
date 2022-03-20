package main.banksystem;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import com.opencsv.CSVWriter;

public class DataBase {
    public void Save(Id id, String dbPart, String object) {
        String fileName = baseAddress + dbPart;

        // first create file object for file placed at location
        // specified by filepath
        File file = new File(fileName);
        try {
            // create FileWriter object with file as parameter
            FileWriter outputfile = new FileWriter(file);

            // create CSVWriter object filewriter object as parameter
            CSVWriter writer = new CSVWriter(outputfile);

            // adding header to csv
            String[] header = { "Name", "Class", "Marks" };
            writer.writeNext(header);

            // add data to csv
            String[] data1 = { "Aman", "10", "620" };
            writer.writeNext(data1);
            String[] data2 = { "Suraj", "10", "630" };
            writer.writeNext(data2);

            // closing writer connection
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Remove(Id id, String dbPart, String object) {
        String filename = baseAddress + dbPart;
    }

    private final String baseAddress = "../../../../../database/";
}
