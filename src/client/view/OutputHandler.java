/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.view;

import client.controller.ControllerOutputHandler;
import common.GameStateSnapshot;
import common.ServerAnswer;

/**
 *
 * @author Griffone
 */
public class OutputHandler implements ControllerOutputHandler {

    @Override
    public void handleError(Exception error) {
        System.err.print("Error! ");
        System.err.println(error);
    }
    
    public void drawGameState(GameStateSnapshot snapshot) {
        System.out.print("Score: ");
        System.out.println(snapshot.points);
        System.out.print("Remaining lives: ");
        System.out.println(snapshot.remainingLives);
        System.out.println(snapshot.word);
        System.out.println();
    }

    @Override
    public void print(String line) {
        System.out.println(line);
    }

    @Override
    public void drawServerAnswer(ServerAnswer answer) {
        System.out.println();
        if (answer.optional != null)
            print(answer.optional);
        if (answer.snapshot != null)
            drawGameState(answer.snapshot);
    }

}
