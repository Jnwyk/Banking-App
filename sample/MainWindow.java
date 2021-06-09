package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Adress;
import model.Client;
import model.BaseHandler;
import model.InvalidAmount;

import javax.swing.text.html.Option;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainWindow implements Initializable{

    @FXML
    private ChoiceBox<Client> listOfClients;

    private ArrayList<Client> clients;
    private Client currentClient;

    private Stage stage;
    private Scene scene;
    private Parent root;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        clients = BaseHandler.readClient();
        if(clients != null){
            listOfClients.getItems().addAll(clients);
            listOfClients.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Client>() {
                @Override
                public void changed(ObservableValue<? extends Client> observableValue, Client client, Client t1) {
                    currentClient = listOfClients.getSelectionModel().getSelectedItem();
                }
            });
            listOfClients.setValue(currentClient);
        }
    }

    public void switchToAddAccountScene(ActionEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("AddAccountScene.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToShowClientsWindow(ActionEvent event) throws IOException {
        if(currentClient == null)
            return;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("showClientsScene.fxml"));
        root = loader.load();
        ShowClientsHandler show = loader.getController();
        show.showClient(currentClient);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void depositMoney(ActionEvent event){
        if(currentClient == null)
            return;
        try{
            Optional<String> amount = dialogMoney();
            Optional<ButtonType> button = dialogConfirmation();
            if(button.get() == ButtonType.OK){
                currentClient.deposit(Double.parseDouble(amount.get()));
                listOfClients.getItems().addAll(clients);
                BaseHandler.saveClient(clients);
            }
        }
        catch(Exception e){
            errorDialog();
            e.printStackTrace();
        }
    }

    public void withdrawMoney(ActionEvent event){
        if(currentClient == null)
            return;
        try{
            Optional<String> amount = dialogMoney();
            Optional<ButtonType> button = dialogConfirmation();
            if(button.get() == ButtonType.OK){
                currentClient.withdraw(Double.parseDouble(amount.get()));
                listOfClients.getItems().addAll(clients);
                BaseHandler.saveClient(clients);
            }
        }
        catch(Exception e){
            errorDialog();
            e.printStackTrace();
        }
    }

    public void deleteClient(ActionEvent event){
        if(currentClient == null)
            return;
        Optional<ButtonType> button = dialogConfirmation();
        if(button.get() == ButtonType.OK){
            clients.remove(currentClient);
            listOfClients.getItems().remove(currentClient);
            BaseHandler.saveClient(clients);
        }
    }

    public void transferMoney(ActionEvent event){
        if(currentClient == null)
            return;
        try {
            Optional<Client> odbiorca = dialogClient();
            Optional<String> amount = dialogMoney();
            Optional<ButtonType> button = dialogConfirmation();
            if (button.get() == ButtonType.OK) {
                Client nextClient = odbiorca.get();
                currentClient.transfer(nextClient, Double.parseDouble(amount.get()));
                listOfClients.getItems().addAll();
                BaseHandler.saveClient(clients);
            }
        }
        catch (InvalidAmount e){
            errorDialog();
            e.printStackTrace();
        }
    }

    public Optional<ButtonType> dialogConfirmation(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Java Banking Application");
        alert.setHeaderText("Do you really want to do this?");
        return alert.showAndWait();
    }

    public Optional<String> dialogMoney(){
        TextInputDialog dialog = new TextInputDialog("Input amount");
        dialog.setTitle("Java Banking Application");
        dialog.setHeaderText("Deposit/Withdraw");
        dialog.setContentText("Enter amount: ");
        return dialog.showAndWait();
    }

    public Optional<Client> dialogClient(){
        ChoiceDialog<Client> dialog = new ChoiceDialog<>(currentClient, clients);
        dialog.setTitle("Java Banking Application");
        dialog.setHeaderText("Enter name of odbiorca");
        return dialog.showAndWait();
    }

    public void errorDialog(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Java Banking Application");
        alert.setHeaderText("Error");
        alert.setContentText("Wrong amount of money");
        alert.showAndWait();
    }

    public void exitSystem(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Java Banking Application");
        alert.setHeaderText("Do you really want to exit?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK){
            Platform.exit();
        }
    }

}
