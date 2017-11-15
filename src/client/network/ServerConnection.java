/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.network;

import common.Definitions;
import common.GameStateSnapshot;
import common.Message;
import common.MessageType;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Griffone
 */
public class ServerConnection {
    
    public static final int MS_CONNECT_TIMEOUT = 30000; // 30 seconds
    public static ServerConnection connection;
    public static NetworkOutputHandler handler;
    
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    
    private boolean connected = false;
    
    public boolean isConnected() {
        return connected;
    }
    
    public static ServerConnection connect(InetSocketAddress address) throws IOException {
        ServerConnection con = new ServerConnection();
        con.connected = false;
        con.socket = new Socket();
        con.socket.connect(address, MS_CONNECT_TIMEOUT);
        con.socket.setSoTimeout(Definitions.MS_TIMEOUT);
        con.connected = true;
        return con;
    }
    
    public void sendMessage(Message message) throws IOException {
        out.writeObject(message);
        out.flush();
        out.reset();
    }
    
    public void disconnect() throws IOException {
        sendMessage(new Message(MessageType.MT_DISCONNECT, null));
    }
    
    public class ConnectionThread implements Runnable {
        
        @Override
        public void run() {
            while (connected) {
                try {
                    Message msg = (Message) in.readObject();
                    if (msg.type == MessageType.MT_ANSWER) {
                        GameStateSnapshot snap = GameStateSnapshot.fromString(msg.string);
                        if (snap != null)
                            handler.onSnapshotReceive(snap);
                    }
                } catch (IOException | ClassNotFoundException ex) {
                    Logger.getLogger(ServerConnection.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    public ConnectionThread getThread() {
        return new ConnectionThread();
    }
}
