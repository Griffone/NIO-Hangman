/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.controller;

import common.GameStateSnapshot;

/**
 *
 * @author Griffone
 */
public interface ControllerOutputHandler {
    
    public void handleError(Exception error);
    public void drawGameState(GameStateSnapshot snapshot);
    public void print(String line);
    
}
