/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.model;

import common.GameStateSnapshot;
import common.ServerAnswer;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * An instance of a game for a single player.
 *
 * @author Griffone
 */
public class Game {
    
    private static final ArrayList<String> DICTIONARY = new ArrayList();
    private static final Random RNG = new Random();
    
    private String guess, word;
    private int points, lives;
    
    private final boolean wrong_guesses[] = new boolean[26];
    
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
        return (word != null && word.compareTo(guess) != 0 && lives > 0);
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
        for (int i = 0; i < wrong_guesses.length; i++)
            wrong_guesses[i] = false;
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
    
    private GameStateSnapshot getSnapshot() {
        return new GameStateSnapshot(points, toFullForm(guess), lives);
    }
    
    public ServerAnswer makeAGuess(String guess) {
        if (!isInValidState())
            newRound();
        
        if (!isALegalGuess(guess))
            System.out.println("Illegal guess!");
        
        if (!isALegalGuess(guess))
            return new ServerAnswer(getSnapshot(), "Illegal guess!");
        
        guess = guess.toUpperCase();
        
        if (guess.length() == 1) {
            char g = guess.charAt(0);
            if (word.contains(guess)) {
                // Check for a repeat guess
                if (this.guess.contains(guess))
                    return new ServerAnswer(getSnapshot(), "Already guessed letter");
                
                // Replace '_' with correctly guessed character
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < word.length(); i++) {
                    if (word.charAt(i) == g)
                        sb.append(g);
                    else
                        sb.append(this.guess.charAt(i));
                }
                this.guess = sb.toString();
                return new ServerAnswer(getSnapshot(), null);
            } else {
                // Check for a repeat guess
                if (wrong_guesses[g - 'A'])
                    return new ServerAnswer(getSnapshot(), "Already guessed letter");
                // Reduce lives counter!
                wrong_guesses[g - 'A'] = true;
                if (--lives <= 0)
                    newRound();
                return new ServerAnswer(getSnapshot(), "Miss!");
            }
        } else {
            if (word.compareTo(guess) == 0) {
                this.guess = guess; // this will update the points
                newRound();
            }
            return new ServerAnswer(getSnapshot(), null);
        }
    }
    
}
