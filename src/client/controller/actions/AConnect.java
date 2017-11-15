/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.controller.actions;

import java.util.function.Consumer;
import client.controller.Controller;
import client.network.ServerConnection;
import java.io.IOException;
import java.net.InetSocketAddress;

/**
 *
 * @author Griffone
 */
public class AConnect implements Consumer<String[]> {

    @Override
    public void accept(String[] params) {
        if (params.length < 2) {
            Controller.outputHandler.print("Please specify an address!");
            return;
        }
        
        try {
            InetSocketAddress address = new InetSocketAddress(params[1], 6942);
            Controller.outputHandler.print("Connecting to " + address.toString());
            ServerConnection.connection = ServerConnection.connect(address);
            Thread lt = new Thread(ServerConnection.connection.getThread());
            Controller.outputHandler.print("Successfully connected!");
            lt.start();
        } catch (IOException ex) {
            Controller.outputHandler.handleError(ex);
        }
    }
    
}
