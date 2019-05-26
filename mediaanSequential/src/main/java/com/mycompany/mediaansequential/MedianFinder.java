/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mediaansequential;


import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Finds the median of a list without sorting
 * Ignores part of list that doesn't contain the median value
 * 
 * @author Siree
 */
public class MedianFinder {

    public int findMedian(ArrayList<Integer> list, int targetIndex) {
        ArrayList<Integer> smallerThanPivot = new ArrayList<>();
        ArrayList<Integer> biggerThanPivot = new ArrayList<>();
        ArrayList<Integer> equalsToPivot = new ArrayList<>();

        int pivot = findPivot(list);

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) < list.get(pivot)) {
                smallerThanPivot.add(list.get(i));
            } else if (i == pivot) {
                //Do nothing
            } else if (Objects.equals(list.get(i), list.get(pivot))) {
                equalsToPivot.add(list.get(i));
            } else {
                biggerThanPivot.add(list.get(i));
            }
        }

        if (smallerThanPivot.size()
                > targetIndex) {
            return findMedian(smallerThanPivot, targetIndex);
        } else if ((smallerThanPivot.size() + equalsToPivot.size()) > targetIndex) {
            return list.get(pivot);
        } else {
            return findMedian(biggerThanPivot, targetIndex - smallerThanPivot.size() - equalsToPivot.size() - 1);
        }
    }

    public int findrealMedian(ArrayList<Integer> list) {
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
