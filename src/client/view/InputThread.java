/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.view;

import client.controller.Controller;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author Griffone
 */
public class InputThread implements Runnable {

    private final BufferedReader reader;
    
    public InputThread() {
        reader = new BufferedReader(new InputStreamReader(System.in));
    }
    
    public static void parseCommand(String line) {
        String[] words = line.split(" ");
        if (words[0].compareToIgnoreCase("connect") == 0)
            Controller.connect(words);
        else if (words[0].compareToIgnoreCase("disconnect") == 0 || words[0].compareToIgnoreCase("quit") == 0 || words[0].compareToIgnoreCase("exit") == 0)
            Controller.disconnect();
        else if (words[0].compareToIgnoreCase("guess") == 0) {
            if (words.length < 2)
                System.out.println("Please enter your guess");
            else
                Controller.guess(words[1]);
        } else if (words[0].compareToIgnoreCase("start") == 0)
            Controller.startNewGame();
        else
            System.out.println("Unknown command!");
    }
    
    @Override
    public void run() {
        while (!Controller.shouldEnd) {
            try {
                parseCommand(reader.readLine());
            } catch (IOException ex) {
                Controller.outputHandler.handleError(ex);
            }
        }
    }
    
}
