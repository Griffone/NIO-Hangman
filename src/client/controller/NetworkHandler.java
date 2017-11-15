/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.controller;

import client.network.NetworkOutputHandler;
import common.GameStateSnapshot;

/**
 *
 * @author Griffone
 */
public class NetworkHandler implements NetworkOutputHandler {

    @Override
    public void onSnapshotReceive(GameStateSnapshot snapshot) {
        Controller.outputHandler.drawGameState(snapshot);
    }
    
}
