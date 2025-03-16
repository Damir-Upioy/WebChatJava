package org.example.main;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        MyWebSocket server = new MyWebSocket();
        server.start();
        System.out.println("Server started on address" + server.getAddress());
    }
}