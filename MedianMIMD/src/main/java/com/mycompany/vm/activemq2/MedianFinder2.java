/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.vm.activemq2;

import com.mycompany.activemqmaster.SmallEqualBiggerLists;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

/**
 *
 * @author Tamara
 */
public class MedianFinder2 {

    private int jobsProcessed;
    private ArrayList<Integer> list;
    private ArrayList<Integer> smallerThanPivot;
    private ArrayList<Integer> equalsToPivot;
    private ArrayList<Integer> biggerThanPivot;

    MedianFinder2() {
        this.jobsProcessed = 0;
    }

    public SmallEqualBiggerLists findMedianMIMD(int pivotValue, ArrayList<Integer> list, String listCategory) {
        this.list = list;
        if (listCategory != null && list == null) {
            if ("smallerThanPivot".equals(listCategory)) {
                this.list = smallerThanPivot;
            }
            if ("biggerThanPivot".equals(listCategory)) {
                this.list = biggerThanPivot;
            }
        }

        smallerThanPivot = new ArrayList<>();
        equalsToPivot = new ArrayList<>();
        biggerThanPivot = new ArrayList<>();

        for (Integer integer : this.list) {
            if (integer < this.list.get(pivotValue)) {
                int countRandom = 0;
                // Code so that finding the median will take time
                Random random = new Random();
                int j = ((random.nextInt(50)) + 1) * 1000000;

                //Act
                for (int i = 0; i < j; i++) {
                    countRandom++;
                }
                smallerThanPivot.add(integer);
            } else if (Objects.equals(integer, this.list.get(pivotValue))) {
                int countRandom = 0;
                // Code so that finding the median will take time
                Random random = new Random();
                int j = ((random.nextInt(50)) + 1) * 1000000;

                //Act
                for (int i = 0; i < j; i++) {
                    countRandom++;
                }
                equalsToPivot.add(integer);
            } else {
                int countRandom = 0;
                // Code so that finding the median will take time
                Random random = new Random();
                int j = ((random.nextInt(50)) + 1) * 1000000;

                //Act
                for (int i = 0; i < j; i++) {
                    countRandom++;
                }
                biggerThanPivot.add(integer);
            }
        }

        return new SmallEqualBiggerLists(
                null,
                smallerThanPivot.size(),
                equalsToPivot.size(),
                biggerThanPivot.size());
    }
}
