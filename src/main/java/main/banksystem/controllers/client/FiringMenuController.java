package main.banksystem.controllers.client;

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
import main.banksystem.entities.SalaryProject;

public class FiringMenuController {

    ObservableList<String> salaryList = FXCollections.observableArrayList();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label errorLabel;

    @FXML
    private Button fireButton;

    @FXML
    private ChoiceBox<String> salaryChoice;

    @FXML
    void initialize() {
        DataBase dataBase = DataBase.getInstance();
        Map<Id, SalaryProject> salaries = dataBase.downloadMap(DataBase.SALARY_PART, SalaryProject.class);
        for (SalaryProject salaryProject : salaries.values()) {
            salaryList.add(salaryProject.getSalaryProjectId().toString());
        }
        salaryChoice.setItems(salaryList);

        fireButton.setOnAction(event -> {

        });

    }

}
