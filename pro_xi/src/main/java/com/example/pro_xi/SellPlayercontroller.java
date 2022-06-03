package com.example.pro_xi;

import Connectivity.ConnectionClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.sql.*;

public class SellPlayercontroller {
    @FXML
    public TextField setsellpricetextfield;
    @FXML
    public ImageView sellplayerimagelogo;
    @FXML
    public Label Namelabel;
    @FXML
    public Label jrsynmlabel;
    @FXML
    public Label teamnamelabel;
    @FXML
    public Label checklabel;

    public void addtotransferbutton(ActionEvent actionEvent) {







        if(setsellpricetextfield.getText().isBlank()==false) {

            //  System.out.println(teamnamelabel.getText()+" "+jrsynmlabel.getText());

            String Fname =null, Lname=null, Position=null, PlayerImage=null, Clubname=null, Country=null;
            Integer Value=null, Salary=null, Goals=null, Assists=null, Jersey=null, Price=null;
            Double Height=null;

            Jersey= Integer.valueOf(setsellpricetextfield.getText());
            Price= Integer.valueOf(setsellpricetextfield.getText());


            Connection connection = null;


            ConnectionClass connectionClass=new ConnectionClass();
            try {
                connection=connectionClass.getConnection();


                //check if the player is already in transferlist or not!!!
                String sqll="SELECT COUNT(1) from transferlist where Club='"+teamnamelabel.getText()+"' AND Jersey='"+jrsynmlabel.getText()+"'";

                Statement s=connection.createStatement();
                ResultSet r=s.executeQuery(sqll);
                Integer c=0;

                if(r.next())
                {
                    c=r.getInt(1);
                }

                if(c==1)
                {
                    checklabel.setText("Already Registered IN TRANSFER LIST!");
                    return;
                }


                //Extracting data from players table

                String sql="SELECT * FROM player WHERE Club='"+teamnamelabel.getText()+"' and Jersey='"+jrsynmlabel.getText()+"'";


                Statement statement=connection.createStatement();
                ResultSet resultSet=statement.executeQuery(sql);



                while (resultSet.next())
                {

                    Fname=resultSet.getString(1);
                    Lname=resultSet.getString(2);
                    Clubname=resultSet.getString(3);
                    Value=resultSet.getInt(4);
                    Salary=resultSet.getInt(5);
                    Goals=resultSet.getInt(6);
                    Assists=resultSet.getInt(7);
                    Jersey=resultSet.getInt(8);
                    Country=resultSet.getString(9);
                    PlayerImage=resultSet.getString(10);
                    Height=resultSet.getDouble(11);
                    Position=resultSet.getString(12);


                }


             //   System.out.println(" "+Fname+" "+Lname+" "+Salary+" "+Value+" "+Goals+" "+Assists+" "+Position);

                //Adding to transferlist

                String sql1="INSERT INTO transferlist "+"Values(?,?,?,?,?,?,?,?,?,?,?,?,?)";

                PreparedStatement statement1 = connection.prepareStatement(sql1);

                statement1.setString(1, Fname);
                statement1.setString(2, Lname);
                statement1.setString(3,Clubname);
                statement1.setInt(4,Value);
                statement1.setInt(5,Salary);
                statement1.setInt(6,Goals);
                statement1.setInt(7,Assists);
                statement1.setInt(8,Jersey);
                statement1.setString(9,Country);
                statement1.setString(10,PlayerImage);
                statement1.setDouble(11,Height);
                statement1.setString(12,Position);
                statement1.setInt(13,Price);
                statement1.executeUpdate();
                checklabel.setText("SUCCESSFULLY ADDED TO TRANSFER LIST");









            } catch (ClassNotFoundException e) {
                checklabel.setText("ERROR TRY AGAIN !");
                e.printStackTrace();
            } catch (SQLException e) {
                checklabel.setText("ERROR TRY AGAIN !");
                e.printStackTrace();
            }




        }
        else
        {
            checklabel.setText("ERROR ! PLEASE SET THE PRICE");

        }



    }

    public void cancelprocess(ActionEvent actionEvent) {

        Stage stage=(Stage)Namelabel.getScene().getWindow();
        stage.close();

    }
}
