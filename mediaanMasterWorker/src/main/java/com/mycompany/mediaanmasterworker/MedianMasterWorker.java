/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mediaanmasterworker;

import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Siree
 */
public class MedianMasterWorker {

    private static final long MEGABYTE = 1024L * 1024L;

    private static long bytesToMegabytes(long bytes) {
        return bytes / MEGABYTE;
    }

    /**
     * @param args the command line arguments
     * @throws java.lang.InterruptedException
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws InterruptedException, IOException {
        long totalRuntime = System.currentTimeMillis();

        FindMedianMaster findMedianM = new FindMedianMaster();
        ReadCsvMasterWorker csv = new ReadCsvMasterWorker();
        
        System.out.println("Working Directory = " + System.getProperty("user.dir"));

        ArrayList<Integer> dataSet = csv.readFile();
        long start = System.currentTimeMillis();
        
        String format = "%-30s%s%n";
        System.out.println("--------------------------------");
        System.out.printf(format, "Median of data set is ", findMedianM.findMedianMasterWorker(dataSet));
        System.out.printf(format, "Median found in time: ", (System.currentTimeMillis() - start) + " ms");
        System.out.println("--------------------------------");
        System.out.printf(format, "Total Runtime: ", (System.currentTimeMillis() - totalRuntime) + " ms");
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
