/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mediaansequential;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.lang.management.OperatingSystemMXBean;

/**
 *
 * @author Siree
 */
public class MediaanSequential {

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

    }
}
