/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mediaanproducerconsumer;

import com.mycompany.mediaanproducerconsumer.Objects.WineQuality;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;

/**
 *
 * @author Siree
 */
public class Consumer extends Thread {

    private BlockingQueue<WineQuality> queue;
    private int id;
    private int jobsProcessed;
    private int pivotValue;
    ArrayList<Integer> smallerThanPivot;
    ArrayList<Integer> equalsToPivot;
    ArrayList<Integer> biggerThanPivot;
     

    Consumer(BlockingQueue<WineQuality> queue, int id, int pivotValue, ArrayList<Integer> smallerThanPivot
                ,ArrayList<Integer> equalsToPivot, ArrayList<Integer> biggerThanPivot) {
        this.queue = queue;
        this.id = id;
        this.pivotValue = pivotValue;
        this.smallerThanPivot = smallerThanPivot;
        this.equalsToPivot = equalsToPivot;
        this.biggerThanPivot = biggerThanPivot;

        
        this.jobsProcessed = 0;
    }

    @Override
    public void run() {
        for (;;) {

            try {

                try {
                    WineQuality wq = queue.take();
                    if (wq.getList() == null) {
                        break;
                    }

                    for (int i = 0; i < wq.getList().size(); i++) {
                        if (wq.getList().get(i) < pivotValue) {
                            synchronized (smallerThanPivot) {
                                smallerThanPivot.add(wq.getList().get(i));
                            }
                        } else if (Objects.equals(wq.getList().get(i), pivotValue)) {
                            synchronized (equalsToPivot) {
                                equalsToPivot.add(wq.getList().get(i));
                            }
                        } else {
                            synchronized (biggerThanPivot) {
                                biggerThanPivot.add(wq.getList().get(i));
                            }
                        }
                        jobsProcessed++;
                    }

                    

                } catch (InterruptedException e) {
                    e.printStackTrace();
                    throw new Error(e);
                }
            } catch (Throwable e) {
                e.printStackTrace();
                throw e;
            }
        }
        System.out.println("Consumer " + id + " processed " + jobsProcessed);
    }
}
