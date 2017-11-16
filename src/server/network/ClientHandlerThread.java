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
import common.ServerAnswer;
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
        System.out.print("Creating thred on ");
        System.out.println(this.socket);
        socket.setSoLinger(true, MS_LINGER);
        socket.setSoTimeout(Definitions.MS_TIMEOUT);
        controller = new Controller();
        System.out.println("Something immature and explicit");
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
        System.out.println("Something immature and explicit");
        connected = true;
    }
    
    @Override
    public void run() {
        while (connected) {
            try {
                Message message = (Message) in.readObject();
                System.out.println(message);
                switch (message.type) {
                    case MT_CONNECT:
                        System.out.println("New client connected");
                        sendAnswer(new ServerAnswer(controller.getCurrentState(), "New game!"));
                        break;
                        
                    case MT_NEW_GAME:
                        System.out.println("Starting new game");
                        controller = new Controller();
                        sendAnswer(new ServerAnswer(controller.getCurrentState(), "New game!"));
                        break;
                        
                    case MT_GUESS:
                        String guess = (String) message.payload;
                        sendAnswer(controller.guess(guess));
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
    
    void sendAnswer(ServerAnswer answer) throws IOException {
        out.writeObject(new Message(MessageType.MT_ANSWER, answer));
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
