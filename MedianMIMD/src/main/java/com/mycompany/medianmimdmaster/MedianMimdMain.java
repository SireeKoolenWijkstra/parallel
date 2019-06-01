/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.medianmimdmaster;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 *
 * @author Siree
 */
public class MedianMimdMain {

    private static final String VM_1 = "1";
    private static final String VM_2 = "2";
    private static final String VM_3 = "3";
    private static final String VM_4 = "4";

    private static boolean firstRun = true;
    private static final String URL = "tcp://localhost:61616";
    private static final String TO_QUEUE_1 = "toQueue1";
    private static final String TO_QUEUE_2 = "toQueue2";
    private static final String TO_QUEUE_3 = "toQueue3";
    private static final String TO_QUEUE_4 = "toQueue4";
    private static final String FROM_QUEUE_1 = "fromQueue1";
    private static final String FROM_QUEUE_2 = "fromQueue2";
    private static final String FROM_QUEUE_3 = "fromQueue3";
    private static final String FROM_QUEUE_4 = "fromQueue4";
    static int targetIndex;
    static int pivotIndex;
    static int listSize = 0;
    static ArrayList<LengthMessage> lengthMessageList;
    static ArrayList<SmallEqualBiggerLists> smallEqualBiggerList;
    static final String SMALLER_LIST = "smallerThanPivot";
    static final String BIGGER_LIST = "biggerThanPivot";
    static int subListPivotIndex;
    static String searchListForPivot = "";
    static String category = null;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws JMSException {
        //Setting up the connection. 
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(URL);
        connectionFactory.setTrustAllPackages(true);
        Connection connection = connectionFactory.createConnection();
        connection.start();

        if (firstRun) {
            //getting the targetindex from total list via seperate session.
            getTargetIndex(connection);

            pivotIndex = findPivotIndex(listSize);
            subListPivotIndex = pivotIndex;
            // getting machine name for getting pivotValue
            int combinedLists = lengthMessageList.get(0).getListSize();
            // search in which list the value of the pivot should be
            for (int i = 0; i < lengthMessageList.size(); i++) {
                if (pivotIndex <= combinedLists) {
                    searchListForPivot = lengthMessageList.get(i).getId();
                    break;
                } else {
                    // get position of pivot on subList
                    subListPivotIndex = -combinedLists;
                    // add next list
                    combinedLists
                            += lengthMessageList.get(i + 1).getListSize();
                }
            }
            firstRun = false;
        } else {
            pivotIndex = findPivotIndex(listSize);
            subListPivotIndex = getSubListPivotIndexValue(pivotIndex, category);
        }

        //TODO : only once pivot is choosen by length of total list. 
        // get random pivot index
        //////////////////////////////////////////////////////////////////////////////////////////////
        // Producer: Sending pivot message to correct queue
        /////////////////////////////////////////////////////////////////////////////////////////////
        // connection for getting pivotValue with pivotIndex and right queue and sending requests to all
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        // initiate producer and queue for vm 1     
        Destination destination_toQueue1 = session.createQueue(TO_QUEUE_1);
        MessageProducer producer_toQueue1 = session.createProducer(destination_toQueue1);

        // initiate producer and queue for vm 2     
        Destination destination_toQueue2 = session.createQueue(TO_QUEUE_2);
        MessageProducer producer_toQueue2 = session.createProducer(destination_toQueue2);

        //////////////////////////////////////////////////////////////////////////////////////////////
        // Consumer: Getting pivot message to correct queue
        /////////////////////////////////////////////////////////////////////////////////////////////
        Destination destination_fromQueue1 = session.createQueue(FROM_QUEUE_1);
        Destination destination_fromQueue2 = session.createQueue(FROM_QUEUE_2);

        //creating consumer
        MessageConsumer consumer_fromQueue1 = session.createConsumer(destination_fromQueue1);
        MessageConsumer consumer_fromQueue2 = session.createConsumer(destination_fromQueue2);

        int smallerThanPivotLength = 0;
        int equalsToPivotLength = 0;
        int biggerThanPivotLength = 0;
        int subListPivotIndexValue = -1;

        if (searchListForPivot.equals(VM_1)) {
            Pivot message = new Pivot(subListPivotIndex);
            ObjectMessage messagePivotTo1 = session.createObjectMessage(message);
            producer_toQueue1.send(messagePivotTo1);
            //Waits until forever to get the message back with pivotValue, otherwise code cannot compile
            //TODO find out how to get back here after running through code and cosumer starts listening again
            Message message_fromQueue1 = consumer_fromQueue1.receive();
            if (message_fromQueue1 instanceof ObjectMessage) {
                ObjectMessage om = (ObjectMessage) message_fromQueue1;
                var m = om.getObject();
                if (m instanceof Pivot) {
                    Pivot p = (Pivot) message_fromQueue1;
                    subListPivotIndexValue = p.getPivot();
                    if (subListPivotIndexValue >= 0) {
                        //TODO check f otherside checks for null value first run for category
                        PivotCategory message1 = new PivotCategory(subListPivotIndexValue, category);
                        PivotCategory message2 = new PivotCategory(subListPivotIndexValue, category);

                        ObjectMessage messageTo1 = session.createObjectMessage(message1);
                        ObjectMessage messageTo2 = session.createObjectMessage(message2);

                        producer_toQueue1.send(messageTo1);
                        producer_toQueue2.send(messageTo2);

                    }
                } else if (m instanceof SmallEqualBiggerLists) {
                    SmallEqualBiggerLists sebl = (SmallEqualBiggerLists) message_fromQueue1;
                    smallEqualBiggerList.add(sebl);
                    smallerThanPivotLength = +sebl.getSmallerThanPivot();
                    equalsToPivotLength = +sebl.getEqualsToPivot();
                    biggerThanPivotLength = +sebl.getBiggerThanPivot();
                } else {
                    throw new Error("Unknown object... You're being hacked!");
                }
            }
            
        }
        

        // sending pivot to active mq 4x queue
        //TODO : lists clear after sending new value
        // determine which list is bigger than the targetIndex to decide which listCategory needs to be send with new pivot
        if (smallerThanPivotLength > targetIndex) {
            category = SMALLER_LIST;
            listSize = smallerThanPivotLength;
        } else if ((smallerThanPivotLength + equalsToPivotLength) > targetIndex) {
            System.out.println("Median is " + subListPivotIndexValue);
        } else {
            targetIndex = targetIndex - smallerThanPivotLength - equalsToPivotLength;
            listSize = biggerThanPivotLength;
            category = BIGGER_LIST;
        }

        // all functions recursive method call 
        connection.close();
    }

    private static int findPivotIndex(int totalList) {
        return ThreadLocalRandom.current().nextInt(0, totalList);
    }

    private static int getSubListPivotIndexValue(int pivotIndex, String category) {
        if (category == null) {
            throw new Error("No category found.");
        }
        if (category == SMALLER_LIST) {
            // getting machine name for getting pivotValue
            int combinedLists = smallEqualBiggerList.get(0).getSmallerThanPivot();
            // search in which list the value of the pivot should be
            for (int i = 0; i < smallEqualBiggerList.size(); i++) {
                if (pivotIndex <= combinedLists) {
                    searchListForPivot = smallEqualBiggerList.get(i).getId();
                    break;
                } else {
                    // get position of pivot on subList
                    pivotIndex = -combinedLists;
                    // add next list
                    combinedLists
                            += smallEqualBiggerList.get(i + 1).getSmallerThanPivot();
                }
            }
        }
        if (category == BIGGER_LIST) {
            // getting machine name for getting pivotValue
            int combinedLists = smallEqualBiggerList.get(0).getBiggerThanPivot();
            // search in which list the value of the pivot should be
            for (int i = 0; i < smallEqualBiggerList.size(); i++) {
                if (pivotIndex <= combinedLists) {
                    searchListForPivot = smallEqualBiggerList.get(i).getId();
                    break;
                } else {
                    // get position of pivot on subList
                    pivotIndex = -combinedLists;
                    // add next list
                    combinedLists
                            += smallEqualBiggerList.get(i + 1).getBiggerThanPivot();
                }
            }
        }

        return pivotIndex;
    }

    public static void getTargetIndex(Connection connection) throws JMSException {
        int messageReceived = 0;
        String lengthAllLists = "lengthAllLists";
        lengthMessageList = new ArrayList();
        //Creating Session on connection. 
        Session session_targetIndex = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        //asking activeMQ for length of all lists
        Destination destination_lengthAllLists = session_targetIndex.createQueue(lengthAllLists);
        MessageConsumer consumer = session_targetIndex.createConsumer(destination_lengthAllLists);
        while (messageReceived < 1) {

            Message message = consumer.receive(1000);

            if (message instanceof ObjectMessage) {
                ObjectMessage om = (ObjectMessage) message;
                LengthMessage lm = new LengthMessage();
                lm = (LengthMessage) om.getObject();
                listSize = +lm.getListSize();
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
