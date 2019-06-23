package com.mycompany.mediaansynchronised;

import java.io.BufferedReader;
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
 * @author Siree & Tamara
 */
class ReadCsv extends Thread {

    private static final ArrayList<Integer> numberOfRecords = new ArrayList<>();
    private String format = "%-30s%s%n";

    ArrayList<Integer> readFile() {

        ArrayList<String> fileNames = new ArrayList<>();

        // dataSet 1n
        fileNames.add("..\\resources\\dataSet1\\winequality-red-part-1.csv");
        fileNames.add("..\\resources\\dataSet1\\winequality-red-part-2.csv");
        fileNames.add("..\\resources\\dataSet1\\winequality-white-part-1.csv");
        fileNames.add("..\\resources\\dataSet1\\winequality-white-part-2.csv");

        // dataSet 2n
//        fileNames.add("..\\resources\\dataSet2\\winequality-red-part-1.csv");
//        fileNames.add("..\\resources\\dataSet2\\winequality-red-part-1.1.csv");
//        fileNames.add("..\\resources\\dataSet2\\winequality-red-part-2.csv");
//        fileNames.add("..\\resources\\dataSet2\\winequality-red-part-2.1.csv");
//        fileNames.add("..\\resources\\dataSet2\\winequality-white-part-1.csv");
//        fileNames.add("..\\resources\\dataSet2\\winequality-white-part-1.1.csv");
//        fileNames.add("..\\resources\\dataSet2\\winequality-white-part-2.csv");
//        fileNames.add("..\\resources\\dataSet2\\winequality-white-part-2.1.csv");

        // dataSet 4n
//        fileNames.add("..\\resources\\dataSet3\\winequality-red-part-1.csv");
//        fileNames.add("..\\resources\\dataSet3\\winequality-red-part-1.1.csv");
//        fileNames.add("..\\resources\\dataSet3\\winequality-red-part-1.2.csv");
//        fileNames.add("..\\resources\\dataSet3\\winequality-red-part-1.3.csv");
//        fileNames.add("..\\resources\\dataSet3\\winequality-red-part-2.csv");
//        fileNames.add("..\\resources\\dataSet3\\winequality-red-part-2.1.csv");
//        fileNames.add("..\\resources\\dataSet3\\winequality-red-part-2.2.csv");
//        fileNames.add("..\\resources\\dataSet3\\winequality-red-part-2.3.csv");
//        fileNames.add("..\\resources\\dataSet3\\winequality-white-part-1.csv");
//        fileNames.add("..\\resources\\dataSet3\\winequality-white-part-1.1.csv");
//        fileNames.add("..\\resources\\dataSet3\\winequality-white-part-1.2.csv");
//        fileNames.add("..\\resources\\dataSet3\\winequality-white-part-1.3.csv");
//        fileNames.add("..\\resources\\dataSet3\\winequality-white-part-2.csv");
//        fileNames.add("..\\resources\\dataSet3\\winequality-white-part-2.1.csv");
//        fileNames.add("..\\resources\\dataSet3\\winequality-white-part-2.2.csv");
//        fileNames.add("..\\resources\\dataSet3\\winequality-white-part-2.3.csv");


        long startDataSet = System.currentTimeMillis();
        final int MAX_THREADS = 8;
        ExecutorService executorService = Executors.newFixedThreadPool(MAX_THREADS);

        for (int i = 0; i < fileNames.size(); i++) {
            final int j = i;
            executorService.submit(() -> {
                try {
                    parseFile(fileNames.get(j));

                } catch (IOException e) {
                    System.out.printf(format, "No .csv files are read");
                    throw new Error(e);
                }
            });
        }

        executorService.shutdown();
        try {
            executorService.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        System.out.printf(format, "RecordNr of data set: ", numberOfRecords.size());
        System.out.printf(format, "ReadFile, time: ", (System.currentTimeMillis() - startDataSet) + " ms");

        return numberOfRecords;
    }

    private void parseFile(String fileName) throws IOException {

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
