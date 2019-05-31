/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.medianmimdmaster;

/**
 *
 * @author Siree
 */
public class LengthMessage {
    
    String vmName;
    int listSize;

    public LengthMessage(String vmName, int listSize) {
        this.vmName = vmName;
        this.listSize = listSize;
    }

    LengthMessage() {}

    public String getVmName() {
        return vmName;
    }

    public void setVmName(String vmName) {
        this.vmName = vmName;
    }

    public int getListSize() {
        return listSize;
    }

    public void setListSize(int listSize) {
        this.listSize = listSize;
    }
    
    
    
}
