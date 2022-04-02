package main.banksystem.controllers;

import java.net.URL;
import java.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import main.banksystem.*;
import main.banksystem.builders.AddressBuilder;
import main.banksystem.builders.FullNameBuilder;
import main.banksystem.builders.PassportBuilder;
import main.banksystem.builders.UserBuilder;
import main.banksystem.commands.ICommand;
import main.banksystem.commands.RegistryCommand;
import main.banksystem.entities.*;

import static main.banksystem.controllers.SwitchMenu.switchMenu;

public class RegistrationMenuController {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    static {
        MAPPER.findAndRegisterModules();
    }

    ObservableList<String> roleList = FXCollections.observableArrayList();
    ObservableList<String> citizenshipList = FXCollections.observableArrayList();
    ObservableList<String> sexList = FXCollections.observableArrayList();

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
    private TextField passwordField;

    @FXML
    private TextField loginField;

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
        for (Role role : Role.values()) {
            roleList.add(role.toString());
        }
        for (Citizenship citizenship : Citizenship.values()) {
            citizenshipList.add(citizenship.toString());
        }
        for (Sex sex : Sex.values()) {
            sexList.add(sex.toString());
        }
        sexStatus.setItems(sexList);
        sexStatus.setValue(sexList.get(0));
        citizenshipStatus.setItems(citizenshipList);
        citizenshipStatus.setValue(citizenshipList.get(0));
        roleStatus.setItems(roleList);
        roleStatus.setValue(roleList.get(0));

        registrationButton.setOnAction(event -> {
            errorLabel.setText("");

            FullNameBuilder fullNameBuilder = new FullNameBuilder();
            fullNameBuilder.buildFirstName(nameField.getText());
            fullNameBuilder.buildSecondName(surnameField.getText());
            fullNameBuilder.buildFatherName(fatherName.getText());
            FullNameBuilder.Result fullName = fullNameBuilder.getFullName();
            if (!fullName.valid) {
                errorLabel.setText("Invalid full name");
                return;
            }

            AddressBuilder addressBuilder = new AddressBuilder();
            addressBuilder.buildCity(cityField.getText());
            addressBuilder.buildCountry(countryField.getText());
            addressBuilder.buildStreetAddress(streetField.getText());
            AddressBuilder.Result address = addressBuilder.getAddress();
            if (!address.valid) {
                errorLabel.setText("Invalid address");
                return;
            }

            PassportBuilder passportBuilder = new PassportBuilder();
            passportBuilder.buildFullName(fullName.fullName);
            passportBuilder.buildBirthday(dateOfBirthday.getValue());
            passportBuilder.buildSex(sexStatus.getValue());
            passportBuilder.buildIdx(idField.getText());
            passportBuilder.buildCitizenship(citizenshipStatus.getValue());
            passportBuilder.buildAddress(address.address);
            PassportBuilder.Result passport = passportBuilder.getPassport();
            if (!passport.valid) {
                errorLabel.setText("Invalid passport");
                return;
            }

            UserBuilder userBuilder = new UserBuilder();
            userBuilder.buildIdx(new Id(0L));
            userBuilder.buildRole(roleStatus.getValue());
            userBuilder.buildEmail(emailField.getText());
            userBuilder.buildNumber(numberField.getText());
            userBuilder.buildLogin(loginField.getText());
            userBuilder.buildPassword(passwordField.getText());
            userBuilder.buildPassport(passport.passport);
            UserBuilder.Result user = userBuilder.getUser();
            if (!user.valid) {
                errorLabel.setText("Invalid user" + user.description);
                return;
            }

            IndexGenerator generator = IndexGenerator.getInstance();
            user.user.setIdx(new Id(generator.generateIdx(IndexGenerator.USER_IDX)));

            ICommand.Type type = new ICommand.Type(true, false);
            RegistryCommand command = new RegistryCommand(user.user, type);

            User initUser = new User();
            initUser.setIdx(DataBase.INIT_USER_ID);
            CPU cpu = new CPU(initUser);
            cpu.heldCommand(command);

            switchMenu(registrationButton, "/main/banksystem/login_menu.fxml");
        });
    }

}
