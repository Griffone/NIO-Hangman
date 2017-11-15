/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.controller;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Griffone
 */
public class ServiceThread implements Runnable {

    private static boolean all_enabled = false;
    
    private boolean enabled = true;
    
    @Override
    public void run() {
        enabled = true;
        all_enabled = true;
        while (enabled && all_enabled) {
            try {
                Action action = Controller.QUEUE.take();
                action.consumer.accept(action.argument);
            } catch (InterruptedException ex) {
                Logger.getLogger(ServiceThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void stop() {
        enabled = false;
    }
    
    public static void stopAll() {
        all_enabled = true;
    }
}
