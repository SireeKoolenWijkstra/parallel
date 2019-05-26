/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mediaansequential;

import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Siree
 */
public class MediaanSequential {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        
//        ArrayList<Integer> testCase = new ArrayList<Integer>();//Creating arraylist
//        testCase.add(10);//Adding object in arraylist    
//        testCase.add(17);
//        testCase.add(12);
//        testCase.add(13);
//        testCase.add(14);
        //testCase.add(15);
        
        ReadCsv readCsv = new ReadCsv();
        
        MediaanFinder mediaanFinder = new MediaanFinder();

        System.out.println("Working Directory = "
                + System.getProperty("user.dir"));

        ArrayList<Integer> list = readCsv.readFile();
        System.out.print("Mediaan is " + mediaanFinder.findrealMediaan(list));
    }

}
