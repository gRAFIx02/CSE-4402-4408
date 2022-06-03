package com.example.pro_xi;

import Connectivity.ConnectionClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.*;

public class Buywindow {
    public Label pricelabel;
    public TextField paidamounttextfield;
    @FXML
    public Label checklabel;

    String userclubb;
    String clubb;
    Integer Jersey;

    public Connection connection;

    public void getUserClubb(String s)
    {
        userclubb=s;
    }


    public void getClubb(String s)
    {
        clubb=s;
    }


    public  void getJersey(Integer a)
    {
        Jersey=a;
    }

    public void buybutton(ActionEvent actionEvent) throws ClassNotFoundException, SQLException {

             if(paidamounttextfield.getText().isBlank()==false) {

                 Integer moneyneed = Integer.valueOf(pricelabel.getText());
                 Integer moneypaid=Integer.valueOf(paidamounttextfield.getText());

                 //System.out.println(moneyneed+" "+moneypaid+" "+clubb);

                 //DBMS
                 ConnectionClass connectionClass=new ConnectionClass();
                 connection=connectionClass.getConnection();

                 String sql="SELECT Budget from passwordlist where Username='"+userclubb+"' ";
                 String sqll="SELECT Budget from passwordlist where Username='"+clubb+"' ";

                 Statement statement= connection.createStatement();
                 ResultSet resultSet=statement.executeQuery(sql);

                 Integer totalamount=0;

                 while (resultSet.next())
                 {
                     totalamount=resultSet.getInt(1);
                 }
                 
                 statement=connection.createStatement();
                 ResultSet ss=statement.executeQuery(sqll);
                 
                 int curbudget = 0;
                 
                 while (ss.next())
                 {
                     curbudget=ss.getInt(1);
                 }

                // System.out.println(totalamount);

                 if(totalamount<moneyneed)
                 {
                     checklabel.setText("You Don't  Have enough Money!");
                 }
                 else if(totalamount>=moneyneed&&moneypaid>=moneyneed)
                 {
                     totalamount-=moneyneed;
                     //System.out.println(Jersey);

                     //
                     sql="SELECT * FROM transferlist WHERE Club='"+clubb+"' AND Jersey='"+Jersey+"'";
                     statement=connection.createStatement();
                     ResultSet resultSet1=statement.executeQuery(sql);
                     String Fname = null;
                     String lname=null;
                     String clubn=null;
                     Integer val=null;
                     Integer sal=null;
                     Integer gols=null;
                     Integer assist=null;
                     Integer jersey=null;
                     String country=null;
                     String clubimag=null;
                     Double height=null;
                     String pos=null;

                     while (resultSet1.next())
                     {
                          Fname=resultSet1.getString(1);
                          lname=resultSet1.getString(2);
                          clubn=resultSet1.getString(3);
                          val=resultSet1.getInt(4);
                          sal=resultSet1.getInt(5);
                          gols=resultSet1.getInt(6);
                          assist=resultSet1.getInt(7);
                          jersey=resultSet1.getInt(8);
                          country=resultSet1.getString(9);
                          clubimag=resultSet1.getString(10);
                          height=resultSet1.getDouble(11);
                          pos=resultSet1.getString(12);
                     }

                 //    System.out.println(Fname);
                  //   System.out.println(userclubb+" "+clubb);

                     //Delete From Transfer LIST

                     //DELETE FROM transferlist WHERE Club='Psg' AND Jersey='1' ;
                     sql="DELETE FROM transferlist WHERE Club='"+clubb+"' AND Jersey='"+Jersey+"' ";
                     statement.executeUpdate(sql);

                     //Delete From Current Club

                     sql="DELETE FROM player WHERE Club='"+clubb+"' AND Jersey='"+Jersey+"' ";
                     statement.executeUpdate(sql);

                     //Insert into new Club
                     String sql1="INSERT INTO player "+"Values(?,?,?,?,?,?,?,?,?,?,?,?)";

                     PreparedStatement statement1 = connection.prepareStatement(sql1);

                     statement1.setString(1, Fname);
                     statement1.setString(2, lname);
                     statement1.setString(3,userclubb);
                     statement1.setInt(4,val);
                     statement1.setInt(5,sal);
                     statement1.setInt(6,gols);
                     statement1.setInt(7,assist);
                     statement1.setInt(8,jersey);
                     statement1.setString(9,country);
                     statement1.setString(10,clubimag);
                     statement1.setDouble(11,height);
                     statement1.setString(12,pos);
                     statement1.executeUpdate();

                     //deducting money

                     //UPDATE passwordlist SET Budget='' WHERE Username='Psg';
                     sql="UPDATE passwordlist SET Budget='"+totalamount+"' WHERE Username='"+userclubb+"'";
                     statement.executeUpdate(sql);


                     System.out.println(curbudget);

                     curbudget+=moneyneed;

                     sqll="UPDATE passwordlist SET Budget='"+curbudget+"' WHERE Username='"+clubb+"'";
                     statement.executeUpdate(sqll);


                     //Confirm success to user
                     checklabel.setText("Successfully Bought!");


                 }
                 else
                 {
                     checklabel.setText("Enter Sufficient amount!");
                 }



             }
             else
             {
                 checklabel.setText("Enter Required Amount to buy");
             }



    }

    public void cancelbutton(ActionEvent actionEvent) {

        Stage stage=(Stage) pricelabel.getScene().getWindow();
        stage.close();

    }
}
