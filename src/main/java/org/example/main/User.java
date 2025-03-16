package org.example.main;

public class User {
    private String Login;
    private String Pass;
    private String Token;

    public User(String login, String pass, String token) {
        Login = login;
        Pass = pass;
        Token = token;
    }

    public String getLogin() {
        return Login;
    }

    public void setLogin(String login) {
        Login = login;
    }

    public String getPass() {
        return Pass;
    }

    public void setPass(String pass) {
        Pass = pass;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    @Override
    public String toString() {
        return "User{" +
                "Login='" + Login + '\'' +
                ", Pass='" + Pass + '\'' +
                ", Token='" + Token + '\'' +
                '}';
    }
}
