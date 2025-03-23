package org.example.main;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;

public class DataBase {
    private final String URL = "jdbc:postgresql://localhost:5432/WebChat_Damir";
    private final String USER = "postgres";
    private final String PASS = "1111";

    public void writeNewUser(User user){
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
            Statement statement = conn.createStatement()) {
            String sql = "INSERT INTO public.\"Users\" (login,password,token) VALUES " +
                    "('" + user.getLogin() + "', '" + user.getPass() + "', '" + user.getToken() + "');";
            statement.execute(sql);
            System.out.println("User " + user.getLogin() + "was added successfully to database.");
        }  catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<User> getAllUsers() {
        ArrayList<User> result = new ArrayList<>();

        try(Connection conn = DriverManager.getConnection(URL, USER, PASS);
            Statement statement = conn.createStatement();
            ResultSet resustSet = statement.executeQuery("SELECT * FROM Users")){

            while (resustSet.next()){
                result.add(new User(resustSet.getString(2),
                                    resustSet.getString(3),
                                    resustSet.getString(4)));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    User getUserByToken(String token) {
        User user = null;
        try (Connection connection = DriverManager.getConnection(URL, USER, PASS);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("select * from Users where = '" + token + "';")) {
        while ( resultSet.next()){
            user = new User(resultSet.getString("login"),
                    resultSet.getString("password"),
                    resultSet.getString("token"));
        }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    public void writeNewMessage(Message message){
        try(Connection conn = DriverManager.getConnection(URL,USER,PASS);
            Statement statement = conn.createStatement()){

            String date = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss").format(message.getDate());
            String sql = "insert info messages (text, token, date) values ('"+message.getText()+"','"+message.getToken()+"','"+date+"')";
            statement.execute(sql);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
