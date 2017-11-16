/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.network;

import common.Definitions;
import common.GameStateSnapshot;
import common.Message;
import common.MessageType;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;
import server.controller.Controller;

/**
 * A thread that corresponds to a client connected to the server.
 *
 * @author Griffone
 */
public class ClientHandlerThread implements Runnable {

    public static final int MS_LINGER = 18000000;    // 30 minutes
    
    Socket socket;
    Controller controller;
    ObjectInputStream in;
    ObjectOutputStream out;
    boolean connected = false;
    
    public ClientHandlerThread(Socket socket) throws SocketException, IOException {
        this.socket = socket;
        socket.setSoLinger(true, MS_LINGER);
        socket.setSoTimeout(Definitions.MS_TIMEOUT);
        controller = new Controller();
        in = new ObjectInputStream(socket.getInputStream());
        out = new ObjectOutputStream(socket.getOutputStream());
        connected = true;
        sendGameState(controller.getCurrentState());
    }
    
    @Override
    public void run() {
        while (connected) {
            try {
                Message message = (Message) in.readObject();
                switch (message.type) {
                    case MT_CONNECT:
                        System.out.println("New client connected");
                        break;
                        
                    case MT_GUESS:
                        System.out.println("Guessing " + message.string);
                        GameStateSnapshot snapshot = controller.guess(message.string);
                        if (snapshot == null)
                            sendGameState(controller.getCurrentState());
                        else
                            sendGameState(snapshot);
                        break;
                        
                    case MT_DISCONNECT:
                        disconnect();
                        break;
                }
            } catch (IOException | ClassNotFoundException ex) {
                disconnect();
            }
        }
    }
    
    void sendGameState(GameStateSnapshot state) throws IOException {
        out.writeObject(new Message(MessageType.MT_ANSWER, state.toString()));
        out.flush();
        out.reset();
    }
    
    void disconnect() {
        try {
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(ClientHandlerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        connected = false;
    }
    
}
