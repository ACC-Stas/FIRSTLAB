package main.banksystem.controllers.specialist;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import main.banksystem.DataBase;
import main.banksystem.entities.Company;
import main.banksystem.entities.Id;

public class ConnectMenuController {

    ObservableList<String> companyList = FXCollections.observableArrayList();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ChoiceBox<String> companyChoice;

    @FXML
    private Button connectButton;

    @FXML
    private Label errorLabel;

    @FXML
    void initialize() {
        DataBase dataBase = DataBase.getInstance();
        Map<Id, Company> companies = dataBase.downloadMap(DataBase.COMPANY_PART, Company.class);
        for (Company company : companies.values()) {
            companyList.add(company.getPAN().toString());
        }
        companyChoice.setItems(companyList);

        connectButton.setOnAction(event -> {

        });
    }

}
