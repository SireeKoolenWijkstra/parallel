/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.activemqmaster;

import java.io.Serializable;

/**
 *
 * @author Tamara
 */
public class PivotCategory implements Serializable {
    
    private int pivotValue;
    private String listCategory;

    public PivotCategory(int pivotValue, String listCategory) {
        this.pivotValue = pivotValue;
        this.listCategory = listCategory;
    }

    public int getPivotValue() {
        return pivotValue;
    }

    public void setPivotValue(int pivotValue) {
        this.pivotValue = pivotValue;
    }

    public String getListCategory() {
        return listCategory;
    }

    public void setListCategory(String listCategory) {
        this.listCategory = listCategory;
    }
    
    
    

}
