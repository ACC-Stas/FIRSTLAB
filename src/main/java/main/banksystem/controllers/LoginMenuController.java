package main.banksystem.controllers;

import java.net.URL;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import main.banksystem.DataBase;
import main.banksystem.containers.Id;
import main.banksystem.containers.User;

import static main.banksystem.controllers.SwitchMenu.switchMenu;

public class LoginMenuController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label errorLabel;

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

            DataBase dataBase = DataBase.GetInstance();
            Map<Id, User> users = dataBase.DownloadMap(DataBase.USER_PART, User.class);
            for (User user : users.values()) {
                if (Objects.equals(user.getLogin(), loginField.getText()) && Objects.equals(user.getPassword(), passwordField.getText())) {
                    switch (user.getRole()) {
                        case CLIENT -> {
                            switchMenu(loginSignUpButton, "/main/banksystem/client_main_menu.fxml");
                        }
                        case OPERATOR -> {
                            switchMenu(loginSignUpButton, "/main/banksystem/operator_main_menu.fxml");
                        }
                        case MANAGER -> {
                            switchMenu(loginSignUpButton, "/main/banksystem/manager_main_menu.fxml");
                        }
                        case SPECIALIST -> {
                            switchMenu(loginSignUpButton, "/main/banksystem/specialist_main_menu.fxml");
                        }
                        case ADMINISTRATOR -> {
                            switchMenu(loginSignUpButton, "/main/banksystem/administrator_main_menu.fxml");
                        }
                    }
                }
            }

            errorLabel.setText("Wrong");
        });

    }

}
