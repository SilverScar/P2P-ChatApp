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
import java.net.*;
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
public class MessageSender extends Thread{
    
    String message, hostname;
    int port;
    
    public MessageSender(String message, String hostname, int port){
        this.message = message;
        this.hostname = hostname;
        this.port = port;
    }
    
    @Override
    public void run(){
        try {
            Socket socket = new Socket(hostname,port);
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            out.println(" "+message);
            out.flush();
            //socket.getOutputStream().write(this.message.getBytes());
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(MessageSender.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
