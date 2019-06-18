/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mediaanproducerconsumer;

import java.util.ArrayList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Siree
 */
public class FindMedianProducerConsumerTest {

    FindMedianProducerConsumer findMedianProducerConsumer;

    @Before
    public void setUp() throws Exception {
        System.out.println("FindMedianProducerConsumerTest: Before method setUp()");
        findMedianProducerConsumer = new FindMedianProducerConsumer();
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("FindMedianProducerConsumerTest: After method tearDown()");
        findMedianProducerConsumer = null;
    }

    /**
     * Test of FindMedianProducerConsumer method, of class
     * FindMedianProducerConsumer.
     */
    @Test (expected = NullPointerException.class)
    public void testFindMedianProducerConsumerTestWithListNullShouldReturnNullPointerException() {
        //Arrange
        ArrayList<Integer> testWithNull = new ArrayList<>();
        System.out.println("FindMedianProducerConsumerTesttWithNull");
        testWithNull = null;

        //Act
        findMedianProducerConsumer.findMedianProducerConsumer(testWithNull);
    }

    /**
     * Test of FindMedianProducerConsumer method, of class
     * FindMedianProducerConsumer.
     */
    @Test
    public void testFindMedianProducerConsumerTestWithListSizeOneShouldReturn1() {
        //Arrange
        ArrayList<Integer> testWithListSizeOne = new ArrayList<>();
        System.out.println("FindMedianProducerConsumerTestWithListSizeOne");
        testWithListSizeOne.add(1);
        int expResult = 1;
        //Act
        int result = findMedianProducerConsumer.findMedianProducerConsumer(testWithListSizeOne);
        //Assert
        assertEquals(expResult, result);

    }

    /**
     * Test of FindMedianProducerConsumer method, of class
     * FindMedianProducerConsumer.
     */
    @Test
    public void testFindMedianProducerConsumerTestWithListSizeEvenShouldReturn3() {
        //Arrange
        System.out.println("FindMedianProducerConsumerTestWithListSizeEven");

        ArrayList<Integer> testWithListSizeEven = new ArrayList<>();

        testWithListSizeEven.add(1);
        testWithListSizeEven.add(2);
        testWithListSizeEven.add(3);
        testWithListSizeEven.add(4);
        int expResult = 3;
        //Act
        int result = findMedianProducerConsumer.findMedianProducerConsumer(testWithListSizeEven);
        //Assert
        assertEquals(expResult, result);

    }

    /**
     * Test of FindMedianProducerConsumer method, of class
     * FindMedianProducerConsumer.
     */
    @Test
    public void testFindMedianProducerConsumerTestWithListSizeUnevenShouldReturn3() {
        //Arrange
        System.out.println("FindMedianProducerConsumerTestWithListSizeEven");

        ArrayList<Integer> testWithListSizeUneven = new ArrayList<>();

        testWithListSizeUneven.add(1);
        testWithListSizeUneven.add(2);
        testWithListSizeUneven.add(3);
        testWithListSizeUneven.add(4);
        testWithListSizeUneven.add(5);
        int expResult = 3;
        //Act
        int result = findMedianProducerConsumer.findMedianProducerConsumer(testWithListSizeUneven);
        //Assert
        assertEquals(expResult, result);

    }

    /**
     * Test of FindMedianProducerConsumer method, of class
     * FindMedianProducerConsumer.
     */
    @Test
    public void testFindMedianProducerConsumerTestLastIndexShouldReturn3() {
        //Arrange
        System.out.println("FindMedianProducerConsumerTestWithListSizeEven");

        ArrayList<Integer> testLastIndex = new ArrayList<>();

        testLastIndex.add(1);
        testLastIndex.add(2);
        testLastIndex.add(3);
        int targetIndex = testLastIndex.size() - 1;

        int expResult = 3;
        //Act
        int result = findMedianProducerConsumer.compareArraysToTargetIndex(testLastIndex, targetIndex);
        //Assert
        assertEquals(expResult, result);

    }

}
