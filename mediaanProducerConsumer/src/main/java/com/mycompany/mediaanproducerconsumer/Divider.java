/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mediaanproducerconsumer;

import java.util.ArrayList;
import java.util.Objects;

/**
 *
 * @author Siree
 */
public class Divider extends Thread {

    final DataHandler dataHandler;
    private final int id;
    //private int jobsProcessed;
    private final int pivotValue;

     

    Divider(DataHandler dataHandler, int id, int pivotValue) {
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
               
                for (int i = work.get(0); i < (work.get(1)+1); i++) {
                    //jobsProcessed++;
                    
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
        //System.out.println("Divider " + id + " processed " + jobsProcessed);
    }
}
