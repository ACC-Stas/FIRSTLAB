package main.banksystem.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class RegistrationMenuController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button administratorSignInButton;

    @FXML
    private Button clientSignUpButton;

    @FXML
    private Button managerSignUpButton;

    @FXML
    private Button operatorSignUpButton;

    @FXML
    private Button specialistSignUpButton;

    @FXML
    void initialize() {
        clientSignUpButton.setOnAction(event -> {
            clientSignUpButton.getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/banksystem/client_registration_menu.fxml"));

            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        });

    }

}
