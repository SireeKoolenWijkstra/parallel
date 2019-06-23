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
 * @author Siree & Tamara
 */
public class MedianFinderTest {

    /**
     * Test of findRealMedian method, of class MedianFinder.Limit values are a list of null,
     * list.size = 1, an even and an uneven list and the last index to check for index out
     * of bounds errors
     */

    private MedianFinder medianFinder;

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
    public void testFindRealMedianTest_WithListNull_ShouldReturnNullPointerException() {
        //Arrange
        ArrayList<Integer> testWithNull = null;
        System.out.println("findRealMedianTestWithNull");

        //Act
        medianFinder.findRealMedian(testWithNull);
    }

    /**
     * Test of findRealMedian method, of class MedianFinder.
     */
    @Test
    public void testFindRealMedianTest_WithListSizeOne_ShouldReturn1() {
        //Arrange
        ArrayList<Integer> testWithListSizeOne = new ArrayList<>();
        System.out.println("findRealMedianTestWithListSizeOne");
        testWithListSizeOne.add(1);
        int expResult = 1;
        //Act
        int result = medianFinder.findRealMedian(testWithListSizeOne);
        //Assert
        assertEquals(expResult, result);

    }

    @Test
    public void testFindRealMedianTest_WithListSizeEven_ShouldReturn3() {
        //Arrange
        System.out.println("findRealMedianTestWithListSizeEven");

        ArrayList<Integer> testWithListSizeEven = new ArrayList<>();

        testWithListSizeEven.add(1);
        testWithListSizeEven.add(2);
        testWithListSizeEven.add(3);
        testWithListSizeEven.add(4);
        int expResult = 3;
        //Act
        int result = medianFinder.findRealMedian(testWithListSizeEven);
        //Assert
        assertEquals(expResult, result);

    }

    @Test
    public void testFindRealMedianTest_WithListSizeUneven_ShouldReturn3() {
        //Arrange
        System.out.println("findRealMedianTestWithListSizeEven");

        ArrayList<Integer> testWithListSizeUneven = new ArrayList<>();

        testWithListSizeUneven.add(1);
        testWithListSizeUneven.add(2);
        testWithListSizeUneven.add(3);
        testWithListSizeUneven.add(4);
        testWithListSizeUneven.add(5);
        int expResult = 3;
        //Act
        int result = medianFinder.findRealMedian(testWithListSizeUneven);
        //Assert
        assertEquals(expResult, result);

    }

    @Test
    public void testFindRealMedianTest_LastIndex_ShouldReturn3() {
        //Arrange
        System.out.println("findRealMedianTestWithListSizeEven");

        ArrayList<Integer> testLastIndex = new ArrayList<>();

        testLastIndex.add(1);
        testLastIndex.add(2);
        testLastIndex.add(3);
        int targetIndex = testLastIndex.size() - 1;

        int expResult = 3;
        //Act
        int result = medianFinder.findMedian(testLastIndex, targetIndex);
        //Assert
        assertEquals(expResult, result);

    }


}
