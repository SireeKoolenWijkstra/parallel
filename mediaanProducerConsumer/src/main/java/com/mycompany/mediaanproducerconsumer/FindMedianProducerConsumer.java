/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mediaanproducerconsumer;

import com.mycompany.mediaanproducerconsumer.Objects.WineQuality;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Siree
 */
public class FindMedianProducerConsumer {

    public int findMedianProducerConsumer(ArrayList<Integer> list) {
        int targetIndex = divideArrayList(list);
        return compareArraysToTargetIndex(list, targetIndex);

    }

    public void SingleProducerSingleConsumer(ArrayList<Integer> wineList, int queueCapacity
            ,int pivotValue, ArrayList<Integer> smallerThanPivot, ArrayList<Integer> equalsToPivot
            ,ArrayList<Integer> biggerThanPivot) throws InterruptedException {

        ExecutorService executorService = Executors.newCachedThreadPool();
        BlockingQueue<WineQuality> queue = new ArrayBlockingQueue<>(queueCapacity);

        Producer producer = new Producer(wineList, queue);
        Consumer consumer = new Consumer(queue, 1, pivotValue, smallerThanPivot
                , equalsToPivot, biggerThanPivot);

        executorService.submit(producer);
        executorService.submit(consumer);

        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.DAYS);

    }

    public int compareArraysToTargetIndex(ArrayList<Integer> list, int targetIndex) {

        ArrayList<Integer> smallerThanPivot = new ArrayList<>();
        ArrayList<Integer> equalsToPivot = new ArrayList<>();
        ArrayList<Integer> biggerThanPivot = new ArrayList<>();

        int pivotValue = findPivot(list);

        try {
            SingleProducerSingleConsumer(list, 5, pivotValue, smallerThanPivot, equalsToPivot, biggerThanPivot);
        } catch (InterruptedException ex) {
            Logger.getLogger(FindMedianProducerConsumer.class.getName()).log(Level.SEVERE, null, ex);
            throw new Error(ex);
        }

        if (smallerThanPivot.size() > targetIndex) {
            return compareArraysToTargetIndex(smallerThanPivot, targetIndex);
        } else if ((smallerThanPivot.size()+ equalsToPivot.size()) > targetIndex) {
            return pivotValue;
        } else {
            return compareArraysToTargetIndex(biggerThanPivot, targetIndex - smallerThanPivot.size() - equalsToPivot.size());
        }
    }

//returns targetIndex
    public int divideArrayList(ArrayList<Integer> list) {
        if (list.size() % 2 == 1) {
            return (list.size() - 1) / 2;
        } else {
            return list.size() / 2;
        }
    }

    //returns value on index (diffrent from MedianSynchronised, where it returns 
    // the index)
    public int findPivot(ArrayList<Integer> list) {
        return list.get(ThreadLocalRandom.current().nextInt(0, list.size()));
    }
}
