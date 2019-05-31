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
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 *
 * @author Siree
 */
public class MedianMimdMain {

    private static String url = "tcp://localhost:61616";
    private static String fromQueue = "Queue2.1";
    static int targetIndex;
    static int pivotIndex;
    static int totalListSize = 0;
    static ArrayList<LengthMessage> lengthMessageList;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws JMSException {
        //Setting up the connection. 
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
        Connection connection = connectionFactory.createConnection();
        connection.start();

        //getting the targetindex from total list via seperate session.
        getTargetIndex(connection);

        // get random pivot index
        pivotIndex = findPivotIndex(totalListSize);
        int subListPivotIndex = pivotIndex;
        // getting machine name for getting pivotValue
        String searchListForPivot = "";
        int combinedLists = lengthMessageList.get(0).getListSize();
        // search in which list the value of the pivot should be
        for (int i = 0; i < lengthMessageList.size(); i++) {
            if (pivotIndex <= combinedLists) {
                searchListForPivot = lengthMessageList.get(i).getVmName();
                break;
            } else {
                // get position of pivot on subList
                subListPivotIndex = -combinedLists;
                // add next list
                combinedLists
                        += lengthMessageList.get(i + 1).getListSize();
            }
        }

        // connection for getting pivotValue with pivotIndex and right queue
        // ask for value of pivot 
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Destination destination = session.createQueue(fromQueue);

        // initiate producer
        MessageProducer producer = session.createProducer(destination);

        //create message with pivot and listCategory
        ObjectMessage messageTo = session.createObjectMessage();
        producer.send(messageTo);

        // asking activeMQ for indexValue of the targetedIndex 
        //TODO
        connection.close();
    }

    private static int findPivotIndex(int totalList) {
        return ThreadLocalRandom.current().nextInt(0, totalList);
    }

    public static void getTargetIndex(Connection connection) throws JMSException {
        int messageReceived = 0;
        String lengthAllLists = "lengthAllLists";

        //Creating Session on connection. 
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        //asking activeMQ for length of all lists
        Destination destination_lengthAllLists = session.createQueue(lengthAllLists);
        MessageConsumer consumer = session.createConsumer(destination_lengthAllLists);
        while (messageReceived < 4) {

            Message message = consumer.receive();

            if (message instanceof ObjectMessage) {
                ObjectMessage om = (ObjectMessage) message;
                LengthMessage lm = new LengthMessage();
                lm = (LengthMessage) om.getObject();
                totalListSize = +lm.getListSize();
                lengthMessageList.add(lm);
                messageReceived++;
            }
        }

        // function for finding the target index on all lists together 
        if (totalListSize % 2 == 1) {
            targetIndex = (totalListSize - 1) / 2;
        } else {
            targetIndex = totalListSize / 2;
        }

        session.close();
    }
}
