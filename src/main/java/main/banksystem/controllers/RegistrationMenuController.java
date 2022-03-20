package main.banksystem.controllers;

import java.net.URL;
import java.time.LocalDate;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.Map;
import java.util.HashMap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import main.banksystem.*;

public class RegistrationMenuController {

    ObservableList<String> roleList = FXCollections.observableArrayList(Role.CLIENT.name(), Role.OPERATOR.name(),
            Role.MANAGER.name(), Role.SPECIALIST.name(), Role.ADMINISTRATOR.name());
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
        roleStatus.setItems(roleList);
        roleStatus.setValue(roleList.get(0));

        registrationButton.setOnAction(event -> {
            errorLabel.setText("");

            FullNameBuilder fullNameBuilder = new FullNameBuilder();
            fullNameBuilder.BuildFirstName(nameField.getText());
            fullNameBuilder.BuildSecondName(surnameField.getText());
            fullNameBuilder.BuildFatherName(fatherName.getText());
            FullNameBuilder.Result fullName = fullNameBuilder.getFullName();
            if (!fullName.valid) {
                errorLabel.setText("Invalid full name");
                return;
            }

            AddressBuilder addressBuilder = new AddressBuilder();
            addressBuilder.BuildCity(cityField.getText());
            addressBuilder.BuildCountry(countryField.getText());
            addressBuilder.BuildStreetAddress(streetField.getText());
            AddressBuilder.Result address = addressBuilder.getAddress();
            if (!address.valid) {
                errorLabel.setText("Invalid address");
                return;
            }

            PassportBuilder passportBuilder = new PassportBuilder();
            passportBuilder.BuildFullName(fullName.fullName);
            passportBuilder.BuildBirthday(dateOfBirthday.getValue());
            passportBuilder.BuildSex(sexStatus.getValue());
            passportBuilder.BuildIdx(idField.getText());
            passportBuilder.BuildCitizenship(citizenshipStatus.getValue());
            passportBuilder.BuildAddress(address.address);
            PassportBuilder.Result passport = passportBuilder.getPassport();
            if (!passport.valid) {
                errorLabel.setText("Invalid password");
                return;
            }

            UserBuilder userBuilder = new UserBuilder();
            userBuilder.BuildIdx(new Id(0L));
            userBuilder.BuildRole(roleStatus.getValue());
            userBuilder.BuildEmail(emailField.getText());
            userBuilder.BuildNumber(numberField.getText());

            IndexGenerator generator = IndexGenerator.GetInstance();
            Id idx = new Id(generator.GenerateUserIdx());
        });
    }

}
