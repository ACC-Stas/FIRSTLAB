package main.banksystem.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;

public class CreateCreditMenuController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ChoiceBox<String> billChoice;

    @FXML
    private Label errorLabel;

    @FXML
    private ChoiceBox<String> percentChoice;

    @FXML
    private TextField percentField;

    @FXML
    private TabPane percentTabPane;

    @FXML
    private ChoiceBox<?> periodChoice;

    @FXML
    private Button transferButton;

    @FXML
    private TextField valueField;

    @FXML
    void initialize() {

    }

}
