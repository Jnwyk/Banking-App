package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Adress;
import model.BaseHandler;
import model.Client;

import javafx.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ShowClientsHandler{

    private Stage stage;
    private Scene scene;
    private Parent root;

    private DecimalFormat df2 = new DecimalFormat("#.##");

    @FXML
    private Label idLabel;
    @FXML
    private Label nameLabel;
    @FXML
    private Label surnameLabel;
    @FXML
    private Label peselLabel;
    @FXML
    private Label adressLabel;
    @FXML
    private Label balanceLabel;

    public void showClient(Client client){
        idLabel.setText("Client ID number: " + client.getId());
        nameLabel.setText(client.getName());
        surnameLabel.setText(client.getSname());
        peselLabel.setText(client.getPesel());
        adressLabel.setText(client.getAdress().getCity() + ", " + client.getAdress().getStreet() + " " + Integer.toString(client.getAdress().getNumber()));
        balanceLabel.setText(df2.format(client.getBalance()) + "zł");
    }


    public void switchToMainWindow(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
