package main.banksystem.controllers.specialist;

import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import main.banksystem.DataBase;
import main.banksystem.ProgramStatus;
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
        ProgramStatus status = ProgramStatus.getInstance();
        Map<Id, Company> companies = dataBase.downloadMap(DataBase.COMPANY_PART, Company.class);
        for (Company company : companies.values()) {
            companyList.add(company.getjName());
        }
        companyChoice.setItems(companyList);

        connectButton.setOnAction(event -> {
            String jName = companyChoice.getValue();
            if (jName == null || jName.equals("")) {
                errorLabel.setText("Choose company");
                return;
            }

            Id userId = status.getUser().getIdx();;
            for (Company company : companies.values()) {
                for (Id specialistId : company.getSpecialistIds()) {
                    if (userId.equals(specialistId)) {
                        errorLabel.setText(String.format("You are already tied to company %s", company.getjName()));
                        return;
                    }
                }
            }

            Company company = null;
            for (Company companyI : companies.values()) {
                if (Objects.equals(companyI.getjName(), jName)) {
                    company = companyI;
                    break;
                }
            }
            if (company == null) {
                errorLabel.setText("No such company");
                return;
            }

            for (Id id : company.getSpecialistIds()) {
                if (id.equals(status.getUser().getIdx())) {
                    errorLabel.setText("You are already in this company");
                    return;
                }
            }

            company.getSpecialistIds().add(status.getUser().getIdx());
            dataBase.save(company.getPAN(), DataBase.COMPANY_PART, company);

            connectButton.getScene().getWindow().hide();
        });
    }

}
