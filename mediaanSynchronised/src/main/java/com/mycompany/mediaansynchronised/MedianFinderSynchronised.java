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
class MedianFinderSynchronised extends Thread {

    int findMedian(ArrayList<Integer> list, int targetIndex) throws InterruptedException {
        ArrayList<Integer> smallerThanPivot = new ArrayList<>();
        ArrayList<Integer> biggerThanPivot = new ArrayList<>();
        ArrayList<Integer> equalsToPivot = new ArrayList<>();
        final int THREAD_COUNT = 16;

        // Necessary for join
        Thread[] threadList = new Thread[THREAD_COUNT];

        int pivot = findPivot(list);
        int pivotValue = list.get(pivot);
        System.out.println("Pivot Value: " + pivotValue);

        //
        for (int i = 0; i < THREAD_COUNT; i++) {

            final List<Integer> subList = list.subList(list.size() * i / THREAD_COUNT,
                    list.size() * (i + 1) / THREAD_COUNT);

            Thread t = new Thread() {
                public void run() {

                    ArrayList<Integer> localSmallerThanPivot = new ArrayList<>();
                    ArrayList<Integer> localBiggerThanPivot = new ArrayList<>();
                    ArrayList<Integer> localEqualsToPivot = new ArrayList<>();

                    System.out.println("subList size: " + subList.size());

                    int countRandom = 0;
                    // Code so that finding the median will take time
                    Random random = new Random();
                    int k = ((random.nextInt(50)) + 1) * 1000000;

                    for (int i1 = 0; i1 < k; i1++) {
                        countRandom++;
                    }

                    // before synchronized was taking ReadFile, time: 3917 ms
                    // now with waiting until a 100

                    for (int j = 0; j < subList.size(); j++) {
                        int value = subList.get(j);
                        if (value < pivotValue) {
                            localSmallerThanPivot.add(value);
                            if (localSmallerThanPivot.size() == 100 || (j + 1) == subList.size()) {
                                synchronized (smallerThanPivot) {
                                    smallerThanPivot.addAll(localSmallerThanPivot);
                                    localSmallerThanPivot.clear();
                                }
                            }
                        } else if (Objects.equals(value, pivotValue)) {
                            localEqualsToPivot.add(value);
                            if (localEqualsToPivot.size() == 100 || (j + 1) == subList.size()) {
                                synchronized (equalsToPivot) {
                                    equalsToPivot.addAll(localEqualsToPivot);
                                    localEqualsToPivot.clear();
                                }
                            }
                        } else {
                            localBiggerThanPivot.add(value);
                            if (localBiggerThanPivot.size() == 100 || (j + 1) == subList.size()) {
                                synchronized (biggerThanPivot) {
                                    biggerThanPivot.addAll(localBiggerThanPivot);
                                    localBiggerThanPivot.clear();
                                }
                            }
                        }
                    }
                }
            };
            t.start();
            threadList[i] = t;
        }
        // Join isn't working correctly because length of all lists together are not the total list size
        for (Thread t : threadList) {

//            System.out.println("---------------------THREAD--------------------------");
//            System.out.println("smallerThanPivot: " + smallerThanPivot.size());
//            System.out.println("equalsToPivot: " + equalsToPivot.size());
//            System.out.println("biggerThanPivot: " + biggerThanPivot.size());
//            System.out.println("-------------------THREAD END----------------------------");
            t.join();
        }
        System.out.println("-----------------------------------------------");
        System.out.println("total list: " + list.size());
        System.out.println("smallerThanPivot: " + smallerThanPivot.size());
        System.out.println("equalsToPivot: " + equalsToPivot.size());
        System.out.println("biggerThanPivot: " + biggerThanPivot.size());
        System.out.println("-----------------------------------------------");

        if (smallerThanPivot.size() > targetIndex) {
            return findMedian(smallerThanPivot, targetIndex);
        } else if ((smallerThanPivot.size() + equalsToPivot.size()) > targetIndex) {
            return pivotValue;
        } else {
            return findMedian(biggerThanPivot, targetIndex - smallerThanPivot.size() - equalsToPivot.size());
        }

    }

    int findRealMedian(ArrayList<Integer> list) throws InterruptedException {

        int targetIndex = divideArrayList(list);
        return findMedian(list, targetIndex);
    }

    private int divideArrayList(ArrayList<Integer> list) {
        if (list.size() % 2 == 1) {
            return (list.size() - 1) / 2;
        } else {
            return list.size() / 2;
        }
    }

    private int findPivot(ArrayList<Integer> list) {
        return ThreadLocalRandom.current().nextInt(0, list.size());
    }

}
