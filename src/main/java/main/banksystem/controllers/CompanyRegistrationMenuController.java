package main.banksystem.controllers;

import java.net.URL;
import java.util.Map;
import java.util.Objects;
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
import main.banksystem.commands.RegistryCompanyCommand;
import main.banksystem.entities.BIC;
import main.banksystem.entities.Company;
import main.banksystem.entities.Id;
import main.banksystem.entities.User;

import static main.banksystem.controllers.SwitchMenu.switchMenu;

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

        DataBase dataBase = DataBase.getInstance();
        Map<Id, Company> companies = dataBase.downloadMap(DataBase.COMPANY_PART, Company.class);
        for (Company company : companies.values()) {
            if (company.getIsBank()) {
                friendList.add(company.getjName());
            }
        }
        friendChoice.setItems(friendList);

        bankChoice.setItems(bankList);
        bankChoice.setValue(bankList.get(0));

        registrationButton.setOnAction(event -> {

            AddressBuilder addressBuilder = new AddressBuilder();
            addressBuilder.buildCity(cityField.getText());
            addressBuilder.buildCountry(countryField.getText());
            addressBuilder.buildStreetAddress(streetField.getText());
            AddressBuilder.Result address = addressBuilder.getAddress();
            if (!address.valid) {
                errorLabel.setText("Invalid address");
                return;
            }

            String JName = friendChoice.getValue();
            Company company = null;
            for (Company companyI : companies.values()) {
                if (Objects.equals(JName, companyI.getjName())) {
                    company = companyI;
                    break;
                }
            }

            if (company == null) {
                errorLabel.setText(String.format("No company %s", JName));
                return;
            }

            CompanyBuilder companyBuilder = new CompanyBuilder();
            companyBuilder.buildAddress(address.address);
            companyBuilder.buildName(jNameField.getText());
            companyBuilder.buildIsBank(bankChoice.getValue());
            companyBuilder.buildType(typeChoice.getValue());
            companyBuilder.buildPAN(panField.getText());
            companyBuilder.buildBankId(new BIC(company.getPAN()));
            CompanyBuilder.Result result = companyBuilder.getCompany();
            if (!result.valid) {
                errorLabel.setText(result.description);
                return;
            }

            ICommand.Type type = new ICommand.Type(true, false);
            RegistryCompanyCommand command = new RegistryCompanyCommand(result.company, type);

            User initCompanyUser = new User();
            initCompanyUser.setIdx(DataBase.INIT_COMPANY_ID);
            CPU cpu = new CPU(initCompanyUser);
            cpu.heldCommand(command);

            switchMenu(registrationButton, "/main/banksystem/login_menu.fxml");
        });
    }

}
