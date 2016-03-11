/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2pchatapp;

import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.util.*;
import javax.swing.JOptionPane;
/**
 *
 * @author aditya
 */
public class Server {

    static Connection connection;
    static Statement statement;
    
    public void Server(){
        
    }
    
    public static void main(String[] args) {
        try{
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/chatclientserver?zeroDateTimeBehavior=convertToNull","root","");
            statement = connection.createStatement();
            final int PORT = 444;
            ServerSocket server_socket = new ServerSocket(PORT);
            System.out.println("Auhorization Server Started");
            
            while(true){
                Socket socket = server_socket.accept();
                Scanner IN = new Scanner(socket.getInputStream());
                PrintWriter OUT = new PrintWriter(socket.getOutputStream());
                String flag = IN.nextLine();
                String username = IN.nextLine();
                String password;
                switch (flag) {
                    case "R":
                        password = IN.nextLine();
                        if(checkUsername(username)){
                            if(registerClient(username, password)){
                                OUT.println("**");
                                OUT.flush();    
                            }
                            else{
                                OUT.println("*");
                                OUT.flush();    
                            }
                        }
                        else{
                            OUT.println("*");
                            OUT.flush();
                        }
                        break;
                    case "L":
                        password = IN.nextLine();
                        String port = IN.nextLine();
                        if(checkClient(username,password)){
                            boolean success = loginUser(username, socket.getInetAddress().getHostAddress(), port);
                            if(success){
                                OUT.println("**");
                            }
                            else
                                OUT.println("*");
                        }
                        else
                            OUT.println("*");
                        OUT.flush();
                        break;
                    case "X":
                        logoutUser(username);
                        break;
                    case "Y":
                        if(exitGroup(username)){
                            ArrayList<String> end_group = new ArrayList<>();
                            getGroup(username, end_group);
                            for(String group1 : end_group) {
                                OUT.println(group1);
                            }
                            OUT.flush();
                        }
                        break;
                    case "F":
                        ArrayList<String> friend_list = new ArrayList<>();
                        populateFriendList(username, friend_list);
                        //System.out.println(friend_list);
                        for (String friend_list1 : friend_list) {
                            OUT.println(friend_list1);
                        }
                        OUT.flush();
                        break;
                    case "A":
                        String friend_name = IN.nextLine();
                        if(!checkUsername(friend_name)){
                            if(!checkFriend(username, friend_name)){
                                if(updateFriendList(username, friend_name))
                                    OUT.println("**");
                            }
                            else
                                OUT.println("***");
                        }
                        else
                            OUT.println("*");
                        OUT.flush();
                        break;
                    case "S":
                        ArrayList<String> user_list = new ArrayList<>();
                        fetchUserList(user_list);
                        for (String user_list1 : user_list) {
                            if(!username.equals(user_list1))
                            OUT.println(user_list1);
                        }
                        OUT.flush();
                        break;
                    case "G":
                        ArrayList<String> det = new ArrayList<>();
                        getContact(username, det);
                        if(!det.get(0).equals("0")){
                            OUT.println(det.get(1));
                            OUT.println(det.get(2));
                        }
                        else
                            OUT.println("*");
                        OUT.flush();
                        break;
                    case "W":
                        ArrayList<String> group = new ArrayList<>();
                        getGroup(username, group);
                        for(String group1 : group) {
                            OUT.println(group1);
                        }
                        OUT.flush();
                        break;
                    case "C":
                        String gport = IN.nextLine();
                        if(connectGroup(username,gport)){
                            OUT.println("**");
                            ArrayList<String> init_group = new ArrayList<>();
                            getGroup(username, init_group);
                            for(String group1 : init_group) {
                                OUT.println(group1);
                            }
                        }
                        else
                            OUT.println("*");
                        OUT.flush();
                        break;    
                    default:
                        break;
                }
                socket.close();
            }
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
    
    private static boolean checkUsername(String username){
        String sql = "select id from clients where username='"+username+"'";
        try {
            ResultSet result = statement.executeQuery(sql);
            if(!result.next())
                return true;
        } 
        catch (Exception e) {
            //JOptionPane.showMessageDialog(null,e);
        }
        return false;
    }
    
    private static boolean checkClient(String username,String password){
        String sql = "select id from clients where username='"+username+"' and password='"+password+"'";
        try {
            ResultSet result = statement.executeQuery(sql);
            if(result.next())
                return true;
        } 
        catch (Exception e) {
            //JOptionPane.showMessageDialog(null,e);
            System.out.println(e);
        }
        return false;
    }
    
    private static boolean registerClient(String username, String password){
        String sql = "insert into clients (username,password) values ('"+username+"','"+password+"')";
        try {
            statement.executeUpdate(sql);
            //JOptionPane.showMessageDialog(null,"Registered Successfully");
            return true;
        } 
        catch (Exception e) {
            //JOptionPane.showMessageDialog(null,e);
            System.out.println(e);
        }
        return false;
    }
    
    private static boolean loginUser(String username, String ip_address, String port){
        String sql = "update clients set ip_address='"+ip_address+"', port='"+port+"', is_online='1' where username='"+username+"'";
        try {
            statement.executeUpdate(sql);
            //JOptionPane.showMessageDialog(null,"Logged In Successfully");
            return true;
        } 
        catch (Exception e) {
            //JOptionPane.showMessageDialog(null,e);
            System.out.println(e);
        }
        return false;
    }
    
    private static void logoutUser(String username){
        String sql = "update clients set is_online='0' where username='"+username+"'";
        
        try {
            statement.executeUpdate(sql);
            //JOptionPane.showMessageDialog(null,"Logged Out Successfully");
        } 
        catch (Exception e) {
            //JOptionPane.showMessageDialog(null,e);
            System.out.println(e);
        }
    }
    
    private static boolean exitGroup(String username){
        String sql = "update clients set is_online='1' where username='"+username+"'";
        
        try {
            statement.executeUpdate(sql);
            //JOptionPane.showMessageDialog(null,"Logged Out Successfully");
            return true;
        } 
        catch (Exception e) {
            //JOptionPane.showMessageDialog(null,e);
            System.out.println(e);
        }
        return false;
    }
    
    public static void populateFriendList(String username, ArrayList<String> friend_list){
        String sql = "select friend2 from friend_list where friend1='"+username+"'";
        try {
            ResultSet result = statement.executeQuery(sql);
            while(result.next()){
                friend_list.add(result.getString("friend2"));
            }                
        } 
        catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public static void getGroup(String username, ArrayList<String> group){
        String sql = "select username, ip_address, gport from clients where is_online='2'";
        try {
            ResultSet result = statement.executeQuery(sql);
            while(result.next()){
                if(!result.getString("username").equals(username))
                    group.add(result.getString("ip_address")+"\n"+result.getString("gport"));
            }                
        } 
        catch (Exception e) {
            System.out.println(e);
        }
    }
    
    private static boolean checkFriend(String username, String friend_name){
        String sql = "select friend2 from friend_list where friend1='"+username+"'";
        try {
            ResultSet result = statement.executeQuery(sql);
            while(result.next()){
                if(result.getString("friend2").equals(friend_name))
                    return true;
            }
        } 
        catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }
    
    private static boolean updateFriendList(String username, String friend_name){
        String sql1 = "insert into friend_list (friend1,friend2) values ('"+username+"','"+friend_name+"')";
        String sql2 = "insert into friend_list (friend2,friend1) values ('"+username+"','"+friend_name+"')";
        try {
            statement.executeUpdate(sql1);
            statement.executeUpdate(sql2);
            return true;
        } 
        catch (Exception e) {
            //JOptionPane.showMessageDialog(null,e);
            System.out.println(e);
        }
        return false;
    }
    
    private static boolean connectGroup(String username, String gport){
        System.out.println(username+" "+gport);
        String sql = "update clients set is_online='2', gport='"+gport+"' where username='"+username+"'";
        try {
            statement.executeUpdate(sql);
            return true;
        } 
        catch (Exception e) {
            //JOptionPane.showMessageDialog(null,e);
            System.out.println(e);
        }
        return false;
    }
    
    private static void fetchUserList(ArrayList<String> user_list){
        String sql = "select username from clients";
        try {
            ResultSet result = statement.executeQuery(sql);
            while(result.next()){
                user_list.add(result.getString("username"));
            }                
        } 
        catch (Exception e) {
            System.out.println(e);
            //JOptionPane.showMessageDialog(null, "Some Error Occured !");
        }
    }
    
    private static void getContact(String username, ArrayList<String> det){
        String sql = "select ip_address, port, is_online from clients where username='"+username+"'";
        try {
            ResultSet result = statement.executeQuery(sql);
            if(result.next()){
                det.add(result.getString("is_online"));
                det.add(result.getString("ip_address"));
                det.add(result.getString("port"));
            }                
        } 
        catch (Exception e) {
            System.out.println(e);
            
        }
    }
}
