/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2pchatapp;

/**
 *
 * @author aditya
 */
import java.util.*;
import java.net.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
public class MessageListener extends Thread {
    ServerSocket server;
    int port;
    WritableGUI gui;
    public MessageListener(WritableGUI gui, int port){
        this.port = port;
        this.gui = gui;
        try {
            server = new ServerSocket(port);
        } catch (IOException ex) {
            Logger.getLogger(MessageListener.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    @Override
    public void run(){
        Socket clientSocket;
        try {
            while((clientSocket = server.accept()) != null){
                //System.out.println("Client connected from : "+clientSocket.getLocalAddress().getHostName());
                InputStream in = clientSocket.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                String msg = br.readLine();
                if(msg!=null){
                    gui.write(msg);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(MessageListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
