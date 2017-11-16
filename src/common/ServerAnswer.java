/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import java.io.Serializable;

/**
 *
 * @author thegr
 */
public class ServerAnswer implements Serializable {
    
    public final String optional;
    public final GameStateSnapshot snapshot;
    
    public ServerAnswer(GameStateSnapshot snapshot, String optional) {
        this.snapshot = snapshot;
        this.optional = optional;
    }
}
