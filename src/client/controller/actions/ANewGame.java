/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.controller.actions;

import client.controller.Controller;
import client.network.ServerConnection;
import common.Message;
import common.MessageType;
import java.io.IOException;
import java.util.function.Consumer;

/**
 *
 * @author thegr
 */
public class ANewGame implements Consumer<String[]> {

    @Override
    public void accept(String[] params) {
        if (ServerConnection.connection == null) {
            Controller.outputHandler.print("Please connect first!");
            return;
        }
        
        try {
            Controller.outputHandler.print("Starting a new game");
            ServerConnection.connection.sendMessage(new Message(MessageType.MT_NEW_GAME, null));
        } catch (IOException ex) {
            Controller.outputHandler.handleError(ex);
        }
    }
    
}
