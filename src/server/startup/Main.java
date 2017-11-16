/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.startup;

import java.io.IOException;
import server.model.Game;
import server.network.Server;

/**
 *
 * @author Griffone
 */
public class Main {
    
    public static void main(String[] args) throws IOException {
        // Read words.txt
        Game.initializeDictionary("C:\\Studies\\Network Programming\\HW1\\src\\server\\model\\words.txt");
        Server.inititalize();
        System.out.print("Initialized server on ");
        System.out.println(Server.getServerAddress());
        Server.run();
    }
    
}
