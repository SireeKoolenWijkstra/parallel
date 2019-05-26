/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mediaansequential;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

/**
 * Reads the 4 Excel files and makes a Array of strings. It walks over the array
 * and for each record in the array it looks for the header "quality" and puts
 * the value of that record in variable "quality"
 *
 * @author Siree
 */
public class ReadCsv {
    long start = System.currentTimeMillis();

    public ArrayList<Integer> overallQuality = new ArrayList<>();
    int counter = 0;

    public ArrayList<Integer> readFile() throws FileNotFoundException, IOException {
        
         

        String[] fileNames = {"src\\main\\resources\\winequality-red-part-1.csv",
            "src\\main\\resources\\winequality-red-part-2.csv",
            "src\\main\\resources\\winequality-white-part-1.csv",
            "src\\main\\resources\\winequality-white-part-2.csv"};
        try {
            for (int i = 0; i < fileNames.length; i++) {
                Reader in = new FileReader(fileNames[i]);
                System.out.println(fileNames[i]);

                Iterable<CSVRecord> records = CSVFormat.EXCEL.withDelimiter(';').withFirstRecordAsHeader().parse(in);
                for (CSVRecord record : records) {
                    
                    int quality = Integer.parseInt(record.get("quality"));
                    counter = counter + 1;
                    overallQuality.add(quality);
                }
            }
            System.out.println("Number of quality record: " + counter);
            System.out.println("ReadFile, kosten in tijd: " +  (System.currentTimeMillis() - start));
            return overallQuality;
        } catch (Exception e) {
            System.out.print("No .csv files are read");
            throw e;
        }
    }
}
