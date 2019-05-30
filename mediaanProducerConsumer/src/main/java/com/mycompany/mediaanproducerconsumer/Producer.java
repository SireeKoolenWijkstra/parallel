/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mediaanproducerconsumer;

import com.mycompany.mediaanproducerconsumer.Objects.WineQuality;
import java.util.ArrayList;
import java.util.List;
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
        //TODO find the right spot for findPivot
        int pivotValue = findPivot(list);
        ArrayList<Integer> subList = new ArrayList<>();

        try {
            jobsProduced = 0;
            for (int i = 0; i < list.size(); i++) {
                subList.add(list.get(i));
                if (subList.size() == 100) {
                    queue.put(new WineQuality(subList, pivotValue));
                    jobsProduced++;
                    subList = new ArrayList<Integer>();
                }
            }
            System.out.println("Producer has put " + jobsProduced + " on the queue.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //returns value on index (diffrent from MedianSynchronised, where it returns 
    // the index)
    public int findPivot(ArrayList<Integer> list) {
        return list.get(ThreadLocalRandom.current().nextInt(0, list.size()));
    }

}
