/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.vm.activemq2;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.util.ArrayList;

/**
 * Reads the 4 Excel files and makes a Array of strings. It walks over the array
 * and for each record in the array it looks for the header "quality" and puts
 * the value of that record in variable "quality"
 *
 * @author Tamara
 */
public class ReadCsv {
    long startTotal = System.currentTimeMillis();

    public ArrayList<Integer> overallQuality = new ArrayList<>();

    // reads the csv file, you can comment out the
    ArrayList<Integer> readFile() {

        ArrayList<String> dataSet = new ArrayList<>();


        // dataset 1n
        dataSet.add("..\\resources\\winequality-red-part-2.csv");

        // dataset 2n
//        dataSet.add("..\\resources\\dataSet2\\winequality-red-part-1.csv");
//        dataSet.add("..\\resources\\dataSet2\\winequality-red-part-1.1.csv");

        // dataset 4n
//        dataSet.add("..\\resources\\dataSet2\\winequality-red-part-1.csv");
//        dataSet.add("..\\resources\\dataSet2\\winequality-red-part-1.1.csv");
//        dataSet.add("..\\resources\\dataSet3\\winequality-red-part-1.2.csv");
//        dataSet.add("..\\resources\\dataSet3\\winequality-red-part-1.3.csv");

        Thread[] threadList = new Thread[dataSet.size()];
        BufferedReader br = null;
        for (int i = 0; i < dataSet.size(); i++) {
            final int j = i;
            Thread t = new Thread(() -> {
                try {

                    BufferedReader in = new BufferedReader(new FileReader(dataSet.get(j)));
                    Iterable<CSVRecord> records = CSVFormat.EXCEL
                            .withDelimiter(';')
                            .withFirstRecordAsHeader()
                            .parse(in);
                    for (CSVRecord record : records) {
                        synchronized (overallQuality){
                            overallQuality.add(Integer.parseInt(record.get("quality")));
                        }
                    }

                } catch (IOException | NumberFormatException e) {
                    System.out.println("No .csv files are read");
                    throw new Error(e);
                }
            });
//            System.out.println("starting thread " + t);
            t.start();
            threadList[i] = t;
        }

        for (Thread threadList1 : threadList) {
            try {
                threadList1.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        String format = "%-25s%s%n";
        System.out.println("-------------VM2------------------");
        System.out.printf(format, "Length of list VM2: ", overallQuality.size());
        System.out.printf(format, "ReadFile time VM_2: ", (System.currentTimeMillis() - startTotal) + " ms");
        System.out.printf(format, "Number of threads started: ", threadList.length);
        //System.out.printf(format, "Data size is: ", dataSet.size() + "n");
        System.out.println("-------------END VM2------------------");
        return overallQuality;
    }
}
