/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mediaanproducerconsumer;

import com.mycompany.mediaanproducerconsumer.Objects.WineQuality;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author Siree
 */
public class Producer extends Thread {

    private int jobsProduced;
    private ArrayList<Integer> list;
    private BlockingQueue<WineQuality> queue;

    public Producer(ArrayList<Integer> list, BlockingQueue<WineQuality> queue) {
        this.list = list;
        this.queue = queue;
    }

    @Override
    public void run() {

        ArrayList<Integer> subList = new ArrayList<>();

        try {
            jobsProduced = 0;
            for (int i = 0; i < list.size(); i++) {
                subList.add(list.get(i));
                if (subList.size() == 100) {
                    queue.put(new WineQuality(subList));
                    jobsProduced++;
                    subList = new ArrayList<Integer>();
                }
            }
            queue.put(new WineQuality(null));
            System.out.println("Producer has put " + jobsProduced + " on the queue.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
