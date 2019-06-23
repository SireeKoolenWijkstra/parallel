/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mediaanproducerconsumer;

import java.util.ArrayList;
import java.util.Random;
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

    public void SingleProducerSingleConsumer(DataHandler dataHandler,
            int pivotValue) throws InterruptedException {

        final int MAX_WORKERS = 8;

        ExecutorService executorService = Executors.newFixedThreadPool(MAX_WORKERS);

        for (int i = 0; i < MAX_WORKERS; i++) {
            int id = i + 1;
            Divider divider = new Divider(dataHandler, id, pivotValue);

            executorService.submit(divider);
        }

        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.DAYS);

    }

    public int compareArraysToTargetIndex(ArrayList<Integer> list, int targetIndex) {

        int pivotValue = findPivot(list);
        DataHandler dataHandler = new DataHandler(list);

        try {
            SingleProducerSingleConsumer(dataHandler, pivotValue);
        } catch (InterruptedException ex) {
            Logger.getLogger(FindMedianProducerConsumer.class.getName()).log(Level.SEVERE, null, ex);
            throw new Error(ex);
        }

        int countRandom = 0;
        // Code so that finding the median will take time
        Random random = new Random();
        int k = ((random.nextInt(80)) + 1) * 15000000;

        //Act
        for (int i = 0; i < k; i++) {
            countRandom++;
        }

        if (dataHandler.smallerThanPivot.size() > targetIndex) {
            return compareArraysToTargetIndex(dataHandler.smallerThanPivot, targetIndex);
        } else if ((dataHandler.smallerThanPivot.size() + dataHandler.equalsToPivot.size()) > targetIndex) {
            return pivotValue;
        } else {
            return compareArraysToTargetIndex(dataHandler.biggerThanPivot, targetIndex
                    - dataHandler.smallerThanPivot.size() - dataHandler.equalsToPivot.size());
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
