package com.example.pro_xi;

import Connectivity.ConnectionClass;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;
import java.sql.*;

public class editplayerController  implements Initializable {
    @FXML
    public Label labeledit;
    @FXML
    public TextField fnam;
    @FXML
    public TextField lstnam;
    @FXML
    public TextField ssal;
    @FXML
    public TextField mval;
    @FXML
    public TextField countrynam;
    @FXML
    public ChoiceBox ppos;
    @FXML
    public ImageView imageviewlogo;
    @FXML
    public Label jerseynumber;
    Image image;

    private String choice[]={"Forward","Defender","Midfielder","Goalkeeper"};

    public Connection connection;

    String clubb;
    String jnum;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {



             ppos.getItems().addAll(choice);
             ppos.setOnAction(this::getchoice);




    }

    public void tempo(ConnectionClass connectionClass) throws ClassNotFoundException, SQLException {
        //using tempo get info

        connection=connectionClass.getConnection();
        String sqll="Select * from tempo";
        Statement s=connection.createStatement();
        ResultSet r=s.executeQuery(sqll);

        while(r.next())
        {


            clubb=r.getString(1);
            jnum=r.getString(2);
        }


        //deleting tempo
        String sqlll="Delete From tempo where 1=1";
        Statement ss=connection.createStatement();
        ss.executeUpdate(sqlll);




    }
    public void activating_tempo()
    {
        ConnectionClass con=new ConnectionClass();
        try {
            tempo(con);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private  void getchoice(Event e)
    {
        String s=(String) ppos.getValue();
    }

    public void saveinfo(ActionEvent actionEvent) throws ClassNotFoundException, SQLException {

        activating_tempo();

        if(fnam.getText().isBlank()==false&&lstnam.getText().isBlank()==false&&ssal.getText().isBlank()==false&&mval.getText().isBlank()==false&&countrynam.getText().isBlank()==false)
        {

            //execute query
            //UPDATE player SET FirstName='K' , LastName='VB' ,Position='Defender',Salary='1111',Value='111',Country='arf' WHERE Club='Realmadrid' AND Jersey='9';
            ConnectionClass connectionClass=new ConnectionClass();
            connection=connectionClass.getConnection();


            String sql="UPDATE player SET FirstName='"+fnam.getText()+"' , LastName='"+lstnam.getText()+"' ,Position='"+ppos.getValue().toString()+"',Salary='"+ssal.getText()+"',Value='"+mval.getText()+"',Country='"+countrynam.getText()+"' WHERE Club='"+clubb+"' AND Jersey='"+jnum+"'";

            Statement ss=connection.createStatement();
            ss.executeUpdate(sql);
            labeledit.setText("SUCCESSFULLY EDITED");



        }
        else
        {
            labeledit.setText("Enter Not Null values!");
        }
    }

    public void closewindow(ActionEvent actionEvent) {

        Stage stage=(Stage) labeledit.getScene().getWindow();
        stage.close();
    }

}
