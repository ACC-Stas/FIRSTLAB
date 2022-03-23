package main.banksystem.controllers;

import java.net.URL;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import main.banksystem.CPU;
import main.banksystem.DataBase;
import main.banksystem.builders.AddressBuilder;
import main.banksystem.builders.CompanyBuilder;
import main.banksystem.commands.ICommand;
import main.banksystem.commands.RegistryCommand;
import main.banksystem.commands.RegistryCompanyCommand;
import main.banksystem.containers.Company;
import main.banksystem.containers.Id;
import main.banksystem.containers.User;

public class CompanyRegistrationMenuController {

    ObservableList<String> typeList = FXCollections.observableArrayList();
    ObservableList<String> bankList = FXCollections.observableArrayList("Bank", "Company");
    ObservableList<String> friendList = FXCollections.observableArrayList();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ChoiceBox<String> bankChoice;

    @FXML
    private TextField cityField;

    @FXML
    private ChoiceBox<String> friendChoice;

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
    private TextField panField;

    @FXML
    private ChoiceBox<String> typeChoice;

    @FXML
    void initialize() {
        for(Company.Type type : Company.Type.values()) {
            typeList.add(type.toString());
        }
        typeChoice.setItems(typeList);
        typeChoice.setValue(typeList.get(0));

        DataBase dataBase = DataBase.GetInstance();
        Map<Id, Company> companies = dataBase.DownloadMap(DataBase.COMPANY_PART, Company.class);
        for (Company company : companies.values()) {
            if (company.isBank()) {
                friendList.add(company.getPAN().toString());
            }
        }
        friendChoice.setItems(friendList);

        bankChoice.setItems(bankList);
        bankChoice.setValue(bankList.get(0));

        registrationButton.setOnAction(event -> {

            AddressBuilder addressBuilder = new AddressBuilder();
            addressBuilder.BuildCity(cityField.getText());
            addressBuilder.BuildCountry(countryField.getText());
            addressBuilder.BuildStreetAddress(streetField.getText());
            AddressBuilder.Result address = addressBuilder.getAddress();
            if (!address.valid) {
                errorLabel.setText("Invalid address");
                return;
            }

            CompanyBuilder companyBuilder = new CompanyBuilder();
            companyBuilder.BuildAddress(address.address);
            companyBuilder.BuildName(jNameField.getText());
            companyBuilder.BuildIsBank(bankChoice.getValue());
            companyBuilder.BuildType(typeChoice.getValue());
            companyBuilder.BuildPAN(panField.getText());
            companyBuilder.BuildBankId(friendChoice.getValue());
            CompanyBuilder.Result company = companyBuilder.getCompany();
            if (!company.valid) {
                errorLabel.setText("Invalid input");
                return;
            }

            ICommand.Type type = new ICommand.Type(true, false);
            RegistryCompanyCommand command = new RegistryCompanyCommand(company.company, type);

            Company initCompany = new Company();
            //initCompany.setPAN(DataBase.COMPANY_PART);
            //CPU cpu = new CPU(initCompany);
            //cpu.HeldCommand(command);

        });
    }

}
