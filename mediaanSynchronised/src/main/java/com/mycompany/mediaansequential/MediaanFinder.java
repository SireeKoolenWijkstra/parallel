/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mediaansequential;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Wat te doen met waarden die gelijk zijn aan list.get(pivot)?
 *
 * @author Siree
 */
public class MediaanFinder {

    public int findMediaan(ArrayList<Integer> list, int targetIndex) {
        ArrayList<Integer> smaller = new ArrayList<>();
        ArrayList<Integer> bigger = new ArrayList<>();
        ArrayList<Integer> equals = new ArrayList<>();

        int pivot = findPivot(list);

        for (int i = 0;
                i < list.size();
                i++) {
            if (list.get(i) < list.get(pivot)) {
                smaller.add(list.get(i));
            } else if (i == pivot) {
                //Do nothing
            } else if ((list.get(i) == list.get(pivot)) && i < pivot) {
                smaller.equals(list.get(i));
            } else if ((list.get(i) == list.get(pivot)) && i > pivot) {
                bigger.equals(list.get(i));
            } else {
                bigger.add(list.get(i));
            }
        }

        if (smaller.size()
                > targetIndex) {
            return findMediaan(smaller, targetIndex);
        } else if ((smaller.size() + equals.size()) > targetIndex) {
            return list.get(pivot);
        } else {
            return findMediaan(bigger, targetIndex - smaller.size() - equals.size() - 1);
        }
    }

    public int findrealMediaan(ArrayList<Integer> list) {
        int targetIndex = divideArrayList(list);
        return findMediaan(list, targetIndex);
    }

    public int divideArrayList(ArrayList<Integer> list) {
        if (list.size() % 2 == 1) {
            int mediaanIndex = (list.size() - 1) / 2;
            return mediaanIndex;
        } else {
            int mediaanIndex = list.size() / 2;
            return mediaanIndex;
        }
    }

    public int findPivot(ArrayList<Integer> list) {
        int index = ThreadLocalRandom.current().nextInt(0, list.size());
        return index;

    }
}
