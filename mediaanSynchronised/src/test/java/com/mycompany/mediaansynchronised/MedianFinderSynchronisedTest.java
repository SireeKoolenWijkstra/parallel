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
    
    public MedianFinderSynchronisedTest() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of findMedian method, of class MedianFinderSynchronised.
     */
    @Test
    public void testFindMedian() throws Exception {
        System.out.println("findMedian");
        ArrayList<Integer> list = null;
        int targetIndex = 0;
        MedianFinderSynchronised instance = new MedianFinderSynchronised();
        int expResult = 0;
        int result = instance.findMedian(list, targetIndex);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findrealMedian method, of class MedianFinderSynchronised.
     */
    @Test
    public void testFindrealMedian() throws Exception {
        System.out.println("findrealMedian");
        ArrayList<Integer> list = null;
        MedianFinderSynchronised instance = new MedianFinderSynchronised();
        int expResult = 0;
        int result = instance.findrealMedian(list);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of divideArrayList method, of class MedianFinderSynchronised.
     */
    @Test
    public void testDivideArrayList() {
        System.out.println("divideArrayList");
        ArrayList<Integer> list = null;
        MedianFinderSynchronised instance = new MedianFinderSynchronised();
        int expResult = 0;
        int result = instance.divideArrayList(list);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findPivot method, of class MedianFinderSynchronised.
     */
    @Test
    public void testFindPivot() {
        System.out.println("findPivot");
        ArrayList<Integer> list = null;
        MedianFinderSynchronised instance = new MedianFinderSynchronised();
        int expResult = 0;
        int result = instance.findPivot(list);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
