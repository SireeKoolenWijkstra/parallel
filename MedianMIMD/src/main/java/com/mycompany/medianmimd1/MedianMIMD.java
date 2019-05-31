/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.medianmimd1;

import java.io.IOException;
import java.util.ArrayList;
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
public class MedianMIMD {

    private static String url = "tcp://localhost:61616";
    private static String fromQueue = "Queue1.1";
    private static String toQueue = "Queue1.2";
    private static int pivotValue;
    private static String listCategory;
    static boolean firstRun = true;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, JMSException {

        //total list of one csv is created.
        ArrayList<Integer> list;
        ReadCsv csv = new ReadCsv();
        list = csv.readFile();
        //create new object instance of medianFinder        
        MedianFinderMIMD mf = new MedianFinderMIMD();

        Integer[] lengthList = new Integer[3];

        //Setting up the connection. 
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
        Connection connection = connectionFactory.createConnection();
        connection.start();

        //Creating Session on connection. 
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination destination_fromQueue = session.createQueue(fromQueue);

        //creating consumer
        MessageConsumer consumer = session.createConsumer(destination_fromQueue);
        Message message = consumer.receive();

        //Will if-statement ever not be used? Casting should not be necessary.
        if (message instanceof ObjectMessage) {
            ObjectMessage om = (ObjectMessage) message;
            PivotCategory pc = (PivotCategory) om.getObject();
            pivotValue = pc.getPivotValue();
            listCategory = pc.getListCategory();
        }
        if (firstRun) {
            //message that need to be returned by producer to Queue2
            lengthList = mf.findMedianMIMD(pivotValue, list, null);

            firstRun = false;
        } else {
            //message that need to be returned by producer to Queue2
            lengthList = mf.findMedianMIMD(pivotValue, null, listCategory);
        }

        //creating an other queue on 
        Destination destination_toQueue = session.createQueue(toQueue);
        MessageProducer producer = session.createProducer(destination_toQueue);
        ObjectMessage messageTo = session.createObjectMessage(lengthList);
        producer.send(messageTo);
        
        connection.close();

    }

}
