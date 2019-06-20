/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mediaansynchronised;

import java.util.ArrayList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Siree
 */
public class MedianFinderSynchronisedTest {

    MedianFinderSynchronised medianFinderSynchronised;

    @Before
    public void setUp() {
        System.out.println("MedianFinderSynchronisedTest: Before method setUp()");
        medianFinderSynchronised = new MedianFinderSynchronised();
    }

    @After
    public void tearDown() {
        System.out.println("MedianFinderSynchronisedTest: After method tearDown()");
        medianFinderSynchronised = null;
    }

    /**
     * Test of findRealMedian method, of class MedianFinderSynchronised.
     */
    @Test(expected = NullPointerException.class)
    public void testFindrealMedianSynchronisedTestWithListNullShouldReturnNullPointerException() throws InterruptedException {
        //Arrange
        ArrayList<Integer> testWithNull = new ArrayList<>();
        System.out.println("findrealMedianSynchronisedTestWithListNull");
        testWithNull = null;

        //Act
        medianFinderSynchronised.findRealMedian(testWithNull);
    }

    /**
     * Test of findRealMedian method, of class MedianFinder.
     */
    @Test
    public void testFindrealMedianSynchronisedTestWithListSizeOneShouldReturn1() throws InterruptedException {
        //Arrange
        ArrayList<Integer> testWithListSizeOne = new ArrayList<>();
        System.out.println("findrealMedianSynchronisedTestWithListSizeOne");
        testWithListSizeOne.add(1);
        int expResult = 1;
        //Act
        int result = medianFinderSynchronised.findRealMedian(testWithListSizeOne);
        //Assert
        assertEquals(expResult, result);

    }

    @Test
    public void testFindrealMedianSynchronisedTestWithListSizeEvenShouldReturn3() throws InterruptedException {
        //Arrange
        System.out.println("findrealMedianSynchornisedTestWithListSizeEven");

        ArrayList<Integer> testWithListSizeEven = new ArrayList<>();

        testWithListSizeEven.add(1);
        testWithListSizeEven.add(2);
        testWithListSizeEven.add(3);
        testWithListSizeEven.add(4);
        int expResult = 3;
        //Act
        int result = medianFinderSynchronised.findRealMedian(testWithListSizeEven);
        //Assert
        assertEquals(expResult, result);

    }

    @Test
    public void testFindrealMedianTestWithListSizeUnevenShouldReturn3() throws InterruptedException {
        //Arrange
        System.out.println("findrealMedianSynchronisedTestWithListSizeEven");

        ArrayList<Integer> testWithListSizeUneven = new ArrayList<>();

        testWithListSizeUneven.add(1);
        testWithListSizeUneven.add(2);
        testWithListSizeUneven.add(3);
        testWithListSizeUneven.add(4);
        testWithListSizeUneven.add(5);
        int expResult = 3;
        //Act
        int result = medianFinderSynchronised.findRealMedian(testWithListSizeUneven);
        //Assert
        assertEquals(expResult, result);

    }

    @Test
    public void testFindrealMedianTestLastIndexShouldReturn3() throws InterruptedException {
        //Arrange
        System.out.println("findrealMedianSynchronisedTestWithListSizeEven");

        ArrayList<Integer> testLastIndex = new ArrayList<>();

        testLastIndex.add(1);
        testLastIndex.add(2);
        testLastIndex.add(3);
        int targetIndex = testLastIndex.size() - 1;

        int expResult = 3;
        //Act
        int result = medianFinderSynchronised.findMedian(testLastIndex, targetIndex);
        //Assert
        assertEquals(expResult, result);

    }

}
