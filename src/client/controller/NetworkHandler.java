/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.controller;

import client.network.NetworkOutputHandler;
import common.ServerAnswer;

/**
 *
 * @author Griffone
 */
public class NetworkHandler implements NetworkOutputHandler {

    @Override
    public void onAnswerReceive(ServerAnswer answer) {
        Controller.outputHandler.drawServerAnswer(answer);
    }
    
}
