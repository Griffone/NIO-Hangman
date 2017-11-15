/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.network;

import common.GameStateSnapshot;

/**
 *
 * @author Griffone
 */
public interface NetworkOutputHandler {
    
    public void onSnapshotReceive(GameStateSnapshot snapshot);
}
