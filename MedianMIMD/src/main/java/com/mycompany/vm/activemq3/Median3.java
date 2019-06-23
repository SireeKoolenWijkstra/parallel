/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.vm.activemq3;

import com.mycompany.activemqmaster.LengthMessage;
import com.mycompany.activemqmaster.Pivot;
import com.mycompany.activemqmaster.PivotCategory;
import com.mycompany.activemqmaster.SmallEqualBiggerLists;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Tamara
 */
public class Median3 {

    private static final String URL = "tcp://localhost:61616";
    private static final String TO_LENGTH_LIST_QUEUE = "lengthAllLists";
    private static final String TO_QUEUE_3 = "fromQueue3";
    private static final String FROM_QUEUE_3 = "toQueue3";
    private static final String VM_3 = "3";

    private static boolean firstRun = true;
    private static boolean active = true;
    private static String format = "%-40s%s%n";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, JMSException {

        //total list of one csv is read and put in memory.
        ArrayList<Integer> list;
        ReadCsv csv = new ReadCsv();
        list = csv.readFile();

        long startConnection = System.currentTimeMillis();
        //Setting up the connection to activeMQ.
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(URL);

        // make sure they can read each others package by trusting all. Security wise, this needs to be looked at.
        connectionFactory.setTrustAllPackages(true);
        Connection connection = connectionFactory.createConnection();
        connection.start();

        //Creating Session on connection. 
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        //creating queues on session
        Destination destination_toLengthListQueue = session.createQueue(TO_LENGTH_LIST_QUEUE);
        Destination destination_toQueue = session.createQueue(TO_QUEUE_3);
        Destination destination_fromQueue = session.createQueue(FROM_QUEUE_3);

        // creating producer and consumer with correct queues attached
        MessageProducer producer = session.createProducer(destination_toQueue);
        MessageProducer producer_toLengthList = session.createProducer(destination_toLengthListQueue);
        MessageConsumer consumer = session.createConsumer(destination_fromQueue);

        System.out.printf(format, "Connection VM_3 set up in ", (System.currentTimeMillis() - startConnection) + " ms");

        // if it's the first run we need the producer to send the list length to manager
        // this is done before listening to other messages
        // it keeps this in memory until its shutdown
        LengthMessage lm = new LengthMessage(VM_3, list.size());
        // send message about totalListSize
        ObjectMessage LengthMessageTo = session.createObjectMessage(lm);
        producer_toLengthList.send(LengthMessageTo);


        while (active) {
            // let consumer receive messages
            Message message = consumer.receive();
//            System.out.println("Received Message VM_3");

            //If consumer receive message it will check whether it's one of the known objects and do something
            if (message instanceof ObjectMessage) {
                ObjectMessage om = (ObjectMessage) message;
                var m = om.getObject();

                if (m instanceof Pivot) {
                    Pivot pi = (Pivot) om.getObject();
                    //retrieve the startAllAtOnce index number
                    int pivotIndex = pi.getPivot();
//                    System.out.println("PivotIndex VM_3: " + pivotIndex);
                    // get the startAllAtOnce index value from list and send message about value of requested index
                    Pivot pivotIndexValue = new Pivot(list.get(pivotIndex));
                    ObjectMessage pivotIndexValueTo = session.createObjectMessage(pivotIndexValue);
                    producer.send(pivotIndexValueTo);
                }
                else if (m instanceof PivotCategory) {
                    PivotCategory pc = (PivotCategory) om.getObject();
                    int pivotValue = pc.getPivotValue();
                    String listCategory = pc.getListCategory();

                    //create new object instance of medianFinder
                    SmallEqualBiggerLists lengthList;
                    if (firstRun) {
                        //With the first run there is no listCategory because it's still the whole list
                        lengthList = MedianFinder3.findMedianMIMD(pivotValue, list, null);
//                        System.out.println("pivotValue VM_3: " + pivotValue);
//                        System.out.println("list VM_3: " + list.size());
                        firstRun = false;
                    } else {
                        //Using only startAllAtOnce value and list category to determine the new list that needs to be processed
                        lengthList = MedianFinder3.findMedianMIMD(pivotValue, null, listCategory);

//                        System.out.println("pivotValue VM_3: " + pivotValue);
//                        System.out.println("listCategory VM_3: " + listCategory);
                    }
                    // Adding the Id of the VM so manager knows where values came from
                    lengthList.setId(VM_3);
                    //sending to queue for manager to determine if vm is found
                    ObjectMessage messageTo = session.createObjectMessage(lengthList);
                    producer.send(messageTo);

                } else {
                    throw new Error("object unknown");
                }
            }
        }
        // while active is true the connection is not closed
        connection.close();
    }
}
