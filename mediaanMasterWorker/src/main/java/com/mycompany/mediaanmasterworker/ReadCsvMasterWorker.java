/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mediaanmasterworker;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

/**
 * Reads the 4 Excel files and makes a Array of strings. It walks over the array
 * and for each record in the array it looks for the header "quality" and puts
 * the value of that record in variable "quality"
 *
 * @author Siree
 */
public class ReadCsvMasterWorker extends Thread {

    ArrayList<Integer> numberOfRecords = new ArrayList<>();
    private String format = "%-30s%s%n";

    public ArrayList<Integer> readFile() throws FileNotFoundException, IOException, InterruptedException {
        final int MAX_THREADS = 8;

        ArrayList<String> dataSet = new ArrayList<>();

        //dataset 1
        dataSet.add("..\\resources\\dataSet1\\winequality-red-part-1.csv");
        dataSet.add("..\\resources\\dataSet1\\winequality-red-part-2.csv");
        dataSet.add("..\\resources\\dataSet1\\winequality-white-part-1.csv");
        dataSet.add("..\\resources\\dataSet1\\winequality-white-part-2.csv");

        //dataset 2n
//            dataSet.add("..\\resources\\dataSet2\\winequality-red-part-1.csv");
//            dataSet.add("..\\resources\\dataSet2\\winequality-red-part-1.1.csv");
//            dataSet.add("..\\resources\\dataSet2\\winequality-red-part-2.csv");
//            dataSet.add("..\\resources\\dataSet2\\winequality-red-part-2.1.csv");
//            dataSet.add("..\\resources\\dataSet2\\winequality-white-part-1.csv");
//            dataSet.add("..\\resources\\dataSet2\\winequality-white-part-1.1.csv");
//            dataSet.add("..\\resources\\dataSet2\\winequality-white-part-2.csv");
//            dataSet.add("..\\resources\\dataSet2\\winequality-white-part-2.1.csv");
            
        //dataset 4n
//            dataSet.add("..\\resources\\dataSet3\\winequality-red-part-1.csv");
//            dataSet.add("..\\resources\\dataSet3\\winequality-red-part-1.1.csv");
//            dataSet.add("..\\resources\\dataSet3\\winequality-red-part-1.2.csv");
//            dataSet.add("..\\resources\\dataSet3\\winequality-red-part-1.3.csv");
//            dataSet.add("..\\resources\\dataSet3\\winequality-red-part-2.csv");
//            dataSet.add("..\\resources\\dataSet3\\winequality-red-part-2.1.csv");
//            dataSet.add("..\\resources\\dataSet3\\winequality-red-part-2.2.csv");
//            dataSet.add("..\\resources\\dataSet3\\winequality-red-part-2.3.csv");
//            dataSet.add("..\\resources\\dataSet3\\winequality-white-part-1.csv");
//            dataSet.add("..\\resources\\dataSet3\\winequality-white-part-1.1.csv");
//            dataSet.add("..\\resources\\dataSet3\\winequality-white-part-1.2.csv");
//            dataSet.add("..\\resources\\dataSet3\\winequality-white-part-1.3.csv");
//            dataSet.add("..\\resources\\dataSet3\\winequality-white-part-2.csv");
//            dataSet.add("..\\resources\\dataSet3\\winequality-white-part-2.1.csv");
//            dataSet.add("..\\resources\\dataSet3\\winequality-white-part-2.2.csv");
//            dataSet.add("..\\resources\\dataSet3\\winequality-white-part-2.3.csv");
            
            
        ExecutorService executorService = Executors.newFixedThreadPool(MAX_THREADS);
        long start = System.currentTimeMillis();
        for (int i = 0; i < dataSet.size(); i++) {
            final int j = i;
            executorService.submit(() -> {
                try {
                    parseFile(dataSet.get(j));

                } catch (IOException | NumberFormatException e) {
                    System.out.println("No .csv files are read");
                    throw new Error(e);
                }
            });
        }

        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.DAYS);

        System.out.printf(format, "RecordNr of data set: ", numberOfRecords.size());
        System.out.printf(format, "ReadFile, time: ", (System.currentTimeMillis() - start) + " ms");

        return numberOfRecords;
    }

    public void parseFile(String fileName) throws IOException {

        BufferedReader inDataSet = new BufferedReader(new FileReader(fileName));

        Iterable<CSVRecord> records = CSVFormat.EXCEL.
                withDelimiter(';').
                withFirstRecordAsHeader().
                parse(inDataSet);
        for (CSVRecord record : records) {
            synchronized (numberOfRecords) {
                numberOfRecords.add(Integer.parseInt(record.get("quality")));
            }
        }
    }
}
