/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.network;

import common.Definitions;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The highest-level part of the whole server.
 *
 * @author Griffone
 */
public class Server {
    
    static ServerSocket socket;
    
    public static void inititalize() throws IOException {
        socket = new ServerSocket(Definitions.PORT);
    }
    
    public static void run() {
        while (true) {
            try {
                Socket clientSock = socket.accept();
                System.out.println("New connection");
                Thread t = new Thread(new ClientHandlerThread(clientSock));
                t.start();
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public static String getServerAddress() {
        return socket.getInetAddress().getHostAddress();
    }
}
