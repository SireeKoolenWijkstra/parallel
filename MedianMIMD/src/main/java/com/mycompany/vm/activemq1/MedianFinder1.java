/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.vm.activemq1;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

import com.mycompany.activemqmaster.*;

/**
 *
 * @author Tamara
 */
public class MedianFinder1 {

    private static int jobsProcessed;
    private static ArrayList<Integer> list;
    private static ArrayList<Integer> smallerThanPivot;
    private static ArrayList<Integer> equalsToPivot;
    private static ArrayList<Integer> biggerThanPivot;

    MedianFinder1() {
        this.jobsProcessed = 0;
    }

    public static SmallEqualBiggerLists findMedianMIMD(int pivotValue, ArrayList<Integer> list, String listCategory) {

        if (listCategory != null && list == null) {
            if ("smallerThanPivot".equals(listCategory)) {
                System.out.println("listCategory: " + listCategory);
                System.out.println("List is smaller with size: " + smallerThanPivot.size());
                list = smallerThanPivot;
            }
            if ("biggerThanPivot".equals(listCategory)) {
                System.out.println("List is bigger with size: " + biggerThanPivot.size());
                list = biggerThanPivot;
            }
        }

        smallerThanPivot = new ArrayList<>();
        equalsToPivot = new ArrayList<>();
        biggerThanPivot = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            int value = list.get(i);
            if (value < pivotValue) {
                int countRandom = 0;
                // Code so that finding the median will take time
                Random random = new Random();
                int k = ((random.nextInt(50)) + 1) * 1000000;

                //Act
                for (int j = 0; j < k; j++) {
                    countRandom++;
                }
                smallerThanPivot.add(value);
            } else if (value == pivotValue) {
                int countRandom = 0;
                // Code so that finding the median will take time
                Random random = new Random();
                int k = ((random.nextInt(50)) + 1) * 1000000;

                //Act
                for (int j = 0; j < k; j++) {
                    countRandom++;
                }
                equalsToPivot.add(value);
            } else {
                int countRandom = 0;
                // Code so that finding the median will take time
                Random random = new Random();
                int k = ((random.nextInt(50)) + 1) * 1000000;

                //Act
                for (int j = 0; j < k; j++) {
                    countRandom++;
                }
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
