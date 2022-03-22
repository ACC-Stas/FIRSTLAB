package main.banksystem.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import main.banksystem.containers.Company;

public class CompanyRegistrationMenuController {

    ObservableList<String> typeList = FXCollections.observableArrayList();
    ObservableList<String> bankList = FXCollections.observableArrayList("Bank", "Company");

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ChoiceBox<String> bankChoice;

    @FXML
    private TextField cityField;

    @FXML
    private TextField countryField;

    @FXML
    private Label errorLabel;

    @FXML
    private TextField jNameField;

    @FXML
    private Button registrationButton;

    @FXML
    private TextField streetField;

    @FXML
    private ChoiceBox<String> typeChoice;

    @FXML
    void initialize() {
        for(Company.Type type : Company.Type.values()) {
            typeList.add(type.toString());
        }
        typeChoice.setItems(typeList);
        typeChoice.setValue(typeList.get(0));

        bankChoice.setItems(bankList);
        bankChoice.setValue(bankList.get(0));

        registrationButton.setOnAction(event -> {

        });
    }

}
