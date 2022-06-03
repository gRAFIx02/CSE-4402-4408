package com.example.pro_xi;

import Connectivity.ConnectionClass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;




public class TransferwindowController implements Initializable {

    @FXML
    public TextField searchtextfield;
    @FXML
    public Button clrsearchlabel;
    @FXML
    public TableView playertableview;
    @FXML
    public TableColumn playernamecol;
    @FXML
    public TableColumn lastnamecol;
    @FXML
    public TableColumn playerposcol;
    @FXML
    public TableColumn detailcol;
    @FXML
    public Label clubname;
    @FXML
    public ImageView transferclublogo;
    @FXML
    public AnchorPane leftanchor;
    @FXML
    public AnchorPane rightanchor;
    @FXML
    public Button playernamelebel;
    @FXML
    public Button jerseynumberlebel;
    @FXML
    public Button poslebel;
    @FXML
    public Button goalslebel;
    @FXML
    public Button assistslebel;
    @FXML
    public Button sallebel;
    @FXML
    public Button marketvallebel;
    @FXML
    public AnchorPane middleupanchor;

    String clubb;


    boolean srchbynam=false;
    boolean srchbyjrsy=false;
    boolean srchbypos=false;




    ObservableList<TransferListPlayers> observableList= FXCollections.observableArrayList();

    public Connection connection;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        String sql="SELECT * FROM transferlist";
        qry(sql);



    }

    public  void  clr()
    {
        playertableview.getItems().clear();
    }



    public void qry(String sql)
    {
        ConnectionClass connectionClass=new ConnectionClass();
        try {



            // SELECT * FROM player WHERE Club='Psg';
            //String sql="SELECT * FROM player WHERE Club='"+clubb+"'";

            connection=connectionClass.getConnection();
            Statement statement=connection.createStatement();
            ResultSet resultSet=statement.executeQuery(sql);


            while(resultSet.next())
            {
                String Fname=resultSet.getString(1);
                String lname=resultSet.getString(2);
                String clubn=resultSet.getString(3);
                Integer val=resultSet.getInt(4);
                Integer sal=resultSet.getInt(5);
                Integer gols=resultSet.getInt(6);
                Integer assist=resultSet.getInt(7);
                Integer jersey=resultSet.getInt(8);
                String country=resultSet.getString(9);
                String clubimag=resultSet.getString(10);
                Double height=resultSet.getDouble(11);
                String pos=resultSet.getString(12);
                Integer pric=resultSet.getInt(13);


                System.out.println(Fname);
                observableList.add(new TransferListPlayers(Fname,lname,clubn,val,sal,gols,assist,jersey,country,clubimag,height,pos,pric));


            }



            playernamecol.setCellValueFactory(new PropertyValueFactory<>("FirstName"));
            lastnamecol.setCellValueFactory(new PropertyValueFactory<>("LastName"));

            playerposcol.setCellValueFactory(new PropertyValueFactory<>("position"));

            Callback<TableColumn<TransferListPlayers,String>,TableCell<TransferListPlayers,String>> cellfactory= param->{


                TableCell<TransferListPlayers,String> cell=new TableCell<>(){

                    public  void updateItem(String item,boolean empty)
                    {
                        super.updateItem(item,empty);
                        if(empty)
                        {
                            setGraphic(null);
                            setText(null);
                        }
                        else
                        {
                            Button editbutton=new Button("Details");

                            editbutton.setOnAction(actionEvent -> {

                                TransferListPlayers transferListPlayers= (TransferListPlayers) playertableview.getItems().get(getIndex());

                                /*Alert alert=new Alert(Alert.AlertType.INFORMATION);
                                alert.setContentText("You have clicked\n"+players.getPlayername()+"\n"+players.getClub());
                                alert.show();*/

                                Stage stage2 =new Stage();
                                FXMLLoader loader=new FXMLLoader(getClass().getResource("TransferDetails.fxml"));
                                Scene scene= null;
                                try {
                                    scene = new Scene(loader.load());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                stage2.setScene(scene);
                                stage2.setTitle("Details!!!");
                                stage2.show();

                                TransferDetailsController TdetailsControllers=loader.getController();

                                TdetailsControllers.userclublabel.setText(clubb);
                                TdetailsControllers.clubnm.setText(transferListPlayers.getClub());
                                TdetailsControllers.countrynm.setText(transferListPlayers.getCountry());
                                TdetailsControllers.golnm.setText(transferListPlayers.getGoals().toString());
                                TdetailsControllers.assistnm.setText(transferListPlayers.getAssists().toString());
                                TdetailsControllers.playerpicview.setImage(new Image(transferListPlayers.getPlayerImage()));
                                TdetailsControllers.jerseynm.setText(transferListPlayers.getJersey().toString());
                                TdetailsControllers.salnm.setText(transferListPlayers.getSalary().toString());
                                TdetailsControllers.posnm.setText(transferListPlayers.getPosition());
                                TdetailsControllers.heightnm.setText(transferListPlayers.getHeight().toString()+"cm");
                                TdetailsControllers.valnm.setText(transferListPlayers.getValue().toString()+"M");
                                TdetailsControllers.playernm.setText(transferListPlayers.getFirstName()+" "+transferListPlayers.getLastName());
                                TdetailsControllers.pricelabel.setText(transferListPlayers.getPrice().toString());




                            });

                            setGraphic(editbutton);
                            setText(null);
                        }



                    }


                };

                return cell;
            };

            detailcol.setCellFactory(cellfactory);




            playertableview.setItems(observableList);




        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void getClubb(String s)
    {
        clubb=s;
    }

    public void searchby(ActionEvent actionEvent) {

        String sql="";

        if(srchbynam==true)
        {
            sql="SELECT * FROM transferlist WHERE (FirstName Like '"+searchtextfield.getText()+"%' OR LastName Like '"+searchtextfield.getText()+"%')";;
            srchbynam=false;
        }
        else if(srchbyjrsy==true)
        {
            sql="SELECT * FROM transferlist WHERE Jersey='"+searchtextfield.getText()+"'";;
            srchbyjrsy=false;
        }
        else if(srchbypos==true)
        {
            sql="SELECT * FROM transferlist WHERE  Position='"+searchtextfield.getText()+"'";;
            srchbypos=false;
        }


        clr();
        qry(sql);
        searchtextfield.setText("");
        clrsearchlabel.setOpacity(1);


    }

    public void clrsearchbutton(ActionEvent actionEvent) {
        String sql="SELECT * FROM transferlist";
        clr();
        searchtextfield.setText("");
        qry(sql);
        clrsearchlabel.setOpacity(0);
    }



    public void srchplayerbyname(ActionEvent actionEvent) {

        searchtextfield.setOpacity(1);
        searchtextfield.setPromptText("Search by Player Name");
        srchbynam=true;

    }

    public void srchplayerbyjersey(ActionEvent actionEvent) {
        searchtextfield.setOpacity(1);
        searchtextfield.setPromptText("Search by jersey Num");
        srchbyjrsy=true;
    }

    public void srchplayerbypos(ActionEvent actionEvent) {
        searchtextfield.setOpacity(1);
        searchtextfield.setPromptText("Search by Position");
        srchbypos=true;
    }

    public void srchplayerbygoals(ActionEvent actionEvent) {
        searchtextfield.setOpacity(0);
        searchtextfield.setPromptText("");
        String sql="SELECT * FROM transferlist ORDER BY Goals DESC LIMIT 5";
        System.out.println(sql);
        clr();
        qry(sql);
        searchtextfield.setText("");
        clrsearchlabel.setOpacity(1);
    }

    public void srchplayerbyassists(ActionEvent actionEvent) {
        searchtextfield.setOpacity(0);
        searchtextfield.setPromptText("");
        String sql="SELECT * FROM transferlist ORDER BY Assists DESC LIMIT 5";
        System.out.println(sql);
        clr();
        qry(sql);
        searchtextfield.setText("");
        clrsearchlabel.setOpacity(1);
    }

    public void srchplayerbysal(ActionEvent actionEvent) {
        searchtextfield.setOpacity(0);
        searchtextfield.setPromptText("");
        String sql="SELECT * FROM transferlist ORDER BY Salary DESC LIMIT 5";
        System.out.println(sql);
        clr();
        qry(sql);
        searchtextfield.setText("");
        clrsearchlabel.setOpacity(1);
    }

    public void srchplayerbyval(ActionEvent actionEvent) {
        searchtextfield.setOpacity(0);
        searchtextfield.setPromptText("");
        String sql="SELECT * FROM transferlist ORDER BY Value DESC LIMIT 5";
        System.out.println(sql);
        clr();
        qry(sql);
        searchtextfield.setText("");
        clrsearchlabel.setOpacity(1);
    }


    public void Refresh(ActionEvent actionEvent) {
        String sql="SELECT * FROM transferlist";
        clr();
        qry(sql);
        playertableview.refresh();

    }
}
