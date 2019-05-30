/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mediaanproducerconsumer.Objects;

import java.util.ArrayList;

/**
 *
 * @author Siree
 */
public class WineQuality {

    ArrayList<Integer> list;

    public WineQuality() {
    }

    public WineQuality(ArrayList<Integer> list) {
        this.list = list;
    }

    public ArrayList<Integer> getList() {
        return list;
    }

    public void setList(ArrayList<Integer> list) {
        this.list = list;
    }

}
