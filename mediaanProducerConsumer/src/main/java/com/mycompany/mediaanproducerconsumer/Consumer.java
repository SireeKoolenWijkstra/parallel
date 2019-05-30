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
import javax.xml.crypto.dsig.keyinfo.KeyValue;

/**
 *
 * @author Siree
 */
public class Consumer extends Thread {

    private BlockingQueue<WineQuality> queue;
    private int id;
    private int jobsProcessed;

    MedianProducerConsumer mpc = new MedianProducerConsumer();

    Consumer(BlockingQueue<WineQuality> queue, int id) {
        this.queue = queue;
        this.id = id;
        this.jobsProcessed = 0;
    }

    @Override
    public void run() {
        try {

            for (;;) {
                try {
                    WineQuality wq = queue.take();

                    for (int i = 0; i < wq.getList().size(); i++) {

                        if (wq.getList().get(i) < wq.getPivot()) {
                            synchronized (mpc.smallerThanPivot) {
                                mpc.smallerThanPivot.add(wq.getList().get(i));
                            }
                        } else if (Objects.equals(wq.getList().get(i), wq.getPivot())) {
                            synchronized (mpc.equalsToPivot) {
                                mpc.equalsToPivot.add(wq.getList().get(i));
                            }
                        } else {
                            synchronized (mpc.biggerThanPivot) {
                                mpc.biggerThanPivot.add(wq.getList().get(i));
                            }
                        }
                        jobsProcessed++;
                    }

                    System.out.println("Consumer " + id + " processed " + jobsProcessed);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
            throw e;
        }
    }
}
