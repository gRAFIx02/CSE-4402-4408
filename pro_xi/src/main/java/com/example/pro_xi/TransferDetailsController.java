package com.example.pro_xi;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class TransferDetailsController {

    @FXML
    public Label pricelabel;
    @FXML
    public Label clubnm;
    @FXML
    public Label heightnm;
    @FXML
    public Label countrynm;
    @FXML
    public Label assistnm;
    @FXML
    public Label golnm;
    @FXML
    public Label salnm;
    @FXML
    public Label valnm;
    @FXML
    public Label posnm;
    @FXML
    public Label jerseynm;
    @FXML
    public Label playernm;
    @FXML
    public ImageView playerpicview;
    @FXML
    public Label userclublabel;

    public void closebutton(ActionEvent actionEvent) {

        Stage stage=(Stage) golnm.getScene().getWindow();
        stage.close();


    }

    public void Buyplayerbutton(ActionEvent actionEvent) {


        Stage stage2 =new Stage();
        FXMLLoader loader=new FXMLLoader(getClass().getResource("BuyWindow.fxml"));
        Scene scene= null;
        try {
            scene = new Scene(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }

        stage2.setScene(scene);
        Buywindow buywindow=loader.getController();
        buywindow.pricelabel.setText(pricelabel.getText());
        buywindow.getClubb(clubnm.getText());
        buywindow.getUserClubb(userclublabel.getText());
        buywindow.getJersey(Integer.valueOf(jerseynm.getText()));
        stage2.setTitle("TransferWindow");
        stage2.show();





    }
}
