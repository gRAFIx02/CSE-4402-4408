package com.example.pro_xi;

import Connectivity.ConnectionClass;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

import java.sql.*;

public class DetailsControllers {

    @FXML
    public ImageView playerpicview;
     @FXML
    public Label playernm;
    @FXML
    public Label jerseynm;
    @FXML
    public Label posnm;
     @FXML
    public Label valnm;
     @FXML
    public Label salnm;
     @FXML
    public Label assistnm;
     @FXML
    public Label heightnm;
     @FXML
     public Label clubnm;
     @FXML
    public Label countrynm;
     @FXML
    public Label golnm;


     String clubb;
     String jnum;

     public void setClubb(String s)
     {
         clubb=s;
     }

     public void seyjernum(String s)
     {
         jnum=s;
     }


    public void editbutton(ActionEvent actionEvent) throws SQLException {

        Stage stage2 =new Stage();
        FXMLLoader loader=new FXMLLoader(getClass().getResource("editplayer.fxml"));
        Scene scene= null;
        try {
            scene = new Scene(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage2.setScene(scene);
        stage2.setTitle("Edit");
        stage2.show();

        editplayerController editplayerController=loader.getController();
        editplayerController.jerseynumber.setText(jnum);

        //setting image on editplayerlogo
        Connection connection = null;


        ConnectionClass connectionClass=new ConnectionClass();
        try {
            connection=connectionClass.getConnection();


            //  String sql="SELECT * FROM player WHERE Club='"+clubb+"'";

            String sql="SELECT  clubImage FROM passwordlist WHERE Username='"+clubb+"'";

            System.out.println(clubb);

            Statement statement=connection.createStatement();
            ResultSet resultSet=statement.executeQuery(sql);

            while (resultSet.next())
            {

                editplayerController.imageviewlogo.setImage(new Image(resultSet.getString(1)));
            }




        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //using tempo storage
        String sql="INSERT INTO tempo "+"Values(?,?)";
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setString(1,clubb);
        statement.setString(2,jnum);
        statement.executeUpdate();




    }

    public void closebutton(ActionEvent actionEvent) {

            Stage stage=(Stage) golnm.getScene().getWindow();
            stage.close();

    }

    public void delbutton(ActionEvent actionEvent) throws ClassNotFoundException, SQLException {

         //DELETE FROM player WHERE Club='' AND Jersey='' ;
        String sql="DELETE FROM player WHERE Club='"+clubb+"' AND Jersey='"+jnum+"' ";
        Connection connection = null;
        ConnectionClass connectionClass=new ConnectionClass();
        connection=connectionClass.getConnection();
        Statement statement=connection.createStatement();
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);

        alert.setTitle("Delete Player");
        alert.setHeaderText("Delete");
        alert.setContentText("DO you want to remove the player from your CLub");
        alert.getButtonTypes().add(ButtonType.YES);
        alert.getButtonTypes().add(ButtonType.NO);
        alert.getButtonTypes().remove(ButtonType.OK);
        alert.getButtonTypes().remove(ButtonType.CANCEL);

        if(alert.showAndWait().get()== ButtonType.YES) {

            try {

                statement.executeUpdate(sql);
                Stage stage=(Stage) salnm.getScene().getWindow();
                stage.close();

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

        }
        else
        {
            Stage stage=(Stage) salnm.getScene().getWindow();
            stage.close();

        }



    }

    public void sellbutton(ActionEvent actionEvent) {


        Stage stage2 =new Stage();
        FXMLLoader loader=new FXMLLoader(getClass().getResource("SellPlayer.fxml"));
        Scene scene= null;
        try {
            scene = new Scene(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage2.setScene(scene);
        stage2.setTitle("SELL PLAYER!!!");
        stage2.show();

        //setting up name of player for Sell player Window

        Connection connection = null;


        ConnectionClass connectionClass=new ConnectionClass();
        try {
            connection=connectionClass.getConnection();


            String sql="SELECT  FirstName,LastName,PlayerImage FROM player WHERE Club='"+clubb+"' and  Jersey='"+jnum+"'";


            Statement statement=connection.createStatement();
            ResultSet resultSet=statement.executeQuery(sql);

            SellPlayercontroller sellPlayercontroller=loader.getController();
            sellPlayercontroller.jrsynmlabel.setText(jnum);
            sellPlayercontroller.teamnamelabel.setText(clubb);


            while (resultSet.next())
            {
                sellPlayercontroller.Namelabel.setText(resultSet.getString(1)+" "+resultSet.getString(2));
                sellPlayercontroller.sellplayerimagelogo.setImage(new Image(resultSet.getString(3)));
            }




        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }



    }
}
