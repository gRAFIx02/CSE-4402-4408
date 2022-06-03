package com.example.pro_xi;

import Connectivity.ConnectionClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class Controller1  {
    @FXML
    public TextField usernamefield;
    @FXML
    public PasswordField passwordfield;
    @FXML
    public PasswordField passwordfield1;
    @FXML
    public PasswordField confirmpassword;
    @FXML
    public Label errorlabel;
    @FXML
    public TextField usernamefield1;
    @FXML
    public Label labelsignup;


    @FXML

    Image image;

    String budget;


    private Stage stage;
    private Scene scene;
    private Parent root;

    public Connection connection;

    public void swithtoscene1(ActionEvent event) throws IOException {
        root= FXMLLoader.load(getClass().getResource("stage1.fxml"));
        stage= (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene =new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void swithtoscene2(ActionEvent event) throws IOException {
        root= FXMLLoader.load(getClass().getResource("stage2.fxml"));
        stage= (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene =new Scene(root);
        stage.setScene(scene);
        stage.show();
    }



    public void signin(ActionEvent actionEvent) {

        ConnectionClass connectionClass=new ConnectionClass();

        try {
            connection = connectionClass.getConnection();
            System.out.println("Connected successfully!!");

            if(usernamefield.getText().isBlank()==false&&passwordfield.getText().isBlank()==false)
            {

                if(validatelogin())
                {
                    errorlabel.setText("Successfully logged in!");
                    String clubimage=set_sence3(connection,usernamefield.getText());

                    String sql="INSERT INTO tempo "+"Values(?,?)";
                    PreparedStatement statement = connection.prepareStatement(sql);

                    statement.setString(1, usernamefield.getText());
                    statement.setString(2,"NULL");
                    statement.executeUpdate();

                    switchtoscene3(clubimage);


                    System.out.println("Successfully logged in!");

                }
                else
                {
                    loginclear();
                    errorlabel.setText("Enter valid username & password!");
                }

            }
            else
            {
                loginclear();
                errorlabel.setText("Enter valid username & password!");

            }


        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public boolean validatelogin() throws SQLException {
        String sql="SELECT COUNT(1) from passwordlist where Username='"+usernamefield.getText()+"' AND Password='"+passwordfield.getText()+"'";

        Statement statement=connection.createStatement();

        ResultSet resultSet=statement.executeQuery(sql);

        int c=0;

        while(resultSet.next())
        {
            c=resultSet.getInt(1);

        }

        if(c==0)
            return false;
        else
            return true;


    }

    public void signup(ActionEvent actionEvent) {

        ConnectionClass connectionClass=new ConnectionClass();

        try {
            connection = connectionClass.getConnection();
            System.out.println("Successfully connected");

            if(usernamefield1.getText().isBlank()==false&&passwordfield1.getText().isBlank()==false&&confirmpassword.getText().isBlank()==false&&passwordfield1.getText().equals(confirmpassword.getText()))
            {
                if(validatesignup())
                {
                    labelsignup.setText("Account Registered Successfully!");
                    System.out.println("Successfully created user !!");

                }



            }
            else if(passwordfield1.getText().equals(confirmpassword.getText())==false)

            {

                labelsignup.setText("Passwords don't match.");
                clear();

            }
            else
            {
                labelsignup.setText("Error! Please try again.");
                clear();
            }


        }
        catch (Exception e)
        {

        }




    }

    public boolean validatesignup()  {


        try {
               //INSERT INTO passwordlist (Username,Password) VALUES('Leo','messi');
            String sql="INSERT INTO passwordlist (Username,Password)"+"Values(?,?)";

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, usernamefield1.getText());
            statement.setString(2, passwordfield1.getText());
            statement.executeUpdate();

            return true;

        }
        catch (java.sql.SQLIntegrityConstraintViolationException e)
        {
            clear();
            labelsignup.setText("This username is already taken.");
        }
        catch (Exception e)
        {

            clear();
            e.printStackTrace();
        }

            return  false;
    }

    public  void clear()
    {
        usernamefield1.setText("");
        passwordfield1.setText("");
        confirmpassword.setText("");
    }

    public void loginclear()
    {
        usernamefield.setText("");
        passwordfield.setText("");
    }

    public  String set_sence3(Connection connection,String club) throws SQLException {
        //SELECT clubImage FROM passwordlist WHERE Username='Barcelona';
        // "SELECT COUNT(1) from passwordlist where Username='"+usernamefield.getText()+"' AND Password='"+passwordfield.getText()+"'";
       String sql="SELECT clubImage,Budget FROM passwordlist WHERE Username='"+club+"'";

       Statement statement=connection.createStatement();

       ResultSet resultSet=statement.executeQuery(sql);
       String s = null;
       while(resultSet.next())
       {
           s=resultSet.getString(1);
           budget= resultSet.getString(2);
       }

      //  System.out.println(s);

        return s;

    }

    public  void switchtoscene3(String s)
    {
        Stage stage2 =new Stage();
        FXMLLoader loader=new FXMLLoader(getClass().getResource("scene3.fxml"));
        Scene scene= null;
        try {
            scene = new Scene(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }

        stage2.setScene(scene);
        stage2.setTitle("Clubpage");
        CLubPageController cLubPageController=loader.getController();
        cLubPageController.clublogoview.setImage(new Image(s));
        cLubPageController.clublabel.setText(usernamefield.getText());
        cLubPageController.setClubb(usernamefield.getText());
        cLubPageController.budget.setText(budget);

        if(usernamefield.getText().equals("Psg"))
        {
            cLubPageController.leftanchor.setStyle("-fx-background-color: #99bbff;");
            cLubPageController.middleupanchor.setStyle("-fx-background-color:#99bbff;");
            cLubPageController.rightanchor.setStyle("-fx-background-color: #99bbff;");
            cLubPageController.transferlevel.setStyle("-fx-background-color: #ffffff;");
            cLubPageController.playernamelebel.setStyle("-fx-background-color: #ffffff;");
            cLubPageController.jerseynumberlebel.setStyle("-fx-background-color: #ffffff;");
            cLubPageController.poslebel.setStyle("-fx-background-color: #ffffff;");
            cLubPageController.goalslebel.setStyle("-fx-background-color: #ffffff;");
            cLubPageController.assistslebel.setStyle("-fx-background-color: #ffffff;");
            cLubPageController.sallebel.setStyle("-fx-background-color: #ffffff;");
            cLubPageController.marketvallebel.setStyle("-fx-background-color: #ffffff;");

        }
        else if(usernamefield.getText().equals("Barcelona"))
        {
            cLubPageController.leftanchor.setStyle("-fx-background-color: #eb345c;");
            cLubPageController.middleupanchor.setStyle("-fx-background-color:#ffe680;");
            cLubPageController.rightanchor.setStyle("-fx-background-color: #eb345c;");
            cLubPageController.transferlevel.setStyle("-fx-background-color: #ffe680;");
            cLubPageController.playernamelebel.setStyle("-fx-background-color: #ffe680;");
            cLubPageController.jerseynumberlebel.setStyle("-fx-background-color: #ffe680;");
            cLubPageController.poslebel.setStyle("-fx-background-color: #ffe680;");
            cLubPageController.goalslebel.setStyle("-fx-background-color: #ffe680;");
            cLubPageController.assistslebel.setStyle("-fx-background-color: #ffe680;");
            cLubPageController.sallebel.setStyle("-fx-background-color: #ffe680;");
            cLubPageController.marketvallebel.setStyle("-fx-background-color: #ffe680;");
        }
        else if(usernamefield.getText().equals("Real Madrid"))
        {
            cLubPageController.leftanchor.setStyle("-fx-background-color: #ffffff;");
            cLubPageController.middleupanchor.setStyle("-fx-background-color:#ffffff;");
            cLubPageController.rightanchor.setStyle("-fx-background-color: #ffffff;");
            cLubPageController.transferlevel.setStyle("-fx-background-color: #cce4ff;");
            cLubPageController.playernamelebel.setStyle("-fx-background-color: #cce4ff;");
            cLubPageController.jerseynumberlebel.setStyle("-fx-background-color: #cce4ff;");
            cLubPageController.poslebel.setStyle("-fx-background-color: #cce4ff;");
            cLubPageController.goalslebel.setStyle("-fx-background-color: #cce4ff;");
            cLubPageController.assistslebel.setStyle("-fx-background-color: #cce4ff;");
            cLubPageController.sallebel.setStyle("-fx-background-color: #cce4ff;");
            cLubPageController.marketvallebel.setStyle("-fx-background-color: #cce4ff;");

        }
        else if(usernamefield.getText().equals("Liverpool"))
        {
            cLubPageController.leftanchor.setStyle("-fx-background-color: #cc0000;");
            cLubPageController.middleupanchor.setStyle("-fx-background-color:#ff9999;");
            cLubPageController.rightanchor.setStyle("-fx-background-color: #cc0000;");
            cLubPageController.transferlevel.setStyle("-fx-background-color: #ffcccc;");
            cLubPageController.playernamelebel.setStyle("-fx-background-color: #ffcccc;");
            cLubPageController.jerseynumberlebel.setStyle("-fx-background-color: #ffcccc;");
            cLubPageController.poslebel.setStyle("-fx-background-color: #ffcccc;");
            cLubPageController.goalslebel.setStyle("-fx-background-color: #ffcccc;");
            cLubPageController.assistslebel.setStyle("-fx-background-color: #ffcccc;");
            cLubPageController.sallebel.setStyle("-fx-background-color: #ffcccc;");
            cLubPageController.marketvallebel.setStyle("-fx-background-color: #ffcccc;");

        }

        stage2.show();


    }

    public String getClubname(String s)
    {
        return  usernamefield.getText();
    }

}
