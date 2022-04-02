package main.banksystem.controllers.client;

import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
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
import main.banksystem.ProgramStatus;
import main.banksystem.builders.SalaryProjectBuilder;
import main.banksystem.commands.BuildSalaryProjectCommand;
import main.banksystem.commands.ICommand;
import main.banksystem.entities.Company;
import main.banksystem.entities.Id;

public class HiringMenuController {

    ObservableList<String> companyList = FXCollections.observableArrayList();
    ObservableList<String> billList = FXCollections.observableArrayList();


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ChoiceBox<String> billChoice;

    @FXML
    private ChoiceBox<String> companyChoice;

    @FXML
    private Label errorLabel;

    @FXML
    private Button hireButton;

    @FXML
    private TextField valueField;

    @FXML
    void initialize() {
        DataBase dataBase = DataBase.getInstance();
        Map<Id, Company> companies = dataBase.downloadMap(DataBase.COMPANY_PART, Company.class);
        for (Company company : companies.values()) {
            companyList.add(company.getPAN().toString());
        }
        companyChoice.setItems(companyList);

        ProgramStatus status = ProgramStatus.getInstance();
        ArrayList<Id> ids = status.getUser().getBillIds();

        for(Id id : ids) {
            billList.add(String.valueOf(id.getId()));
        }
        billChoice.setItems(billList);

        hireButton.setOnAction(event -> {
            SalaryProjectBuilder salaryProjectBuilder = new SalaryProjectBuilder();
            salaryProjectBuilder.buildBillToId(new Id(Long.parseLong(billChoice.getValue())));
            salaryProjectBuilder.buildSum(valueField.getText());
            salaryProjectBuilder.buildBillFromId(companies.get(new
                    Id(Long.parseLong(companyChoice.getValue()))).getBillCompanyId());
            salaryProjectBuilder.buildSalaryProjectId(new Id(-1));
            SalaryProjectBuilder.Result result = salaryProjectBuilder.getSalaryProject();

            if (!result.valid) {
                errorLabel.setText(result.description);
                return;
            }

            BuildSalaryProjectCommand salaryProjectCommand = new BuildSalaryProjectCommand(status.getUser().getIdx(),
                    new Id(Long.parseLong(companyChoice.getValue())), result.salaryProject,
                    new ICommand.Type(true, false));

            CPU cpu = new CPU(status.getUser());
            cpu.heldCommand(salaryProjectCommand);

            hireButton.getScene().getWindow().hide();
        });



    }

}
