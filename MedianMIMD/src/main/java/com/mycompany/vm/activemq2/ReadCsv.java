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
    long start = System.currentTimeMillis();

    private static final String FILE_PATH = "..\\resources\\winequality-red-part-2.csv";
    public ArrayList<Integer> overallQuality = new ArrayList<>();

    //Delimiters used in the CSV file
    private static final String COMMA_DELIMITER = ",";

    public ArrayList<Integer> readFile() throws IOException {

        BufferedReader br = null;

        try {
//            br = new BufferedReader(new FileReader(FILE_PATH));
//            String line = "";
//            br.readLine();
//            while ((line = br.readLine()) != null) {
//                String[] quality = line.split(COMMA_DELIMITER);
//                overallQuality.add(Integer.parseInt());
//            }


            Reader in = new FileReader(FILE_PATH);
            Iterable<CSVRecord> records = CSVFormat.EXCEL
                    .withDelimiter(';')
                    .withFirstRecordAsHeader()
                    .parse(in);
            for (CSVRecord record : records) {
                overallQuality.add(Integer.parseInt(record.get("quality")));
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException | NumberFormatException e) {
            System.out.print("No .csv files are read");
            throw e;
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        String format = "%-25s%s%n";
//        System.out.printf(format, "Length of list VM2: ", overallQuality.size());
        System.out.printf(format, "ReadFile time VM_2: ", (System.currentTimeMillis() - start) + " ms");
        return overallQuality;
    }
}
