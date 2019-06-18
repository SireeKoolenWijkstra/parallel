/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.activemqmaster;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import com.sun.istack.NotNull;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 *
 * @author Tamara
 */
public class MedianFindMain {

    private static final String VM_1 = "1";
    private static final String VM_2 = "2";
    private static final String VM_3 = "3";
    private static final String VM_4 = "4";

    private static boolean firstRun = true;
    private static boolean anotherRun = true;
    private static final String URL = "tcp://localhost:61616";
    private static final String TO_QUEUE_1 = "toQueue1";
    private static final String TO_QUEUE_2 = "toQueue2";
    private static final String TO_QUEUE_3 = "toQueue3";
    private static final String TO_QUEUE_4 = "toQueue4";
    private static final String FROM_QUEUE_1 = "fromQueue1";
    private static final String FROM_QUEUE_2 = "fromQueue2";
    private static final String FROM_QUEUE_3 = "fromQueue3";
    private static final String FROM_QUEUE_4 = "fromQueue4";
    private static int targetIndex;
    private static int pivotIndex;
    private static int listSize = 0;
    private static ArrayList<LengthMessage> lengthMessageList;
    private static final String SMALLER_LIST = "smallerThanPivot";
    private static final String BIGGER_LIST = "biggerThanPivot";
    private static ArrayList<SmallEqualBiggerLists> smallEqualBiggerList;
    private static int smallerThanPivotLength;
    private static int equalsToPivotLength;
    private static int biggerThanPivotLength;
    private static int subListPivotIndex;
    private static int subListPivotValue;
    private static String searchListForPivot = "";
    private static String category = null;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws JMSException, IOException {
        long totalTime = System.currentTimeMillis();


        long startConnection = System.currentTimeMillis();
        //Setting up the connection. 
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(URL);
        connectionFactory.setTrustAllPackages(true);
        Connection connection = connectionFactory.createConnection();
        connection.start();

        // Producer: Sending startAllAtOnce message to correct queue
        // connection for getting pivotValue with pivotIndex and right queue and sending requests to all
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        // initiate producer and queue for vm 1
        Destination destination_toQueue1 = session.createQueue(TO_QUEUE_1);
        MessageProducer producer_toQueue1 = session.createProducer(destination_toQueue1);

        // initiate producer and queue for vm 2
        Destination destination_toQueue2 = session.createQueue(TO_QUEUE_2);
        MessageProducer producer_toQueue2 = session.createProducer(destination_toQueue2);

        // initiate producer and queue for vm 3
        Destination destination_toQueue3 = session.createQueue(TO_QUEUE_3);
        MessageProducer producer_toQueue3 = session.createProducer(destination_toQueue3);

        // initiate producer and queue for vm 4
        Destination destination_toQueue4 = session.createQueue(TO_QUEUE_4);
        MessageProducer producer_toQueue4 = session.createProducer(destination_toQueue4);

        // Consumer: Getting startAllAtOnce message to correct queue
        Destination destination_fromQueue1 = session.createQueue(FROM_QUEUE_1);
        Destination destination_fromQueue2 = session.createQueue(FROM_QUEUE_2);
        Destination destination_fromQueue3 = session.createQueue(FROM_QUEUE_3);
        Destination destination_fromQueue4 = session.createQueue(FROM_QUEUE_4);

        //creating consumer
        MessageConsumer consumer_fromQueue1 = session.createConsumer(destination_fromQueue1);
        MessageConsumer consumer_fromQueue2 = session.createConsumer(destination_fromQueue2);
        MessageConsumer consumer_fromQueue3 = session.createConsumer(destination_fromQueue3);
        MessageConsumer consumer_fromQueue4 = session.createConsumer(destination_fromQueue4);

        System.out.println("Connection set up in " + (System.currentTimeMillis() - startConnection) + " ms");

        if(firstRun) {
            //getting the targetIndex from total list via separate session.
            getTargetIndex(connection);
            pivotIndex = findPivotIndex(listSize);
            subListPivotIndex = pivotIndex;
            // getting machine name for getting pivotValue
            int combinedLists = lengthMessageList.get(0).getListSize();
            // search in which list the value of the startAllAtOnce should be
            for (int i = 0; i < lengthMessageList.size(); i++) {
                if (pivotIndex <= combinedLists) {
                    searchListForPivot = lengthMessageList.get(i).getId();
                    break;
                } else {
                    // get position of pivotIndex on subList
                    subListPivotIndex = subListPivotIndex - (lengthMessageList.get(i).getListSize());
                    // add next list
                    combinedLists += lengthMessageList.get(i + 1).getListSize();
                }
            }
        }

        boolean medianNotFound = true;

        int medianFoundInCycles = 0;

        while (medianNotFound) {
            medianFoundInCycles++;
            if(firstRun){
                firstRun = false;
            } else {
                pivotIndex = findPivotIndex(listSize);
                subListPivotIndex = getSubListPivotIndex(pivotIndex, category);
            }

            System.out.println("Targeted Index " + targetIndex);
            System.out.println("Total list size is " + listSize);
            System.out.println("subListPivotIndex " + subListPivotIndex);

            smallerThanPivotLength = 0;
            equalsToPivotLength = 0;
            biggerThanPivotLength = 0;
            subListPivotValue = -1;
            smallEqualBiggerList = new ArrayList<>();

            Pivot message = new Pivot(subListPivotIndex);
            ObjectMessage messagePivotTo = session.createObjectMessage(message);
            // Sends index message to correct queue to get value
            switch (searchListForPivot) {
                case VM_1:
                    producer_toQueue1.send(messagePivotTo);
                    break;
                case VM_2:
                    producer_toQueue2.send(messagePivotTo);
                    break;
                case VM_3:
                    producer_toQueue3.send(messagePivotTo);
                    break;
                case VM_4:
                    producer_toQueue4.send(messagePivotTo);
                    break;
                default:
                    throw new Error("Message to get PivotIndexValue could not be send.");
            }

            while(anotherRun) {
                boolean noMessage = true;
                while(noMessage) {
                    //Waits until forever to get the message back with pivotValue, otherwise code cannot compile
                    Message message_fromQueue1 = consumer_fromQueue1.receive(1);
                    Message message_fromQueue2 = consumer_fromQueue2.receive(1);
                    Message message_fromQueue3 = consumer_fromQueue3.receive(1);
                    Message message_fromQueue4 = consumer_fromQueue4.receive(1);


                    if (message_fromQueue1 instanceof ObjectMessage) {
                        ObjectMessage om = (ObjectMessage) message_fromQueue1;
                        processMessage(om, session, producer_toQueue1, producer_toQueue2, producer_toQueue3, producer_toQueue4);
                        noMessage = false;
                    } if (message_fromQueue2 instanceof ObjectMessage) {
                        ObjectMessage om = (ObjectMessage) message_fromQueue2;
                        processMessage(om, session, producer_toQueue1, producer_toQueue2, producer_toQueue3, producer_toQueue4);
                        noMessage = false;
                    } if (message_fromQueue3 instanceof ObjectMessage) {
                        ObjectMessage om = (ObjectMessage) message_fromQueue3;
                        processMessage(om, session, producer_toQueue1, producer_toQueue2, producer_toQueue3, producer_toQueue4);
                        noMessage = false;
                    } if (message_fromQueue4 instanceof ObjectMessage) {
                        ObjectMessage om = (ObjectMessage) message_fromQueue4;
                        processMessage(om, session, producer_toQueue1, producer_toQueue2, producer_toQueue3, producer_toQueue4);
                        noMessage = false;
                    }
                }
            }
            // determine which list is bigger than the targetIndex to decide which listCategory needs to be send with new startAllAtOnce
            String format = "%-40s%s%n";
            System.out.println("----------------------------------------");
            System.out.printf(format, "smallerThanPivotLength, targetIndex: ", smallerThanPivotLength + " > " + targetIndex);
            System.out.printf(format, "smallerThanPivotLength, equalsToPivotLength, targetIndex: ", smallerThanPivotLength + " + " + equalsToPivotLength + " > " + targetIndex);
            System.out.printf(format, "biggerThanPivotLength: ", biggerThanPivotLength);
            System.out.println("----------------------------------------");
            if (smallerThanPivotLength > targetIndex) {
                anotherRun = true;
                category = SMALLER_LIST;
                System.out.printf(format, "category is: ", category);
                listSize = smallerThanPivotLength;
            } else if ((smallerThanPivotLength + equalsToPivotLength) > targetIndex) {
                medianNotFound = false;
                System.out.printf(format, "Total time for finding median: ", (System.currentTimeMillis() - totalTime) + " ms");
                System.out.printf(format, "Median is found in ", medianFoundInCycles + " cycles");
                System.out.printf(format, "Median is ", subListPivotValue);
                //firstRun = true;
                //MedianFindMain.main(null);
            } else {
                anotherRun = true;
                System.out.printf(format, "targetIndex calculation: ", targetIndex + " - " + smallerThanPivotLength + " - " + equalsToPivotLength);
                targetIndex = targetIndex - smallerThanPivotLength - equalsToPivotLength;
                System.out.printf(format, "new target index: ", targetIndex);
                listSize = biggerThanPivotLength;
                category = BIGGER_LIST;
                System.out.printf(format, "category is: ", category);
            }
        }

        // all functions recursive method call 
        connection.close();
    }

    private static void processMessage(ObjectMessage message,
                                       Session session,
                                       MessageProducer producer_toQueue1,
                                       MessageProducer producer_toQueue2,
                                       MessageProducer producer_toQueue3,
                                       MessageProducer producer_toQueue4) throws JMSException {

        var m = message.getObject();
        if (m instanceof Pivot) {
            Pivot p = (Pivot) m;
            subListPivotValue = p.getPivot();

            System.out.println("Pivot Value " + targetIndex);
            System.out.println("total size of lists: " + listSize);

            if (subListPivotValue >= 0) {
                // sending startAllAtOnce to active mq 4x queue
                PivotCategory messageToAll = new PivotCategory(subListPivotValue, category);
                ObjectMessage objectMessageToAll = session.createObjectMessage(messageToAll);

                // Send pivotValue to all VM queue's
                producer_toQueue1.send(objectMessageToAll);
                producer_toQueue2.send(objectMessageToAll);
                producer_toQueue3.send(objectMessageToAll);
                producer_toQueue4.send(objectMessageToAll);

            }
        } else if (m instanceof SmallEqualBiggerLists) {
            SmallEqualBiggerLists sebl = (SmallEqualBiggerLists) m;
            smallEqualBiggerList.add(sebl);
            smallerThanPivotLength = smallerThanPivotLength + sebl.getSmallerThanPivot();
            equalsToPivotLength = equalsToPivotLength + sebl.getEqualsToPivot();
            biggerThanPivotLength = biggerThanPivotLength + sebl.getBiggerThanPivot();

            // keeps getting messages until values are equal and all messages been processed
            if(listSize == (smallerThanPivotLength + equalsToPivotLength + biggerThanPivotLength)) {
                System.out.println("smallerThanPivotLength: " + smallerThanPivotLength);
                System.out.println("equalsToPivotLength: " + equalsToPivotLength);
                System.out.println("biggerThanPivotLength: " + biggerThanPivotLength);
                anotherRun = false;
            }
        } else {
            throw new Error("Unknown object... You're being hacked!");
        }
    }

    private static int findPivotIndex(int totalList) {
        // totalList -1 because list size is measured
        return ThreadLocalRandom.current().nextInt(0, (totalList - 1));
    }

    private static int getSubListPivotIndex(int pivotIndex, String category) {
        if (category == null) {
            throw new Error("No category found.");
        }
        int subPivotIndex = pivotIndex;
        if (category.equals(SMALLER_LIST)) {
            // getting machine name for getting pivotValue
            int combinedLists = smallEqualBiggerList.get(0).getSmallerThanPivot();
            // search in which list the value of the startAllAtOnce should be
            for (int i = 0; i < smallEqualBiggerList.size(); i++) {
                if (pivotIndex <= combinedLists) {
                    searchListForPivot = smallEqualBiggerList.get(i).getId();
                    break;
                } else {
                    // get position of startAllAtOnce on subList
                    subPivotIndex = subPivotIndex - smallEqualBiggerList.get(i).getSmallerThanPivot();
                    // add next list
                    combinedLists += smallEqualBiggerList.get(i + 1).getSmallerThanPivot();
                }
            }
        }
        if (category.equals(BIGGER_LIST)) {
            // getting machine name for getting pivotValue
            int combinedLists = smallEqualBiggerList.get(0).getBiggerThanPivot();
            // search in which list the value of the startAllAtOnce should be
            for (int i = 0; i < smallEqualBiggerList.size(); i++) {
                if (pivotIndex <= combinedLists) {
                    searchListForPivot = smallEqualBiggerList.get(i).getId();
                    break;
                } else {
                    // get position of startAllAtOnce on subList
                    subPivotIndex = subPivotIndex - smallEqualBiggerList.get(i).getBiggerThanPivot();
                    // add next list
                    combinedLists += smallEqualBiggerList.get(i + 1).getBiggerThanPivot();
                }
            }
        }

        return subPivotIndex;
    }

    // Counting total list size and determine the targetIndex where the vm is found
    @NotNull
    public static void getTargetIndex(Connection connection) throws JMSException {
        int messageReceived = 0;
        lengthMessageList = new ArrayList<>();
        //Creating Session on connection. 
        Session session_targetIndex = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        String lengthAllLists = "lengthAllLists";
        //asking activeMQ for length of all lists
        Destination destination_lengthAllLists = session_targetIndex.createQueue(lengthAllLists);
        MessageConsumer consumer = session_targetIndex.createConsumer(destination_lengthAllLists);

        while (messageReceived < 4) {

            Message message = consumer.receive(1000);

            if (message instanceof ObjectMessage) {
                ObjectMessage om = (ObjectMessage) message;
                LengthMessage lm = (LengthMessage) om.getObject();
                listSize = listSize + lm.getListSize();
                lengthMessageList.add(lm);
                messageReceived++;
            }
        }
        // function for finding the target index on all lists together 
        if (listSize % 2 == 1) {
            targetIndex = (listSize - 1) / 2;
        } else {
            targetIndex = listSize / 2;
        }

        session_targetIndex.close();
    }
}
