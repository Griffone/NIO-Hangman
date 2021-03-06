/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.controller;

import common.GameStateSnapshot;
import common.ServerAnswer;
import server.model.Game;

/**
 *
 * @author Griffone
 */
public class Controller {
    
    private final Game game;
    
    public Controller() {
        game = new Game();
    }
    
    public GameStateSnapshot getCurrentState() {
        return game.getCurrentState();
    }
    
    /**
     * Make a guess
     * 
     * @param guess
     * @return null if the guess is illegal or the new game state
     */
    public ServerAnswer guess(String guess) {
        return game.makeAGuess(guess);
    }
}
