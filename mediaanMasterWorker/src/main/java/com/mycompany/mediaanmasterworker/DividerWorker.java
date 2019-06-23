/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mediaanmasterworker;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

/**
 *
 * @author Siree
 */
public class DividerWorker extends Thread {

    final DataHandler dataHandler;
    private final int id;
    //private int jobsProcessed;
    private final int pivotValue;

    DividerWorker(DataHandler dataHandler, int id, int pivotValue) {
        this.dataHandler = dataHandler;
        this.id = id;
        this.pivotValue = pivotValue;
        // this.jobsProcessed = 0;
    }

    @Override
    public void run() {
        for (;;) {
            try {
                ArrayList<Integer> work = dataHandler.takeWork();
                if (work == null) {
                    break;
                }

                for (int i = work.get(0); i < (work.get(1) + 1); i++) {
                    //jobsProcessed++;

                    int countRandom = 0;
                    // Code so that finding the median will take time
                    Random random = new Random();
                    int k = ((random.nextInt(5)) + 1) * 1000 / 4;

                    //Act
                    for (int m = 0; m < k; m++) {
                        countRandom = (countRandom + 1) & 7;

                    }
                    if (countRandom > 10) {
                        System.out.println("Nonsens om de busy-loop ook echt iets te laten doen");
                    }

                    int value = dataHandler.dataSet.get(i);
                    if (value < pivotValue) {
                        synchronized (dataHandler.smallerThanPivot) {
                            dataHandler.smallerThanPivot.add(value);
                        }
                    } else if (Objects.equals(value, pivotValue)) {
                        synchronized (dataHandler.equalsToPivot) {
                            dataHandler.equalsToPivot.add(value);
                        }
                    } else {
                        synchronized (dataHandler.biggerThanPivot) {
                            dataHandler.biggerThanPivot.add(value);
                        }
                    }

                }
            } catch (Throwable e) {
                e.printStackTrace();
                throw e;
            }
        }
        //System.out.println("DividerWorker " + id + " processed " + jobsProcessed);
    }
}
