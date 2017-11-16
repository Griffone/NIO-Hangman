/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.controller.actions;

import client.controller.Controller;
import client.network.ServerConnection;
import common.Definitions;
import common.Message;
import common.MessageType;
import java.io.IOException;
import java.util.function.Consumer;

/**
 *
 * @author Griffone
 */
public class AGuess implements Consumer<String[]> {

    @Override
    public void accept(String[] params) {
        if (ServerConnection.connection == null) {
            Controller.outputHandler.print("Please connect first!");
            return;
        }
        
        char[] chars = new char[1];
        for (int i = 0; i < params[0].length(); i++) {
            chars[0] = params[0].charAt(i);
            if (!Definitions.LEGAL_CHARS.contains(new String(chars))) {
                Controller.outputHandler.print("Illegal character (" + params[0].charAt(i) + ")!");
                return;
            }
        }
        
        try {
            Controller.outputHandler.print("Guessing '" + params[0] + '\'');
            ServerConnection.connection.sendMessage(new Message(MessageType.MT_GUESS, params[0]));
        } catch (IOException ex) {
            Controller.outputHandler.handleError(ex);
        }
    }
    
}
