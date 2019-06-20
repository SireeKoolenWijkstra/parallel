/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mediaanproducerconsumer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Siree
 */
public class MedianProducerConsumer {

    private static final long MEGABYTE = 1024L * 1024L;
    private static String format = "%-40s%s%n";

    public static long bytesToMegabytes(long bytes) {
        return bytes / MEGABYTE;
    }

    /**
     * @param args the command line arguments
     * @throws java.lang.InterruptedException
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws InterruptedException, IOException {

        FindMedianProducerConsumer findMedianPC = new FindMedianProducerConsumer();
        ReadCsvProducerConsumer csv = new ReadCsvProducerConsumer();

        ArrayList<ArrayList<Integer>> dataSet = csv.readFile();
        long start = System.currentTimeMillis();
        for (int i = 0; i < dataSet.size(); i++) {
            System.out.println("Median of dataset " + (i + 1)
                    + " is " + findMedianPC.findMedianProducerConsumer(dataSet.get(i)));

            System.out.println("Median found in time: " + (System.currentTimeMillis() - start) + " ms");
            System.out.println("Available processors: "
                    + Runtime.getRuntime().availableProcessors());
        }
         // Get the Java runtime
            Runtime runtime = Runtime.getRuntime();
            // Run the garbage collector
            runtime.gc();

            // Calculate the used memory
            long memory = runtime.totalMemory() - runtime.freeMemory();
            System.out.printf(format, "Used memory is bytes: ", memory);
            System.out.printf(format, "Used memory is megabytes: ",
                    bytesToMegabytes(memory));

    }

}
