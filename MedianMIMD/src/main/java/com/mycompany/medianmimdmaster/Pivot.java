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
public class Pivot implements Serializable {
    int pivot;

    public Pivot(int pivot) {
        this.pivot = pivot;
    }

    public int getPivot() {
        return pivot;
    }

    public void setPivot(int pivot) {
        this.pivot = pivot;
    }
    
}
