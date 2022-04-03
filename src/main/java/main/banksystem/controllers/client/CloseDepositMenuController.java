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
import javafx.scene.control.Slider;
import main.banksystem.CPU;
import main.banksystem.DataBase;
import main.banksystem.ProgramStatus;
import main.banksystem.commands.ICommand;
import main.banksystem.commands.RepayCreditCommand;
import main.banksystem.entities.Bill;
import main.banksystem.entities.Credit;
import main.banksystem.entities.Deposit;
import main.banksystem.entities.Id;

public class CloseDepositMenuController {

    ObservableList<String> depositList = FXCollections.observableArrayList();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ChoiceBox<String> depositChoice;

    @FXML
    private Label errorLabel;

    @FXML
    private Button transferButton;

    @FXML
    private Slider valueSlider;

    @FXML
    void initialize() {
        ProgramStatus status = ProgramStatus.getInstance();
        DataBase dataBase = DataBase.getInstance();
        Map<Id, Deposit> deposits = dataBase.downloadMap(DataBase.DEPOSIT_PART, Deposit.class);

        for (Id id : status.getUser().getDepositIds()) {
            if (deposits.get(id).getValue() <= 0) {
                continue;
            }
            depositList.add(id.toString());
        }
        depositChoice.setItems(depositList);

        depositChoice.setOnAction(event -> {
            valueSlider.setMin(0);
            double maxValue = deposits.get(new Id(Long.parseLong(depositChoice.getValue()))).getValue();
            valueSlider.setMax(maxValue);
            valueSlider.setShowTickLabels(true);
            valueSlider.setShowTickMarks(true);
            valueSlider.setBlockIncrement(maxValue / 10);
        });

        transferButton.setOnAction(event -> {
            Map<Id, Bill> bills = dataBase.downloadMap(DataBase.BILLS_PART, Bill.class);
            double valueOnBill = bills.get(deposits.get(new Id(Long.parseLong(depositChoice.getValue())))
                    .getBankBillId()).getMoney();
            if (valueOnBill < valueSlider.getValue()){
                errorLabel.setText("Not enough money to withdraw");
                return;
            }
            /*RepayCreditCommand creditCommand = new RepayCreditCommand(new Id(Long.parseLong(creditChoice.getValue())),
                    new ICommand.Type(false, true), valueSlider.getValue());

            ProgramStatus programStatus = ProgramStatus.getInstance();
            CPU cpu = new CPU(programStatus.getUser());
            cpu.heldCommand(creditCommand);
            transferButton.getScene().getWindow().hide();*/
        });
    }

}
