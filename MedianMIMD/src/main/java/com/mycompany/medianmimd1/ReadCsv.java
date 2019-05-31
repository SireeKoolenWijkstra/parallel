/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.medianmimd1;

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

    public ArrayList<Integer> readFile() throws FileNotFoundException, IOException {
        
        try {
        String fileName
                = "..\\resources\\winequality-red-part-1.csv";
        
                Reader in = new FileReader(fileName);
                Iterable<CSVRecord> records = CSVFormat.EXCEL
                        .withDelimiter(';')
                        .withFirstRecordAsHeader()
                        .parse(in);
                for (CSVRecord record : records) {
                    overallQuality.add(Integer.parseInt(record.get("quality")));
                }
            System.out.println("Length of overallQuality is "
                    + overallQuality.size());
            System.out.println("ReadFile, time: "
                    + (System.currentTimeMillis() - start) + " ms");
            return overallQuality;
        } catch (IOException | NumberFormatException e) {
            System.out.print("No .csv files are read");
            throw e;
        }
    }
}
