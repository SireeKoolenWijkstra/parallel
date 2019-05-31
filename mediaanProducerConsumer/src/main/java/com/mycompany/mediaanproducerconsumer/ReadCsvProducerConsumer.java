/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mediaanproducerconsumer;

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
public class ReadCsvProducerConsumer extends Thread {

    long start = System.currentTimeMillis();

    public final ArrayList<Integer> OVERALL_QUALITY = new ArrayList<>();

    public ArrayList<Integer> readFile() throws FileNotFoundException, IOException, InterruptedException {

        String[] fileNames = {"..\\resources\\winequality-red-part-1.csv",
            "..\\resources\\winequality-red-part-2.csv",
            "..\\resources\\winequality-white-part-1.csv",
            "..\\resources\\winequality-white-part-2.csv"};

        Thread[] threadList = new Thread[fileNames.length];

        for (int i = 0; i < fileNames.length; i++) {
            final int j = i;
            Thread t = new Thread(() -> {
                try {
                    Reader in = new FileReader(fileNames[j]);

                    Iterable<CSVRecord> records = CSVFormat.EXCEL.withDelimiter(';').withFirstRecordAsHeader().parse(in);
                    int counter = 0;
                            System.out.println("starting recordcount " + j);
                    for (CSVRecord record : records) {

                        counter = counter + 1;
                        synchronized (OVERALL_QUALITY) {
                            OVERALL_QUALITY.add(Integer.parseInt(record.get("quality")));
                        }
                    }
                    System.out.println("number of quality record: " + counter + " in thread " + j);
                } catch (IOException | NumberFormatException e) {
                    System.out.println("No .csv files are read");
                    throw new Error(e);
                }
            });
            System.out.println("starting thread " + t);
            t.start();
            threadList[j] = t;
        }
        for (int i = 0; i < threadList.length; i++) {
            threadList[i].join();
        }
        System.out.println("Length of overallQuality is " + OVERALL_QUALITY.size());
        System.out.println("ReadFile, time: " 
                + (System.currentTimeMillis() - start) + " ms");
        
        return OVERALL_QUALITY;
    }
}
