package org.example.main;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class MyWebSocket extends WebSocketServer {
    private static final int PORT = 7777;

    DataBase db;
    Set<WebSocket> cons;
    ArrayList<User> users;

    public MyWebSocket() {
        super(new InetSocketAddress(PORT));
        cons = new HashSet<org.java_websocket.WebSocket>();
        db = new DataBase();
        users = db.getAllUsers();
    }

    @Override
    public void onOpen(org.java_websocket.WebSocket webSocket, ClientHandshake clientHandshake) {
        cons.add(webSocket);
        System.out.println("new connection from " + webSocket.getRemoteSocketAddress().getAddress().getHostAddress());

    }

    @Override
    public void onClose(org.java_websocket.WebSocket webSocket, int i, String s, boolean b) {
        cons.remove(webSocket);
        System.out.println("new connection to " + webSocket.getRemoteSocketAddress().getAddress().getHostAddress());

    }

    @Override
    public void onMessage(org.java_websocket.WebSocket webSocket, String message) {
        JSONObject obj = JSON.parseObject(message);
        String type = obj.getString("type");
        String login = obj.getString("name");
        String pass = obj.getString("pass");

        JSONObject answer = new JSONObject();

        if (type.equals("reg")) {
            if (getUserByLogin(login) == null) {
                String token = generatetoken();
                User user = new User(login, pass, token);
                users.add(user);
                db.writeNewUser(user);

                answer.put("type", "reg_success");
                answer.put("message", "Registration completed successfully. Please, log in now");
            } else {
                answer.put("type", "reg_error");
                answer.put("message", "This user already exists");
            }
            webSocket.send(answer.toString());
        } else if (type.equals("login")) {
            User user = getUserByLogin(login);

            if (user != null) {
                if (user.getPass().equals(pass)) {
                    answer.put("type", "login_success");
                    answer.put("token", user.getToken());
                    answer.put("message", "Log in completed successfully");
                } else {
                    answer.put("type", "login_error");
                    answer.put("message", "Password invalid");
                }
            } else {
                answer.put("type", "login_error");
                answer.put("message", "User invalid");
            }
            webSocket.send(answer.toString());
        }else if (type.equals("token.augh")){
            String token = obj.getString("token");
            User user = getUserByToken(token);
            answer.put("type","token_augh");
            answer.put("name", user.getLogin());

            webSocket.send(answer.toString());
        }



    }

    @Override
    public void onError(org.java_websocket.WebSocket webSocket, Exception e) {

    }

    @Override
    public void onStart() {

    }

    User getUserByLogin(String login) {
        for (User usr : users) {
            if (usr.getLogin().equals(login)) return usr;
        }
        return null;
    }

    private String generatetoken() {
        StringBuilder temp = new StringBuilder();
        String sum = "0123456789qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM";
        Random random = new Random();
        for (int i = 0; i < 32; i++) {
            temp.append(sum.charAt(random.nextInt(sum.length())));
        }

        return temp.toString();
    }

    User getUserByToken(String token) {
        for (User usr : users) {
            if (usr.getToken().equals(token)) return usr;
        }
        return null;
    }
}


