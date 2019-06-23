/*
 * To change this license header, choose License Headers inDataSet1 Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template inDataSet1 the editor.
 */
package com.mycompany.mediansequential;

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

    private ArrayList<Integer> numberOfRecords;
    private String format = "%-30s%s%n";

    ArrayList<Integer> readFile() {

        ArrayList<String> dataSet = new ArrayList<>();

        // dataset 1n
        dataSet.add("..\\resources\\dataSet1\\winequality-red-part-1.csv");
        dataSet.add("..\\resources\\dataSet1\\winequality-red-part-2.csv");
        dataSet.add("..\\resources\\dataSet1\\winequality-white-part-1.csv");
        dataSet.add("..\\resources\\dataSet1\\winequality-white-part-2.csv");

        // dataset 2n
//        dataSet.add("..\\resources\\dataSet2\\winequality-red-part-1.csv");
//        dataSet.add("..\\resources\\dataSet2\\winequality-red-part-1.1.csv");
//        dataSet.add("..\\resources\\dataSet2\\winequality-red-part-2.csv");
//        dataSet.add("..\\resources\\dataSet2\\winequality-red-part-2.1.csv");
//        dataSet.add("..\\resources\\dataSet2\\winequality-white-part-1.csv");
//        dataSet.add("..\\resources\\dataSet2\\winequality-white-part-1.1.csv");
//        dataSet.add("..\\resources\\dataSet2\\winequality-white-part-2.csv");
//        dataSet.add("..\\resources\\dataSet2\\winequality-white-part-2.1.csv");

        // dataset 4n
//        dataSet.add("..\\resources\\dataSet3\\winequality-red-part-1.csv");
//        dataSet.add("..\\resources\\dataSet3\\winequality-red-part-1.1.csv");
//        dataSet.add("..\\resources\\dataSet3\\winequality-red-part-1.2.csv");
//        dataSet.add("..\\resources\\dataSet3\\winequality-red-part-1.3.csv");
//        dataSet.add("..\\resources\\dataSet3\\winequality-red-part-2.csv");
//        dataSet.add("..\\resources\\dataSet3\\winequality-red-part-2.1.csv");
//        dataSet.add("..\\resources\\dataSet3\\winequality-red-part-2.2.csv");
//        dataSet.add("..\\resources\\dataSet3\\winequality-red-part-2.3.csv");
//        dataSet.add("..\\resources\\dataSet3\\winequality-white-part-1.csv");
//        dataSet.add("..\\resources\\dataSet3\\winequality-white-part-1.1.csv");
//        dataSet.add("..\\resources\\dataSet3\\winequality-white-part-1.2.csv");
//        dataSet.add("..\\resources\\dataSet3\\winequality-white-part-1.3.csv");
//        dataSet.add("..\\resources\\dataSet3\\winequality-white-part-2.csv");
//        dataSet.add("..\\resources\\dataSet3\\winequality-white-part-2.1.csv");
//        dataSet.add("..\\resources\\dataSet3\\winequality-white-part-2.2.csv");
//        dataSet.add("..\\resources\\dataSet3\\winequality-white-part-2.3.csv");

        try {

            long startDataSet = System.currentTimeMillis();
            numberOfRecords = new ArrayList<>();
            try {
                parseFile(dataSet);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.printf(format, "RecordNr of data set: ", numberOfRecords.size());
            System.out.printf(format, "ReadFile, time: ", (System.currentTimeMillis() - startDataSet) + " ms");

            return numberOfRecords;
        } catch (NumberFormatException e) {
            System.out.print("No .csv files are read");
            return null;
        }
    }

    private void parseFile(ArrayList<String> dataSet) throws IOException {
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
