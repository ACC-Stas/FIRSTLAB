package main.banksystem.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import static main.banksystem.controllers.SwitchMenu.switchMenu;

public class LoginMenuController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button authSignInButton;

    @FXML
    private Button loginSignUpButton;

    @FXML
    private TextField login_field;

    @FXML
    private TextField password_field;

    @FXML
    void initialize() {
        loginSignUpButton.setOnAction(event -> {
            switchMenu(loginSignUpButton, "/main/banksystem/registration_menu.fxml");
        });
        authSignInButton.setOnAction(actionEvent -> {
            String login = login_field.getText();
            String password = password_field.getText();

        });

    }

}
