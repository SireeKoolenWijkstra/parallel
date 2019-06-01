/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.medianmimd1;

import com.mycompany.medianmimdmaster.*;
import java.io.IOException;
import java.util.ArrayList;
import javax.jms.Connection;
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

    private static final String URL = "tcp://localhost:61616";
    private static final String TO_LENGTH_LIST_QUEUE_1 = "lengthAllLists";
    private static final String TO_QUEUE_1 = "fromQueue1";
    private static final String FROM_QUEUE_1 = "toQueue1";
    private static final String VM_1 = "1";

    private static int pivotValue;
    private static String listCategory;
    static boolean firstRun = true;
    static int pivotIndex;

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


        //Setting up the connection. 
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(URL);
        connectionFactory.setTrustAllPackages(true);
        Connection connection = connectionFactory.createConnection();
        connection.start();
       
        //Creating Session on connection. 
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        //creating an other queue on 
        Destination destination_toLengthListQueue = session.createQueue(TO_LENGTH_LIST_QUEUE_1);
        Destination destination_toQueue = session.createQueue(TO_QUEUE_1);
        Destination destination_fromQueue = session.createQueue(FROM_QUEUE_1);

        // creating producer and consumer
        MessageProducer producer = session.createProducer(destination_toQueue);
        MessageProducer producer_toLengthList = session.createProducer(destination_toLengthListQueue);
        MessageConsumer consumer = session.createConsumer(destination_fromQueue);

        if (firstRun) {
            LengthMessage lm = new LengthMessage(VM_1, list.size());
            // send message about totalListSize
            ObjectMessage LengthMessageTo = session.createObjectMessage(lm);
            producer_toLengthList.send(LengthMessageTo);
        }

        // let consumer receive messages
        Message message = consumer.receive();

        //Will if-statement ever not be used? Casting should not be necessary.
        if (message instanceof ObjectMessage) {
            ObjectMessage om = (ObjectMessage) message;
            var m = om.getObject();
            if (m instanceof Pivot) {
                Pivot pi = (Pivot) om.getObject();
                pivotIndex = pi.getPivot();
                Pivot pivotIndexValue = new Pivot(list.get(pivotIndex));
                // send message about totalListSize
                ObjectMessage pivotIndexValueTo = session.createObjectMessage(pivotIndexValue);
                producer.send(pivotIndexValueTo);
            }
            else if (m instanceof PivotCategory) {
                PivotCategory pc = (PivotCategory) om.getObject();
                pivotValue = pc.getPivotValue();
                listCategory = pc.getListCategory();
            } else {
                throw new Error("object unknown");
            }
        }
        
        SmallEqualBiggerLists lengthList;
        if (firstRun) {
            //message that need to be returned by producer to Queue2
            lengthList = mf.findMedianMIMD(pivotValue, list, null);

            firstRun = false;
        } else {
            //message that need to be returned by producer to Queue2
            lengthList = mf.findMedianMIMD(pivotValue, null, listCategory);
        }

        lengthList.setId(VM_1);
        ObjectMessage messageTo = session.createObjectMessage(lengthList);
        producer.send(messageTo);

        connection.close();

    }

}
