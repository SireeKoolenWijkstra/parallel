/*
 * To change this license header, choose License Headers inDataSet1 Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template inDataSet1 the editor.
 */
package com.mycompany.mediaansequential;

import java.io.BufferedReader;
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
 * @author Siree & Tamara
 */
class ReadCsv {

    private long start = System.currentTimeMillis();

    private ArrayList<Integer> numberOfRecords;
    private ArrayList<ArrayList<Integer>> allNumberOfRecordsForAllDataSets = new ArrayList<>();

    ArrayList<ArrayList<Integer>> readFile() {

        ArrayList<String[]> dataSet = new ArrayList<>();

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
        try {
            for (int i = 0; i < dataSet.size(); i++) {
                long startDataSet = System.currentTimeMillis();
                numberOfRecords = new ArrayList<>();
                try {
                    parseFile(dataSet.get(i));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("RecordNr of data set " + (i + 1) + ": " + numberOfRecords.size());
                System.out.println("ReadFile, time: " + (System.currentTimeMillis() - startDataSet) + " ms");

                allNumberOfRecordsForAllDataSets.add(numberOfRecords);
            }
            System.out.println("ReadFile for all dataSets, total time: " + (System.currentTimeMillis() - start) + " ms");

            return allNumberOfRecordsForAllDataSets;
        } catch (NumberFormatException e) {
            System.out.print("No .csv files are read");
            return null;
        }
    }

    private void parseFile(String[] dataSet) throws IOException {
        for (String fileName : dataSet) {
            BufferedReader inDataSet = new BufferedReader(new FileReader(fileName));

            Iterable<CSVRecord> records = CSVFormat.EXCEL
                    .withDelimiter(';')
                    .withFirstRecordAsHeader()
                    .parse(inDataSet);
            for (CSVRecord record : records) {
                numberOfRecords.add(Integer.parseInt(record.get("quality")));

            }
        }
    }
}
