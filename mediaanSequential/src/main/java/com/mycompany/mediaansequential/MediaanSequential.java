/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mediaansequential;

import java.io.IOException;
import static java.lang.String.format;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.lang.management.OperatingSystemMXBean;

/**
 *
 * @author Siree
 */
public class MediaanSequential {
    
     private static final long MEGABYTE = 1024L * 1024L;
     private static String format = "%-40s%s%n";

    public static long bytesToMegabytes(long bytes) {
        return bytes / MEGABYTE;
    }

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {

        ReadCsv readCsv = new ReadCsv();

        MedianFinder medianFinder = new MedianFinder();

        System.out.println("Working Directory = "
                + System.getProperty("user.dir"));

        ArrayList<Integer> list = readCsv.readFile();
        long start = System.currentTimeMillis();
        System.out.println("Median is " + medianFinder.findrealMedian(list));
        System.out.println("Median found in time: "
                + (System.currentTimeMillis() - start) + " ms");
        System.out.println("Available processors: " 
                + Runtime.getRuntime().availableProcessors());
        
        // Get the Java runtime
                Runtime runtime = Runtime.getRuntime();
                // Run the garbage collector
                runtime.gc();
                
                
                // Calculate the used memory
                long memory = runtime.totalMemory() - runtime.freeMemory();
                System.out.printf(format,"Used memory is bytes: ", memory);
                System.out.printf(format, "Used memory is megabytes: ",
                         bytesToMegabytes(memory));

    }
}
