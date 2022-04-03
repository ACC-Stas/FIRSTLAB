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
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import main.banksystem.CPU;
import main.banksystem.DataBase;
import main.banksystem.IndexGenerator;
import main.banksystem.ProgramStatus;
import main.banksystem.builders.CreditBuilder;
import main.banksystem.builders.InstallmentBuilder;
import main.banksystem.commands.BuildCreditCommand;
import main.banksystem.commands.BuildInstallmentCommand;
import main.banksystem.commands.ICommand;
import main.banksystem.entities.*;

public class CreateInstallmentMenuController {

    ObservableList<String> billList = FXCollections.observableArrayList();
    ObservableList<String> percentList = FXCollections.observableArrayList();
    ObservableList<String> periodList = FXCollections.observableArrayList();
    ObservableList<String> companyList = FXCollections.observableArrayList();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ChoiceBox<String> billChoice;

    @FXML
    private ChoiceBox<String> companyChoice;

    @FXML
    private Button installmentButton;

    @FXML
    private Label errorLabel;

    @FXML
    private ChoiceBox<String> percentChoice;

    @FXML
    private TextField percentField;

    @FXML
    private TabPane percentTabPane;

    @FXML
    private ChoiceBox<String> periodChoice;

    @FXML
    private TextField valueField;

    @FXML
    void initialize() {
        ProgramStatus status = ProgramStatus.getInstance();
        ArrayList<Id> ids = status.getUser().getBillIds();

        DataBase dataBase = DataBase.getInstance();
        Map<Id, Bill> bills = dataBase.downloadMap(DataBase.BILLS_PART, Bill.class);

        for(Id id : ids) {
            if (bills.get(id).getBillConditions() == BillConditions.ACTIVE) {
                billList.add(String.valueOf(id.getId()));
            }
        }
        billChoice.setItems(billList);

        for (Period period : Period.values()) {
            periodList.add(period.toString());
        }
        periodChoice.setItems(periodList);
        periodChoice.setValue(periodList.get(0));

        for (Percent percent : Percent.values()) {
            percentList.add(percent.toString());
        }
        percentChoice.setItems((percentList));
        percentChoice.setValue(percentList.get(0));

        Map<Id, Company> companies = dataBase.downloadMap(DataBase.COMPANY_PART, Company.class);

        for (Company company : companies.values()) {
            if (!company.getIsBank()) {
                companyList.add(company.getjName());
            }
        }
        companyChoice.setItems((companyList));

        installmentButton.setOnAction(event -> {
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

            InstallmentBuilder installmentBuilder = new InstallmentBuilder();
            installmentBuilder.buildId(new Id(1));
            if (percentTabPane.getSelectionModel().getSelectedIndex() == 0){
                installmentBuilder.buildPercent(Double.parseDouble(percentChoice.getValue()));
            }
            else {
                installmentBuilder.buildPercent(Double.parseDouble(percentField.getText()));
            }

            installmentBuilder.buildPeriod(periodChoice.getValue());

            installmentBuilder.buildCompanyBillId(company.getBillCompanyId());
            Id billId = new Id(Long.parseLong(billChoice.getValue()));
            installmentBuilder.buildSourceBillId(billId);
            installmentBuilder.buildSumToPay(valueField.getText());
            InstallmentBuilder.Result installment = installmentBuilder.getInstallment();

            if (!installment.valid) {
                errorLabel.setText(installment.description);
                return;
            }

            IndexGenerator indexGenerator = IndexGenerator.getInstance();
            installment.installment.setId(new Id(indexGenerator.generateIdx(IndexGenerator.INSTALLMENT_IDX)));

            BuildInstallmentCommand command = new BuildInstallmentCommand(status.getUser().getIdx(),
                    installment.installment, new ICommand.Type(true, false));
            CPU cpu = new CPU(status.getUser());
            cpu.heldCommand(command);

            installmentButton.getScene().getWindow().hide();
        });

    }

}
