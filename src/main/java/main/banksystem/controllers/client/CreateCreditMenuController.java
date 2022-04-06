package main.banksystem.controllers.client;

import java.net.URL;
import java.util.*;
import java.util.regex.Pattern;

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
import main.banksystem.commands.BuildCreditCommand;
import main.banksystem.commands.ICommand;
import main.banksystem.entities.*;

public class CreateCreditMenuController {

    ObservableList<String> billList = FXCollections.observableArrayList();
    ObservableList<String> percentList = FXCollections.observableArrayList();
    ObservableList<String> periodList = FXCollections.observableArrayList();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ChoiceBox<String> billChoice;

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
    private Button creditButton;

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

        Pattern p = Pattern.compile("(\\d+\\.?\\d*)?");
        valueField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!p.matcher(newValue).matches()) valueField.setText(oldValue);
        });

        percentField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!p.matcher(newValue).matches()) percentField.setText(oldValue);
        });

        creditButton.setOnAction(event -> {

            CreditBuilder creditBuilder = new CreditBuilder();
            creditBuilder.buildId(new Id(1));
            if (percentTabPane.getSelectionModel().getSelectedIndex() == 0){
                creditBuilder.buildPercent(Double.parseDouble(percentChoice.getValue()));
            }
            else {
                creditBuilder.buildPercent(Double.parseDouble(percentField.getText()));
            }

            creditBuilder.buildPeriod(periodChoice.getValue());

            Map<Id, Company> companies = dataBase.downloadMap(DataBase.COMPANY_PART, Company.class);
            if (companies == null) {
                errorLabel.setText("No banks");
                return;
            }
            Id billId = new Id(Long.parseLong(billChoice.getValue()));
            for (Company company : companies.values()) {
                if (company.getIsBank() && company.getBillsIds().contains(billId)) {
                    creditBuilder.buildBankBillId(company.getBillCompanyId());
                    break;
                }
            }
            creditBuilder.buildSourceBillId(billId);
            creditBuilder.buildSumToPay(valueField.getText());
            CreditBuilder.Result credit = creditBuilder.getCredit();

            if (!credit.valid) {
                errorLabel.setText(credit.description);
                return;
            }

            IndexGenerator indexGenerator = IndexGenerator.getInstance();
            credit.credit.setId(new Id(indexGenerator.generateIdx(IndexGenerator.CREDIT_IDX)));

            BuildCreditCommand command = new BuildCreditCommand(status.getUser().getIdx(), credit.credit,
                    new ICommand.Type(true, false));
            CPU cpu = new CPU(status.getUser());
            cpu.heldCommand(command);

            creditButton.getScene().getWindow().hide();
        });

    }

}
