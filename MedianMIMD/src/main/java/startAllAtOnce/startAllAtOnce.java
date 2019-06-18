/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package startAllAtOnce;

import com.mycompany.activemqmaster.MedianFindMain;
import com.mycompany.vm.activemq1.Median1;
import com.mycompany.vm.activemq2.Median2;
import com.mycompany.vm.activemq3.Median3;
import com.mycompany.vm.activemq4.Median4;

import javax.jms.JMSException;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author Tamara
 */
public class startAllAtOnce {


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        ExecutorService executorService = Executors.newCachedThreadPool();

        try {
            executorService.execute(
                    () -> {
                        try {
                            MedianFindMain.main(args);
                        } catch (IOException | JMSException e) {
                            e.printStackTrace();
                        }
                    }
            );
            executorService.execute(
                    () -> {
                        try {
                            Median1.main(args);
                        } catch (IOException | JMSException e) {
                            e.printStackTrace();
                        }
                    }
            );
            executorService.execute(
                    () -> {
                        try {
                            Median2.main(args);
                        } catch (IOException | JMSException e) {
                            e.printStackTrace();
                        }
                    }
            );
            executorService.execute(
                    () -> {
                        try {
                            Median3.main(args);
                        } catch (IOException | JMSException e) {
                            e.printStackTrace();
                        }
                    }
            );
            executorService.execute(
                    () -> {
                        try {
                            Median4.main(args);
                        } catch (IOException | JMSException e) {
                            e.printStackTrace();
                        }
                    }
            );
        } finally {

            System.out.println("Start all at once in: " + (System.currentTimeMillis() - start) + " ms");
            executorService.shutdown();
        }


    }
}
