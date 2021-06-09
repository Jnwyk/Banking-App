package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Adress;
import model.BaseHandler;
import model.Client;

import javafx.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

public class AddAccountHandler implements Initializable {

    private ArrayList<Client> clients;

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private TextField nameField;
    @FXML
    private TextField surnameField;
    @FXML
    private TextField peselField;
    @FXML
    private TextField cityField;
    @FXML
    private TextField streetField;
    @FXML
    private TextField streetNumberField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        clients = BaseHandler.readClient();
    }

    public void addClient(ActionEvent event){
        try{
            if(!validateClient().isEmpty())
                throw new Exception();
            if(clients == null)
                clients = new ArrayList<>();
            Adress adress = new Adress(streetField.getText(), Integer.parseInt(streetNumberField.getText()), cityField.getText());
            Random generator = new Random();
            int newId = generator.nextInt((clients.size()+1) * 200);
            Client client = new Client(newId, nameField.getText(), surnameField.getText(), peselField.getText(), adress, 0);
            clients.add(client);
            nameField.clear();
            surnameField.clear();
            peselField.clear();
            streetField.clear();
            streetNumberField.clear();
            cityField.clear();
            BaseHandler.saveClient(clients);
        }
        catch(Exception e){
            e.printStackTrace();
            errorDialog();
            nameField.clear();
            surnameField.clear();
            peselField.clear();
            streetField.clear();
            streetNumberField.clear();
            cityField.clear();
        }
    }

    public String validateClient() throws Exception{
            String text = new String("");
            text += nameField.getText();
            text += surnameField.getText();
            text += peselField.getText();
            text += streetField.getText();
            text += cityField.getText();
            if(!text.isEmpty())
                throw new Exception();
            try{
                int number = Integer.parseInt(streetNumberField.getText());
            }
            catch(NumberFormatException e){
                e.printStackTrace();
                errorDialog();
            }
            return text;
    }

    public void switchToMainWindow(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void errorDialog(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Java Banking Application");
        alert.setHeaderText("Error");
        alert.setContentText("Incorrect input");
        alert.showAndWait();
    }
}
