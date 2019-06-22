/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mediaanproducerconsumer;

import java.util.ArrayList;

/**
 *
 * @author Siree
 */
public class DataHandler {

    // marks which part of the dataset has been handed out
    int count = 0;
    boolean finished = false;

    final ArrayList<Integer> smallerThanPivot = new ArrayList<>();
    final ArrayList<Integer> equalsToPivot = new ArrayList<>();
    final ArrayList<Integer> biggerThanPivot = new ArrayList<>();
    
    ArrayList<Integer> dataSet;

    public DataHandler(ArrayList<Integer> dataSet) {
        this.dataSet = dataSet;
    }
    
    

    /*
    This method gives to the Worker the indices of the dataSet which the Worker needs
    to divide in either smaller, equal or bigger than the pivot. The first index is 0, the
    end is 100, the next index will be 101 of which the end will be 201 etc
     */
    public ArrayList<Integer> takeWork() {
        ArrayList<Integer> subDataSetIndices = new ArrayList<>();
        
        //count + 100, so that we will partition dataSet bij a 100 each time
        subDataSetIndices.add(count);
        if (!finished) {
            if (dataSet.size() - count <= 100) {
                subDataSetIndices.add(dataSet.size() - 1);
                finished = true;
            } else {
                subDataSetIndices.add(count + 100);
            }
            count = count + 101;
            return subDataSetIndices;
        }else return null;

    }
}
