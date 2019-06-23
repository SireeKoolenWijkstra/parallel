/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.vm.activemq4;

import com.mycompany.activemqmaster.SmallEqualBiggerLists;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

/**
 *
 * @author Tamara
 */
public class MedianFinder4 {

    private static int jobsProcessed;
    private static ArrayList<Integer> list;
    private static ArrayList<Integer> smallerThanPivot;
    private static ArrayList<Integer> equalsToPivot;
    private static ArrayList<Integer> biggerThanPivot;

    MedianFinder4() {
        this.jobsProcessed = 0;
    }

    public static SmallEqualBiggerLists findMedianMIMD(int pivotValue, ArrayList<Integer> list, String listCategory) {
        if (listCategory != null && list == null) {
            if ("smallerThanPivot".equals(listCategory)) {
                list = smallerThanPivot;
            }
            if ("biggerThanPivot".equals(listCategory)) {
                list = biggerThanPivot;
            }
        }

        smallerThanPivot = new ArrayList<>();
        equalsToPivot = new ArrayList<>();
        biggerThanPivot = new ArrayList<>();

        // Code so that finding the median will take time
        int countRandom = 0;
        Random random = new Random();
        int k = ((random.nextInt(5)) + 1) * 1000/4;

        //Act
        for (int j = 0; j < k; j++) {
            countRandom = (countRandom + 1) & 7;

        }
        if (countRandom > 10) {
            System.out.println("countRandom was skipped in run because it was never used");
        }

        assert list != null;
        for (int i = 0; i < list.size(); i++) {
            int value = list.get(i);
            if (value < pivotValue) {
                smallerThanPivot.add(value);
            } else if (value == pivotValue) {
                equalsToPivot.add(value);
            } else {
                biggerThanPivot.add(value);
            }
        }

        return new SmallEqualBiggerLists(
                null,
                smallerThanPivot.size(),
                equalsToPivot.size(),
                biggerThanPivot.size());
    }
}
