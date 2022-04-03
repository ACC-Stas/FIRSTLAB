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
import main.banksystem.CPU;
import main.banksystem.DataBase;
import main.banksystem.ProgramStatus;
import main.banksystem.commands.FireCommand;
import main.banksystem.commands.ICommand;
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
            ProgramStatus status = ProgramStatus.getInstance();
            Id salaryProjectId = new Id(Long.parseLong(salaryChoice.getValue()));
            Map<Id, Company> companies = dataBase.downloadMap(DataBase.COMPANY_PART, Company.class);

            Company company = null;
            for (Company companyI : companies.values()) {
                if (companyI.getSalaryProjectIds().contains(salaryProjectId)) {
                    company = companyI;
                }
            }

            SalaryProject salaryProject = dataBase.download(salaryProjectId, DataBase.SALARY_PART, SalaryProject.class);
            if (salaryProject == null) {
                errorLabel.setText("THERE IS NO such salary project!");
                return;
            }

            if (company == null) {
                errorLabel.setText("THERE IS NO COMPANY with this salary project!");
                return;
            }
            ICommand.Type type = new ICommand.Type(false, true);
            FireCommand command = new FireCommand(status.getUser().getIdx(), company.getPAN(), salaryProject, type);

            CPU cpu = new CPU(status.getUser());
            cpu.heldCommand(command);

            fireButton.getScene().getWindow().hide();
        });

    }

}
