/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mediaansequential;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Finds the median of a list without sorting Ignores part of list that doesn't
 * contain the median value
 *
 * @author Siree & Tamara
 */
class MedianFinder {
    private int recursiveCount;

    int findMedian(ArrayList<Integer> list, int targetIndex) {
        recursiveCount++;
        ArrayList<Integer> smallerThanPivot = new ArrayList<>();
        ArrayList<Integer> biggerThanPivot = new ArrayList<>();
        ArrayList<Integer> equalsToPivot = new ArrayList<>();

        int pivot = findPivot(list);

        for (int i = 0; i < list.size(); i++) {

            // Code so that finding the median will take time
            int countRandom = 0;
            Random random = new Random();
            int k = ((random.nextInt(80)) + 1) * 15000000;

            //Act
            for (int j = 0; j < k; j++) {
                countRandom++;
            }

            //Dividing over different lists based on Pivot
            if (list.get(i) < list.get(pivot)) {
                smallerThanPivot.add(list.get(i));
            } else if (Objects.equals(list.get(i), list.get(pivot))) {
                equalsToPivot.add(list.get(i));
            } else {
                biggerThanPivot.add(list.get(i));
            }
        }

        if (smallerThanPivot.size() > targetIndex) {
            return findMedian(smallerThanPivot, targetIndex);
        } else if ((smallerThanPivot.size() + equalsToPivot.size()) > targetIndex) {
            System.out.printf("%-30s%s%n", "Median found in ", recursiveCount + " cycles");
            return list.get(pivot);
        } else {
            return findMedian(biggerThanPivot, targetIndex - smallerThanPivot.size() - equalsToPivot.size());
        }
    }

    int findRealMedian(ArrayList<Integer> list) {
        int targetIndex = divideArrayList(list);
        recursiveCount = 0;
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
