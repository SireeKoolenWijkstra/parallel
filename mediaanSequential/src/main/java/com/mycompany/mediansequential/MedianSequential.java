/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mediansequential;

import java.util.ArrayList;

/**
 *
 * @author Siree & Tamara
 */
public class MedianSequential {

    private static final long MEGABYTE = 1024L * 1024L;

    private static long bytesToMegabytes(long bytes) {
        return bytes / MEGABYTE;
    }

    private static String format = "%-30s%s%n";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        long totalRuntime = System.currentTimeMillis();

        ReadCsv readCsv = new ReadCsv();
        MedianFinder medianFinder = new MedianFinder();

        System.out.println("Working Directory = " + System.getProperty("user.dir"));

        ArrayList<Integer> dataSet = readCsv.readFile();
        long start = System.currentTimeMillis();
//        for (int i = 0; i < dataSet.size(); i++) {
            System.out.println("--------------------------------");
            System.out.printf(format, "Median of data set is ", medianFinder.findRealMedian(dataSet));
            System.out.printf(format, "Median found in time: ",  (System.currentTimeMillis() - start) + " ms");
            System.out.println("--------------------------------");
//        }


        System.out.printf(format, "Total Runtime: ",  (System.currentTimeMillis() - totalRuntime) + " ms");
        System.out.println("--------------------------------");

        System.out.printf(format, "Available processors: ", Runtime.getRuntime().availableProcessors());

        // Get the Java runtime
        Runtime runtime = Runtime.getRuntime();
        // Run the garbage collector
        runtime.gc();

        // Calculate the used memory
        long memory = runtime.totalMemory() - runtime.freeMemory();
        System.out.printf(format, "Used memory is bytes: ", memory);
        System.out.printf(format, "Used memory is megabytes: ", bytesToMegabytes(memory));

    }
}
