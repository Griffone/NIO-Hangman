/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.model;

import common.GameStateSnapshot;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * An instance of a game for a sinlge player.
 *
 * @author Griffone
 */
public class Game {
    
    private static final ArrayList<String> DICTIONARY = new ArrayList();
    private static final Random RNG = new Random();
    
    private String guess, word;
    private int points, lives;
    
    public static void initializeDictionary(String dictName) throws FileNotFoundException, IOException {
        
        File file = new File(dictName);
        if (!file.exists() || !file.canRead())
            throw new FileNotFoundException(dictName);
        
        FileReader fileReader = new FileReader(file);
        BufferedReader in = new BufferedReader(fileReader);
        
        String line;
        while ((line = in.readLine()) != null)
            DICTIONARY.add(line.toUpperCase());
        
        fileReader.close();
        DICTIONARY.trimToSize();
    }
    
    public Game() {
        lives = points = 0;
        guess = word = null;
    }
    
    private boolean isInValidState() {
        return (word != null && word.compareTo(guess) == 0 && lives > 0);
    }
    
    private void newRound() {
        if (word != null)
            if (word.compareTo(guess) == 0)
                points++;
            else
                points--;

        word = DICTIONARY.get(RNG.nextInt(DICTIONARY.size()));
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < word.length(); i++)
            sb.append('_');
        guess = sb.toString();
        lives = word.length();
    }
    
    /**
     * Transform a word into W O R D
     * 
     * @param item
     * @return 
     */
    private static String toFullForm(String item) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < item.length(); i++) {
            sb.append(item.charAt(i));
            if (i + 1 != item.length())
                sb.append(' ');
        }
        return sb.toString();
    }
    
    public GameStateSnapshot getCurrentState() {
        if (!isInValidState())
            newRound();
        return new GameStateSnapshot(points, toFullForm(guess), lives);
    }
    
    public boolean isALegalGuess(String guess) {
        return guess.length() == 1 || guess.length() == this.word.length();
    }
    
    public GameStateSnapshot makeAGuess(String guess) {
        if (!isALegalGuess(guess))
            return null;
        
        if (!isInValidState())
            newRound();
        
        guess = guess.toUpperCase();
        
        if (guess.length() == 1) {
            if (word.contains(guess)) {
                // Check for a repeat guess
                if (this.guess.contains(guess))
                    return new GameStateSnapshot(points, toFullForm(this.guess), lives);
                
                // Replace '_' with correctly guessed character
                char g = guess.charAt(0);
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < word.length(); i++) {
                    if (word.charAt(i) == g)
                        sb.append(g);
                    else
                        sb.append(this.guess.charAt(i));
                }
                this.guess = sb.toString();
                return new GameStateSnapshot(points, toFullForm(this.guess), lives);
            } else {
                // Reduce lives counter!
                if (--lives <= 0)
                    newRound();
                return new GameStateSnapshot(points, toFullForm(this.guess), lives);
            }
        } else {
            if (word.compareTo(guess) == 0) {
                this.guess = guess; // this will update the points
                newRound();
            }
            return new GameStateSnapshot(points, toFullForm(this.guess), lives);
        }
    }
    
}
