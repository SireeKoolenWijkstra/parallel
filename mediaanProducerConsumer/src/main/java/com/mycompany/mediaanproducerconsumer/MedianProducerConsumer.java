/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mediaanproducerconsumer;

import com.mycompany.mediaanproducerconsumer.Objects.WineQuality;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Siree
 */
public class MedianProducerConsumer {

    public ArrayList<Integer> smallerThanPivot = new ArrayList<>();
    public ArrayList<Integer> equalsToPivot = new ArrayList<>();
    public ArrayList<Integer> biggerThanPivot = new ArrayList<>();

    public void SingleProducerSingleConsumer(ArrayList<Integer> wineList, int queueCapacity) throws InterruptedException {

        ExecutorService executorService = Executors.newCachedThreadPool();

        BlockingQueue<WineQuality> queue = new ArrayBlockingQueue<>(queueCapacity);

        Producer producer = new Producer(wineList, queue);

        Consumer consumer = new Consumer(queue, 1);
        
        executorService.submit(producer);
        executorService.submit(consumer);

        
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.DAYS);

    }

    /**
     * @param args the command line arguments
     * @throws java.lang.InterruptedException
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws InterruptedException, IOException {

        MedianProducerConsumer ps = new MedianProducerConsumer();
        ReadCsvProducerConsumer csv = new ReadCsvProducerConsumer();

        ArrayList<Integer> wineList = csv.readFile();
        long start = System.currentTimeMillis();
        ps.SingleProducerSingleConsumer(wineList, 5);
        System.out.println("Median is 6");
        System.out.println("Median found in time: " + (System.currentTimeMillis() - start) + " ms");
        System.out.println("Available processors: " 
                + Runtime.getRuntime().availableProcessors());

    }

}
