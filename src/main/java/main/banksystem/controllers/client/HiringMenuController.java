package main.banksystem.controllers.client;

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
import javafx.scene.control.TextField;
import main.banksystem.CPU;
import main.banksystem.DataBase;
import main.banksystem.IndexGenerator;
import main.banksystem.ProgramStatus;
import main.banksystem.builders.SalaryProjectBuilder;
import main.banksystem.commands.BuildSalaryProjectCommand;
import main.banksystem.commands.ICommand;
import main.banksystem.entities.Bill;
import main.banksystem.entities.BillConditions;
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
            companyList.add(company.getjName());
        }
        companyChoice.setItems(companyList);

        ProgramStatus status = ProgramStatus.getInstance();
        ArrayList<Id> ids = status.getUser().getBillIds();

        Map<Id, Bill> bills = dataBase.downloadMap(DataBase.BILLS_PART, Bill.class);

        for (Id id : ids) {
            if (bills.get(id).getBillConditions() == BillConditions.ACTIVE) {
                billList.add(String.valueOf(id.getId()));
            }
        }
        billChoice.setItems(billList);

        hireButton.setOnAction(event -> {
            String companyJName = companyChoice.getValue();
            Company company = null;
            for (Company companyI : companies.values()) {
                if (Objects.equals(companyI.getjName(), companyJName)) {
                    company = companyI;
                    break;
                }
            }

            if (company == null) {
                errorLabel.setText(String.format("No company %s", companyJName));
                return;
            }

            SalaryProjectBuilder salaryProjectBuilder = new SalaryProjectBuilder();
            salaryProjectBuilder.buildBillToId(billChoice.getValue());
            salaryProjectBuilder.buildSum(valueField.getText());
            salaryProjectBuilder.buildBillFromId(company.getBillCompanyId());
            salaryProjectBuilder.buildSalaryProjectId(new Id(0));
            SalaryProjectBuilder.Result result = salaryProjectBuilder.getSalaryProject();

            if (!result.valid) {
                errorLabel.setText(result.description);
                return;
            }

            IndexGenerator indexGenerator = IndexGenerator.getInstance();
            result.salaryProject.setSalaryProjectId(new Id(indexGenerator.generateIdx(IndexGenerator.SALARY_IDX)));

            BuildSalaryProjectCommand salaryProjectCommand = new BuildSalaryProjectCommand(status.getUser().getIdx(),
                    company.getPAN(), result.salaryProject, new ICommand.Type(true, false));

            CPU cpu = new CPU(status.getUser());
            cpu.heldCommand(salaryProjectCommand);

            hireButton.getScene().getWindow().hide();
        });


    }

}
