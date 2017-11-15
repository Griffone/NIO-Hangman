/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

/**
 * Possible message types for Networking
 * 
 * @author Griffone
 */
public enum MessageType {
    
    MT_CONNECT,         // New connection message clinet->server
    MT_GUESS_LETTER,    // New guess (single letter) clinet->server
    MT_GUESS_WORD,      // New guess (full word) clinet->server
    MT_DISCONNECT,      // Client is dropping connection client->server
    MT_ANSWER;          // Full answer server->client contains: 1) guessed word, 2) remaining lives and 3) current score
}
