/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.controller.actions;

import java.util.function.Consumer;
import client.controller.Controller;
import client.network.ServerConnection;
import common.Definitions;
import common.Message;
import common.MessageType;
import java.io.IOException;

/**
 *
 * @author Griffone
 */
public class AGuessLetter implements Consumer<String[]> {

    
    @Override
    public void accept(String[] params) {
        if (ServerConnection.connection == null) {
            Controller.outputHandler.print("Please connect first!");
            return;
        }
        
        if (params[0].length() != 1) {
            Controller.outputHandler.handleError(new IllegalArgumentException("Illegal amount of letters!"));
            return;
        }
        
        if (!Definitions.LEGAL_CHARS.contains(params[0])) {
            Controller.outputHandler.print("Illegal character detected (" + params[0] + ")!");
            return;
        }
        
        try {
            Controller.outputHandler.print("Guessing '" + params[0] + '\'');
            ServerConnection.connection.sendMessage(new Message(MessageType.MT_GUESS_LETTER, params[0]));
        } catch (IOException ex) {
            Controller.outputHandler.handleError(ex);
        }
    }
    
}
