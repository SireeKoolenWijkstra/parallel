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

    /**
     * @param args the command line arguments
     * @throws java.lang.InterruptedException
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws InterruptedException, IOException {

        FindMedianProducerConsumer findMedianPC = new FindMedianProducerConsumer();
        ReadCsvProducerConsumer csv = new ReadCsvProducerConsumer();

        ArrayList<Integer> wineList =  csv.readFile();
        long start = System.currentTimeMillis();
        System.out.println("Median is " + findMedianPC.findMedianProducerConsumer(wineList));
        System.out.println("Median found in time: " + (System.currentTimeMillis() - start) + " ms");
        System.out.println("Available processors: " 
                + Runtime.getRuntime().availableProcessors());

    }

}
