package main.banksystem.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import static main.banksystem.controllers.SwitchMenu.switchMenu;

public class LoginMenuController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField loginField;

    @FXML
    private Button loginSignInButton;

    @FXML
    private Button loginSignUpButton;

    @FXML
    private Button loginSignUpCompanyButton;

    @FXML
    private PasswordField passwordField;

    @FXML
    void initialize() {
        loginSignUpButton.setOnAction(event -> {
            switchMenu(loginSignUpButton, "/main/banksystem/registration_menu.fxml");
        });
        loginSignUpCompanyButton.setOnAction(event -> {
            switchMenu(loginSignUpButton, "/main/banksystem/company_registration_menu.fxml");
        });
        loginSignInButton.setOnAction(actionEvent -> {
            String login = loginField.getText();
            String password = passwordField.getText();

        });

    }

}
