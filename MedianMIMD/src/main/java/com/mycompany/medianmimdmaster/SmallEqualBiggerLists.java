/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.medianmimdmaster;

import java.io.Serializable;

/**
 *
 * @author Siree
 */
public class SmallEqualBiggerLists implements Serializable {
    String id;
    int smallerThanPivot;
    int equalsToPivot;
    int biggerThanPivot;
    
    public SmallEqualBiggerLists(){}
    
    public SmallEqualBiggerLists(String id, int smallerThanPivot, int equalsToPivot, int biggerThanPivot) {
        this.id = id;
        this.smallerThanPivot = smallerThanPivot;
        this.equalsToPivot = equalsToPivot;
        this.biggerThanPivot = biggerThanPivot;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getSmallerThanPivot() {
        return smallerThanPivot;
    }

    public void setSmallerThanPivot(int smallerThanPivot) {
        this.smallerThanPivot = smallerThanPivot;
    }

    public int getEqualsToPivot() {
        return equalsToPivot;
    }

    public void setEqualsToPivot(int equalsToPivot) {
        this.equalsToPivot = equalsToPivot;
    }

    public int getBiggerThanPivot() {
        return biggerThanPivot;
    }

    public void setBiggerThanPivot(int biggerThanPivot) {
        this.biggerThanPivot = biggerThanPivot;
    }
    
    
}
