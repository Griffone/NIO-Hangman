/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

/**
 * A message object that is sent over the network.
 *
 * @author Griffone
 */
public class Message {
    
    public final MessageType    type;
    public final String         string;
    
    public Message(MessageType type, String string) {
        this.type = type;
        this.string = string;
    }
    
    /*
    /**
     * Transform string to a json object
     * 
     * {"type":"TYPE", "message":"MESSAGE"}
     * 
     * @return json-represented string
    /
    @Override
    public String toString() {
        return '{' + "\"type\":\"" + "\", \"message\":\"" + string + "\"}";
    }
    */
}
