package Connectivity;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import  java.sql.*;

public class ConnectionClass {


    public Connection connection;

    public Connection getConnection() throws ClassNotFoundException {

        String db="info";
        String username="root";
        String password="";

        try {

            Class.forName("com.mysql.jdbc.Driver");
            connection= DriverManager.getConnection("jdbc:mysql://localhost/"+db,username,password);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


        return connection;
    }

}
