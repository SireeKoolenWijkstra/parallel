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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * Finds the median of a list without sorting Ignores part of list that doesn't
 * contain the median value
 *
 * @author Siree
 */
class MedianFinderSynchronised extends Thread {

    public int findMedian(ArrayList<Integer> list, int targetIndex) throws InterruptedException {
        ArrayList<Integer> smallerThanPivot = new ArrayList<>();
        ArrayList<Integer> biggerThanPivot = new ArrayList<>();
        ArrayList<Integer> equalsToPivot = new ArrayList<>();
        final int MAX_THREADS = 8;

        int pivot = findPivot(list);
        int pivotValue = list.get(pivot);

        ExecutorService executorService = Executors.newFixedThreadPool(MAX_THREADS);

        //
        for (int i = 0; i < MAX_THREADS; i++) {

            final List<Integer> subList = list.subList(list.size() * i / MAX_THREADS,
                    list.size() * (i + 1) / MAX_THREADS);

            executorService.submit(() -> {

                ArrayList<Integer> localSmallerThanPivot = new ArrayList<>();
                ArrayList<Integer> localBiggerThanPivot = new ArrayList<>();
                ArrayList<Integer> localEqualsToPivot = new ArrayList<>();

                for (int j = 0; j < subList.size(); j++) {

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
            });
        };
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.DAYS);

        if (smallerThanPivot.size()
                > targetIndex) {
            return findMedian(smallerThanPivot, targetIndex);
        } else if ((smallerThanPivot.size()
                + equalsToPivot.size()) > targetIndex) {
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
