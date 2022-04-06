package main.banksystem.controllers.client;

import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;
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
import main.banksystem.builders.DepositBuilder;
import main.banksystem.commands.BuildCreditCommand;
import main.banksystem.commands.BuildDepositCommand;
import main.banksystem.commands.ICommand;
import main.banksystem.entities.*;

public class CreateDepositMenuController {

    ObservableList<String> billList = FXCollections.observableArrayList();
    ObservableList<String> percentList = FXCollections.observableArrayList();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ChoiceBox<String> billChoice;

    @FXML
    private Button depositButton;

    @FXML
    private Label errorLabel;

    @FXML
    private ChoiceBox<String> percentChoice;

    @FXML
    private TextField percentField;

    @FXML
    private TabPane percentTabPane;

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

        depositButton.setOnAction(event -> {

            DepositBuilder depositBuilder = new DepositBuilder();
            depositBuilder.buildId(new Id(0));
            Id billId = new Id(Long.parseLong(billChoice.getValue()));
            depositBuilder.buildBillId(billId);

            if (percentTabPane.getSelectionModel().getSelectedIndex() == 0){
                depositBuilder.buildPercent(Double.parseDouble(percentChoice.getValue()));
            }
            else {
                depositBuilder.buildPercent(Double.parseDouble(percentField.getText()));
            }

            Map<Id, Company> companies = dataBase.downloadMap(DataBase.COMPANY_PART, Company.class);
            if (companies == null) {
                errorLabel.setText("No banks");
                return;
            }
            for (Company company : companies.values()) {
                if (company.getIsBank() && company.getBillsIds().contains(billId)) {
                    depositBuilder.buildBankBillId(company.getBillCompanyId());
                    break;
                }
            }
            depositBuilder.buildValue(valueField.getText());
            DepositBuilder.Result result = depositBuilder.getDeposit();

            if (!result.valid) {
                errorLabel.setText(result.description);
                return;
            }

            IndexGenerator indexGenerator = IndexGenerator.getInstance();
            result.deposit.setId(new Id(indexGenerator.generateIdx(IndexGenerator.DEPOSIT_IDX)));

            BuildDepositCommand command = new BuildDepositCommand(status.getUser().getIdx(), result.deposit,
                    new ICommand.Type(false, true));
            CPU cpu = new CPU(status.getUser());
            cpu.heldCommand(command);

            depositButton.getScene().getWindow().hide();
        });
    }

}
