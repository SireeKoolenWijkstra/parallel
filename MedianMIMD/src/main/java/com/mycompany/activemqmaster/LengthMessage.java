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
public class LengthMessage implements Serializable {
    
    String id;
    int listSize;

    public LengthMessage(String vmName, int listSize) {
        this.id = vmName;
        this.listSize = listSize;
    }

    LengthMessage() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getListSize() {
        return listSize;
    }

    public void setListSize(int listSize) {
        this.listSize = listSize;
    }
    
}