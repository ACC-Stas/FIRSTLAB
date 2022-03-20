package main.banksystem.controllers;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import main.banksystem.Citizenship;
import main.banksystem.Sex;

public class RegistrationMenuController {

    ObservableList<String> citizenshipList = FXCollections.observableArrayList(Citizenship.BELARUS.name(),
            Citizenship.RUSSIA.name(), Citizenship.UKRAINE.name(), Citizenship.USA.name());
    ObservableList<String> sexList = FXCollections.observableArrayList(Sex.MAN.name(),
            Sex.WOMAN.name(), Sex.OLEG.name());

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ChoiceBox<String> citizenshipStatus;

    @FXML
    private TextField cityField;

    @FXML
    private TextField countryField;

    @FXML
    private DatePicker dateOfBirthday;

    @FXML
    private TextField emailField;

    @FXML
    private Label errorLabel;

    @FXML
    private TextField fatherName;

    @FXML
    private TextField idField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField numberField;

    @FXML
    private Button registrationButton;

    @FXML
    private ChoiceBox<String> roleStatus;

    @FXML
    private ChoiceBox<String> sexStatus;

    @FXML
    private TextField streetField;

    @FXML
    private TextField surnameField;

    @FXML
    void initialize() {
        sexStatus.setItems(sexList);
        sexStatus.setValue(sexList.get(0));
        citizenshipStatus.setItems(citizenshipList);
        citizenshipStatus.setValue(citizenshipList.get(0));
        registrationButton.setOnAction(event -> {
            String name = nameField.getText();
            String surname = surnameField.getText();
            String fathername = fatherName.getText();
            String email = emailField.getText();
            String number = numberField.getText();
            LocalDate date = dateOfBirthday.getValue();
            String sex = sexStatus.getValue();
            String citizenship = citizenshipStatus.getValue();
            String idPassport = idField.getText();
            String country = countryField.getText();
            String city = cityField.getText();
            String streetAddress = streetField.getText();

        });
    }

}
