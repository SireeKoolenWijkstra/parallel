/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mediaansynchronised;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Finds the median of a list without sorting Ignores part of list that doesn't
 * contain the median value
 *
 * @author Siree
 */
public class MedianFinderSynchronised extends Thread {

    public int findMedian(ArrayList<Integer> list, int targetIndex) throws InterruptedException {
        ArrayList<Integer> smallerThanPivot = new ArrayList<>();
        ArrayList<Integer> biggerThanPivot = new ArrayList<>();
        ArrayList<Integer> equalsToPivot = new ArrayList<>();
        final int THREAD_COUNT = 16;

        // Necessary for join
        Thread[] threadList = new Thread[THREAD_COUNT];

        int pivot = findPivot(list);

        //
        for (int i = 0; i < THREAD_COUNT; i++) {

            final List<Integer> subList = list.subList(list.size() * i / THREAD_COUNT,
                    list.size() * (i + 1) / THREAD_COUNT);

            Thread t = new Thread() {
                public void run() {

                    int countRandom = 0;
                    // Code so that finding the median will take time
                    Random random = new Random();
                    int k = ((random.nextInt(50)) + 1) * 1000000;

                    for (int i = 0; i < k; i++) {
                        countRandom++;
                    }

                    for (int j = 0; j < subList.size(); j++) {
                        if (subList.get(j) < list.get(pivot)) {
                            synchronized (smallerThanPivot) {
                                smallerThanPivot.add(subList.get(j));
                            }
                        } else if (Objects.equals(subList.get(j), list.get(pivot))) {
                            synchronized (equalsToPivot) {
                                equalsToPivot.add(subList.get(j));
                            }
                        } else {
                            synchronized (biggerThanPivot) {
                                biggerThanPivot.add(subList.get(j));
                            }
                        }
                    }
                }
            };
            t.start();
            threadList[i] = t;
        }
        for (Thread t : threadList) {
            t.join();
        }

        if (smallerThanPivot.size()
                > targetIndex) {
            return findMedian(smallerThanPivot, targetIndex);
        } else if ((smallerThanPivot.size() + equalsToPivot.size()) > targetIndex) {
            return list.get(pivot);
        } else {
            return findMedian(biggerThanPivot, targetIndex - smallerThanPivot.size() - equalsToPivot.size());
        }

    }

    public int findrealMedian(ArrayList<Integer> list) throws InterruptedException {

        int targetIndex = divideArrayList(list);
        return findMedian(list, targetIndex);
    }

    public int divideArrayList(ArrayList<Integer> list) {
        if (list.size() % 2 == 1) {
            return (list.size() - 1) / 2;
        } else {
            return list.size() / 2;
        }
    }

    public int findPivot(ArrayList<Integer> list) {
        return ThreadLocalRandom.current().nextInt(0, list.size());
    }

}
