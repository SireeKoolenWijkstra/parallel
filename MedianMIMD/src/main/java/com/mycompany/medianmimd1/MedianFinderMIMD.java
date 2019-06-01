/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.medianmimd1;

import java.util.ArrayList;
import java.util.Objects;
import com.mycompany.medianmimdmaster.*;

/**
 *
 * @author Siree
 */
public class MedianFinderMIMD {

    private int jobsProcessed;
    private ArrayList<Integer> list;
    private ArrayList<Integer> smallerThanPivot;
    private ArrayList<Integer> equalsToPivot;
    private ArrayList<Integer> biggerThanPivot;

    MedianFinderMIMD() {
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
        } else {
            System.out.println("first run! ");
        }

        smallerThanPivot = new ArrayList<>();
        equalsToPivot = new ArrayList<>();
        biggerThanPivot = new ArrayList<>();

        for (int i = 0; i < this.list.size(); i++) {
            if (this.list.get(i) < this.list.get(pivotValue)) {
                smallerThanPivot.add(this.list.get(i));
            } else if (Objects.equals(this.list.get(i), this.list.get(pivotValue))) {
                equalsToPivot.add(this.list.get(i));
            } else {
                biggerThanPivot.add(this.list.get(i));
            }
        }
        SmallEqualBiggerLists lengthList = new SmallEqualBiggerLists(
                null,
                smallerThanPivot.size(),
                equalsToPivot.size(),
                biggerThanPivot.size());

        return lengthList;
    }
;
}
