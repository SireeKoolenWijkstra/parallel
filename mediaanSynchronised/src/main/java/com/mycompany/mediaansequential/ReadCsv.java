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
public class ReadCsv extends Thread {

    long start = System.currentTimeMillis();

    public ArrayList<Integer> overallQuality = new ArrayList<>();

    public ArrayList<Integer> readFile() throws FileNotFoundException, IOException, InterruptedException {

        String[] fileNames = {"src\\main\\resources\\winequality-red-part-1.csv",
            "src\\main\\resources\\winequality-red-part-2.csv",
            "src\\main\\resources\\winequality-white-part-1.csv",
            "src\\main\\resources\\winequality-white-part-2.csv"};

        Thread[] threadList = new Thread[fileNames.length];

        for (int i = 0; i < fileNames.length; i++) {
            final int j = i;
            Thread name = new Thread(() -> {
                try {
                    Reader in = new FileReader(fileNames[j]);
                    System.out.println(fileNames[j]);

                    Iterable<CSVRecord> records = CSVFormat.EXCEL.withDelimiter(';').withFirstRecordAsHeader().parse(in);
                    int counter = 0;
                    for (CSVRecord record : records) {

                        int quality = Integer.parseInt(record.get("quality"));
                        counter = counter + 1;
                        synchronized (overallQuality) {
                            overallQuality.add(quality);
                        }
                    }
                    System.out.println("number of quality record: " + counter + " in thread " + j);
                } catch (Exception e) {
                    System.out.println("No .csv files are read");
                    throw new Error(e);
                }
            });
            name.start();
            threadList[j] = name;
        }
        for (int i = 0; i < threadList.length; i++) {
            threadList[i].join();
        }
        System.out.println("Length of overallQuality is " + overallQuality.size());
        System.out.println("ReadFile, kosten in tijd: " + (System.currentTimeMillis() - start));
        return overallQuality;
    }
}
