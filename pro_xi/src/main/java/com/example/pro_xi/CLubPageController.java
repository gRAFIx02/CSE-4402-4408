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
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.sql.* ;


public class CLubPageController implements Initializable {


    @FXML
    public Label clublabel;
    @FXML
    public ImageView clublogoview;
    @FXML
    public TableView playertableview;
    @FXML
    public TableColumn playernamecol;
    @FXML
    public TableColumn playerposcol;
    @FXML
    public TableColumn detailcol;
    @FXML
    public TableColumn sellcol;
    @FXML
    public TextField searchtextfield;
    @FXML
    public TableColumn lastnamecol;
    @FXML
    public Button clrsearchlabel;

    @FXML
    public Label budget;
    @FXML
    public AnchorPane leftanchor;
    @FXML
    public AnchorPane rightanchor;
    @FXML
    public AnchorPane middleupanchor;
    @FXML
    public Button transferlevel;
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

    String searchsql;

    String clubb;


    boolean srchbynam=false;
    boolean srchbyjrsy=false;
    boolean srchbypos=false;


    public void setClubb(String clubb) {
        this.clubb = clubb;
    }

    ObservableList<Players> observableList= FXCollections.observableArrayList();

    public Connection connection;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        activating_tempo();
        String sql="SELECT * FROM player WHERE Club='"+clubb+"'";
        qry(sql);


    }



    public void tempo(ConnectionClass connectionClass) throws ClassNotFoundException, SQLException {
        //using tempo get clubname
        connection=connectionClass.getConnection();
        String sqll="Select * from tempo";
        Statement s=connection.createStatement();
        ResultSet r=s.executeQuery(sqll);

        //deleting tempo
        String sqlll="Delete From tempo where 1=1";
        Statement ss=connection.createStatement();
        ss.executeUpdate(sqlll);

        while(r.next())
        {
            clubb=r.getString(1);
        }
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

    public  void  clr()
    {
        playertableview.getItems().clear();
    }


    public void qry(String sql)
    {
        ConnectionClass connectionClass=new ConnectionClass();
        try {

            tempo(connectionClass);

            // SELECT * FROM player WHERE Club='Psg';
            //String sql="SELECT * FROM player WHERE Club='"+clubb+"'";

            System.out.println(clubb);


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


                observableList.add(new Players(Fname,lname,clubn,val,sal,gols,assist,jersey,country,clubimag,height,pos));


            }



            playernamecol.setCellValueFactory(new PropertyValueFactory<>("FirstName"));
            lastnamecol.setCellValueFactory(new PropertyValueFactory<>("LastName"));

            playerposcol.setCellValueFactory(new PropertyValueFactory<>("position"));

            Callback<TableColumn<Players,String>,TableCell<Players,String>> cellfactory= param->{


                TableCell<Players,String> cell=new TableCell<>(){

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

                                Players players= (Players) playertableview.getItems().get(getIndex());

                                /*Alert alert=new Alert(Alert.AlertType.INFORMATION);
                                alert.setContentText("You have clicked\n"+players.getPlayername()+"\n"+players.getClub());
                                alert.show();*/

                                Stage stage2 =new Stage();
                                FXMLLoader loader=new FXMLLoader(getClass().getResource("DetailsStage.fxml"));
                                Scene scene= null;
                                try {
                                    scene = new Scene(loader.load());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                stage2.setScene(scene);
                                stage2.setTitle("Details!!!");
                                stage2.show();

                                DetailsControllers detailsControllers=loader.getController();

                                detailsControllers.clubnm.setText(players.getClub());
                                detailsControllers.countrynm.setText(players.getCountry());
                                detailsControllers.golnm.setText(players.getGoals().toString());
                                detailsControllers.assistnm.setText(players.getAssists().toString());
                                detailsControllers.playerpicview.setImage(new Image(players.getPlayerImage()));
                                detailsControllers.jerseynm.setText(players.getJersey().toString());
                                detailsControllers.salnm.setText(players.getSalary().toString());
                                detailsControllers.posnm.setText(players.getPosition());
                                detailsControllers.heightnm.setText(players.getHeight().toString()+"cm");
                                detailsControllers.valnm.setText(players.getValue().toString()+"M");
                                detailsControllers.playernm.setText(players.getFirstName()+" "+players.getLastName());
                                detailsControllers.setClubb(clubb);
                                detailsControllers.seyjernum(players.getJersey().toString());



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




    public void clrsearchbutton(ActionEvent actionEvent) {
        String sql="SELECT * FROM player WHERE Club='"+clubb+"'";
        clr();
        searchtextfield.setText("");
        qry(sql);
        clrsearchlabel.setOpacity(0);
    }

    public void searchby(ActionEvent e) {
        //SELECT * FROM player WHERE Club="Psg" and (FirstName LIKE 'M%' OR LastName LIKE 'M%');

       // String sql="SELECT * FROM player WHERE Club='"+clubb+"' and (FirstName Like '"+searchtextfield.getText()+"%' OR LastName Like '"+searchtextfield.getText()+"%')";;

        String sql="";

        if(srchbynam==true)
        {
            sql="SELECT * FROM player WHERE Club='"+clubb+"' and (FirstName Like '"+searchtextfield.getText()+"%' OR LastName Like '"+searchtextfield.getText()+"%')";;
            srchbynam=false;
        }
        else if(srchbyjrsy==true)
        {
            sql="SELECT * FROM player WHERE Club='"+clubb+"' and Jersey='"+searchtextfield.getText()+"'";;
            srchbyjrsy=false;
        }
        else if(srchbypos==true)
        {
            sql="SELECT * FROM player WHERE Club='"+clubb+"' and  Position='"+searchtextfield.getText()+"'";;
            srchbypos=false;
        }


        clr();
        qry(sql);
        searchtextfield.setText("");
        clrsearchlabel.setOpacity(1);
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
        String sql="SELECT * FROM player WHERE Club='"+clubb+"'  ORDER BY Goals DESC LIMIT 5";
        System.out.println(sql);
        clr();
        qry(sql);
        searchtextfield.setText("");
        clrsearchlabel.setOpacity(1);
    }


    public void srchplayerbyassists(ActionEvent actionEvent) {
        searchtextfield.setOpacity(0);
        searchtextfield.setPromptText("");
        String sql="SELECT * FROM player WHERE Club='"+clubb+"'  ORDER BY Assists DESC LIMIT 5";
        System.out.println(sql);
        clr();
        qry(sql);
        searchtextfield.setText("");
        clrsearchlabel.setOpacity(1);

    }

    public void srchplayerbysal(ActionEvent actionEvent) {
        searchtextfield.setOpacity(0);
        searchtextfield.setPromptText("");
        String sql="SELECT * FROM player WHERE Club='"+clubb+"'  ORDER BY Salary DESC LIMIT 5";
        System.out.println(sql);
        clr();
        qry(sql);
        searchtextfield.setText("");
        clrsearchlabel.setOpacity(1);

    }


    public void srchplayerbyval(ActionEvent actionEvent) {
        searchtextfield.setOpacity(0);
        searchtextfield.setPromptText("");
        String sql="SELECT * FROM player WHERE Club='"+clubb+"'  ORDER BY Value DESC LIMIT 5";
        System.out.println(sql);
        clr();
        qry(sql);
        searchtextfield.setText("");
        clrsearchlabel.setOpacity(1);

    }

    public void transferwindow(ActionEvent actionEvent) throws IOException, SQLException {


        Stage stage2 =new Stage();
        FXMLLoader loader=new FXMLLoader(getClass().getResource("Transferwindow.fxml"));
        Scene scene= null;
        try {
            scene = new Scene(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }

        stage2.setScene(scene);
        stage2.setTitle("TransferWindow");
        TransferwindowController transferwindowController=loader.getController();
        transferwindowController.clubname.setText(clubb);
        transferwindowController.getClubb(clubb);

        String sql="SELECT clubImage FROM passwordlist WHERE Username='"+clubb+"'";
        Statement statement=connection.createStatement();

        ResultSet resultSet=statement.executeQuery(sql);
        String s = null;
        while(resultSet.next())
        {
            s=resultSet.getString(1);
        }


        transferwindowController.transferclublogo.setImage(new Image(s));

        if(clubb.equals("Psg"))
        {
            transferwindowController.leftanchor.setStyle("-fx-background-color: #99bbff");
            transferwindowController.middleupanchor.setStyle("-fx-background-color:#99bbff");
            transferwindowController.rightanchor.setStyle("-fx-background-color: #99bbff");
            transferwindowController.playernamelebel.setStyle("-fx-background-color: #ffffff;");
            transferwindowController.jerseynumberlebel.setStyle("-fx-background-color: #ffffff;");
            transferwindowController.poslebel.setStyle("-fx-background-color: #ffffff;");
            transferwindowController.goalslebel.setStyle("-fx-background-color: #ffffff;");
            transferwindowController.assistslebel.setStyle("-fx-background-color: #ffffff;");
            transferwindowController.sallebel.setStyle("-fx-background-color: #ffffff;");
            transferwindowController.marketvallebel.setStyle("-fx-background-color: #ffffff;");

        }
        else if(clubb.equals("Barcelona"))
        {
            transferwindowController.leftanchor.setStyle("-fx-background-color: #eb345c;");
            transferwindowController.middleupanchor.setStyle("-fx-background-color:#ffe680;");
            transferwindowController.rightanchor.setStyle("-fx-background-color: #eb345c;");
            transferwindowController.playernamelebel.setStyle("-fx-background-color: #ffe680;");
            transferwindowController.jerseynumberlebel.setStyle("-fx-background-color: #ffe680;");
            transferwindowController.poslebel.setStyle("-fx-background-color: #ffe680;");
            transferwindowController.goalslebel.setStyle("-fx-background-color: #ffe680;");
            transferwindowController.assistslebel.setStyle("-fx-background-color: #ffe680;");
            transferwindowController.sallebel.setStyle("-fx-background-color: #ffe680;");
            transferwindowController.marketvallebel.setStyle("-fx-background-color: #ffe680;");
        }
        else if(clubb.equals("Real Madrid"))
        {
            transferwindowController.leftanchor.setStyle("-fx-background-color: #ffffff;");
            transferwindowController.middleupanchor.setStyle("-fx-background-color:#ffffff;");
            transferwindowController.rightanchor.setStyle("-fx-background-color: #ffffff;");
            transferwindowController.playernamelebel.setStyle("-fx-background-color: #cce4ff;");
            transferwindowController.jerseynumberlebel.setStyle("-fx-background-color: #cce4ff;");
            transferwindowController.poslebel.setStyle("-fx-background-color: #cce4ff;");
            transferwindowController.goalslebel.setStyle("-fx-background-color: #cce4ff;");
            transferwindowController.assistslebel.setStyle("-fx-background-color: #cce4ff;");
            transferwindowController.sallebel.setStyle("-fx-background-color: #cce4ff;");
            transferwindowController.marketvallebel.setStyle("-fx-background-color: #cce4ff;");

        }
        else if(clubb.equals("Liverpool"))
        {
            transferwindowController.leftanchor.setStyle("-fx-background-color: #cc0000;");
            transferwindowController.middleupanchor.setStyle("-fx-background-color:#ff9999;");
            transferwindowController.rightanchor.setStyle("-fx-background-color: #cc0000;");
            transferwindowController.playernamelebel.setStyle("-fx-background-color: #ffcccc;");
            transferwindowController.jerseynumberlebel.setStyle("-fx-background-color: #ffcccc;");
            transferwindowController.poslebel.setStyle("-fx-background-color: #ffcccc;");
            transferwindowController.goalslebel.setStyle("-fx-background-color: #ffcccc;");
            transferwindowController.assistslebel.setStyle("-fx-background-color: #ffcccc;");
            transferwindowController.sallebel.setStyle("-fx-background-color: #ffcccc;");
            transferwindowController.marketvallebel.setStyle("-fx-background-color: #ffcccc;");

        }



        stage2.show();


    }

    public void getBudget() throws SQLException {
        String sql="SELECT Budget FROM passwordlist WHERE Username='"+clubb+"'";

        Statement statement=connection.createStatement();

        ResultSet resultSet=statement.executeQuery(sql);
        String s = null;
        while(resultSet.next())
        {
            s=resultSet.getString(1);
        }

        budget.setText(s);
    }

    public void refresh(ActionEvent actionEvent) throws SQLException {
        activating_tempo();
        String sql="SELECT * FROM player WHERE Club='"+clubb+"'";
        clr();
        getBudget();
        qry(sql);

        playertableview.refresh();

    }
}
