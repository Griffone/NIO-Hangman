/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.startup;

import client.controller.NetworkHandler;
import client.controller.Controller;
import client.network.ServerConnection;
import client.view.OutputHandler;
import client.view.InputThread;

/**
 *
 * @author Griffone
 */
public class Main {
    
    public static void main(String[] args) {
        // Create upward dependency-hack
        ServerConnection.handler = new NetworkHandler();
        Controller.outputHandler = new OutputHandler();
        
        // Create servicing thread, one could start several, but there are no real benefits to it
        Controller.createServiceThread().start();
        
        // Finally create listening thread for the client input
        new Thread(new InputThread()).start();
    }
}
