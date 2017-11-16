/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.controller;

import client.controller.actions.*;
import client.network.ServerConnection;
import java.io.IOException;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * A middle layer between view and network layers.
 * 
 * This layer is responsible for the multithreading of the processes and provides non-blocking calls.
 *
 * @author Griffone
 */
public class Controller {
    
    public static ControllerOutputHandler outputHandler;
    public static final BlockingQueue<Action<String[]>> QUEUE = new LinkedBlockingQueue();
    
    public static boolean shouldEnd = false;
    
    public static Thread createServiceThread() {
        return new Thread(new ServiceThread());
    }
    
    public static void connect(String[] params) {
        QUEUE.offer(new Action(new AConnect(), params));
    }
    
    /**
     * The only blocking call.
     * Will disconnect from the server.
     */
    public static void disconnect() {
        if (ServerConnection.connection != null)
            try {
                ServerConnection.connection.disconnect();
            } catch (IOException ex) {
                outputHandler.handleError(ex);
            }
        shouldEnd = true;
    }
    
    public static void guess(String string) {
        String[] params = new String[1];
        params[0] = string;
        QUEUE.offer(new Action(new AGuess(), params));
    }
    
    public static void startNewGame() {
        QUEUE.offer(new Action(new ANewGame(), null));
    }
}
