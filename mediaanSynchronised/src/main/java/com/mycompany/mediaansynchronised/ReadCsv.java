/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mediaansynchronised;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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

    ArrayList<Integer> numberOfRecords;
    public ArrayList<ArrayList<Integer>> allNumberOfRecordsForAllDataSets = new ArrayList<>();

    public ArrayList<ArrayList<Integer>>  readFile() throws FileNotFoundException, IOException, InterruptedException {

        long startTotal = System.currentTimeMillis();

        ArrayList<String[]> dataSet = new ArrayList<>();
        Thread[] threadList = null;

        dataSet.add(new String[]{"..\\resources\\dataSet1\\winequality-red-part-1.csv",
            "..\\resources\\dataSet1\\winequality-red-part-2.csv",
            "..\\resources\\dataSet1\\winequality-white-part-1.csv",
            "..\\resources\\dataSet1\\winequality-white-part-2.csv"});

        dataSet.add(new String[]{"..\\resources\\dataSet2\\winequality-red-part-1.csv",
            "..\\resources\\dataSet2\\winequality-red-part-1.1.csv",
            "..\\resources\\dataSet2\\winequality-red-part-2.csv",
            "..\\resources\\dataSet2\\winequality-red-part-2.1.csv",
            "..\\resources\\dataSet2\\winequality-white-part-1.csv",
            "..\\resources\\dataSet2\\winequality-white-part-1.1.csv",
            "..\\resources\\dataSet2\\winequality-white-part-2.csv",
            "..\\resources\\dataSet2\\winequality-white-part-2.1.csv"});

        dataSet.add(new String[]{"..\\resources\\dataSet3\\winequality-red-part-1.csv",
            "..\\resources\\dataSet3\\winequality-red-part-1.1.csv",
            "..\\resources\\dataSet3\\winequality-red-part-1.2.csv",
            "..\\resources\\dataSet3\\winequality-red-part-1.3.csv",
            "..\\resources\\dataSet3\\winequality-red-part-2.csv",
            "..\\resources\\dataSet3\\winequality-red-part-2.1.csv",
            "..\\resources\\dataSet3\\winequality-red-part-2.2.csv",
            "..\\resources\\dataSet3\\winequality-red-part-2.3.csv",
            "..\\resources\\dataSet3\\winequality-white-part-1.csv",
            "..\\resources\\dataSet3\\winequality-white-part-1.1.csv",
            "..\\resources\\dataSet3\\winequality-white-part-1.2.csv",
            "..\\resources\\dataSet3\\winequality-white-part-1.3.csv",
            "..\\resources\\dataSet3\\winequality-white-part-2.csv",
            "..\\resources\\dataSet3\\winequality-white-part-2.1.csv",
            "..\\resources\\dataSet3\\winequality-white-part-2.2.csv",
            "..\\resources\\dataSet3\\winequality-white-part-2.3.csv"});

       for (int i = 0; i < dataSet.size(); i++) {
            long start = System.currentTimeMillis();
            numberOfRecords = new ArrayList<>();

            String[] fileNames = dataSet.get(i);
            threadList = new Thread[fileNames.length];
            for (int k = 0; k < fileNames.length; k++) {
                final int l = k;
                Thread t = new Thread(() -> {
                    try {
                        parseFile(fileNames[l]);

                    } catch (IOException | NumberFormatException e) {
                        System.out.println("No .csv files are read");
                        throw new Error(e);
                    }
                });

                System.out.println("starting thread " + t);
                t.start();
                threadList[k] = t;
            }

            for (Thread threadList1 : threadList) {
                threadList1.join();
            }
            allNumberOfRecordsForAllDataSets.add(numberOfRecords);
            System.out.println("ReadFile, time: "
                    + (System.currentTimeMillis() - start) + " ms");
            System.out.println("Number of threads started: " + threadList.length);
            System.out.println("Recordnr of dataSet " + (i + 1) + ": "
                    + numberOfRecords.size());
        }

        System.out.println("ReadFile for all dataSets, totall time: "
                + (System.currentTimeMillis() - startTotal) + " ms");

        return allNumberOfRecordsForAllDataSets;
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
