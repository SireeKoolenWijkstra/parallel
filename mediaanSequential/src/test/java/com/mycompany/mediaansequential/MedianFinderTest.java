/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mediaansequential;

import java.util.ArrayList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

/**
 *
 * @author Siree
 */
public class MedianFinderTest {
    
     /**
     * Test of findrealMedian method, of class MedianFinder.Limit values are a list of null,
     * list.size = 1, an even and an uneven list and the last index to check for index out 
     * of bounds errors
     */
          
        MedianFinder medianFinder;
        
        @Rule
        public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() {
        System.out.println("MedianFinderTest: Before method setUp()");
        medianFinder = new MedianFinder();
    }
    
    @After
    public void tearDown() {
        System.out.println("MedianFinderTest: After method tearDown()");
        medianFinder = null;
    }

    @Test(expected = NullPointerException.class)
    public void testFindrealMedianTestWithListNullShouldReturnNullPointerException() {
        //Arrange
        ArrayList<Integer> testWithNull = new ArrayList<>();
        System.out.println("findrealMedianTestWithNull");
        testWithNull = null;
        
        //Act
        medianFinder.findrealMedian(testWithNull);
    }
    
    /**
     * Test of findrealMedian method, of class MedianFinder.
     */
    @Test
    public void testFindrealMedianTestWithListSizeOneShouldReturn1() {
        //Arrange
        ArrayList<Integer> testWithListSizeOne = new ArrayList<>();
        System.out.println("findrealMedianTestWithListSizeOne");
        testWithListSizeOne.add(1);
        int expResult = 1;
        //Act
        int result = medianFinder.findrealMedian(testWithListSizeOne);
        //Assert
        assertEquals(expResult, result);
        
    }
    
        @Test
    public void testFindrealMedianTestWithListSizeEvenShouldReturn3() {
        //Arrange
        System.out.println("findrealMedianTestWithListSizeEven");
        
        ArrayList<Integer> testWithListSizeEven = new ArrayList<>();
        
        testWithListSizeEven.add(1);
        testWithListSizeEven.add(2);
        testWithListSizeEven.add(3);
        testWithListSizeEven.add(4);
        int expResult = 3;
        //Act
        int result = medianFinder.findrealMedian(testWithListSizeEven);
        //Assert
        assertEquals(expResult, result);

    }
    
         @Test
    public void testFindrealMedianTestWithListSizeUnevenShouldReturn3() {
        //Arrange
        System.out.println("findrealMedianTestWithListSizeEven");
        
        ArrayList<Integer> testWithListSizeUneven = new ArrayList<>();
        
        testWithListSizeUneven.add(1);
        testWithListSizeUneven.add(2);
        testWithListSizeUneven.add(3);
        testWithListSizeUneven.add(4);
        testWithListSizeUneven.add(5);
        int expResult = 3;
        //Act
        int result = medianFinder.findrealMedian(testWithListSizeUneven);
        //Assert
        assertEquals(expResult, result);
        
    }
    
             @Test
    public void testFindrealMedianTestLastIndexShouldReturn3() {
        //Arrange
        System.out.println("findrealMedianTestWithListSizeEven");
        
        ArrayList<Integer> testLastIndex = new ArrayList<>(); 
        
        testLastIndex.add(1);
        testLastIndex.add(2);
        testLastIndex.add(3);
        int targetIndex = testLastIndex.size()-1;

        int expResult = 3;
        //Act
        int result = medianFinder.findMedian(testLastIndex, targetIndex);
        //Assert
        assertEquals(expResult, result);

    }


}
