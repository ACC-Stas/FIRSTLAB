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
import main.banksystem.commands.RepayInstallmentCommand;
import main.banksystem.entities.Bill;
import main.banksystem.entities.Credit;
import main.banksystem.entities.Id;
import main.banksystem.entities.Installment;

public class CloseInstallmentMenuController {

    ObservableList<String> installmentList = FXCollections.observableArrayList();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ChoiceBox<String> installmentChoice;

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
        Map<Id, Installment> installments = dataBase.downloadMap(DataBase.INSTALLMENT_PART, Installment.class);

        for (Id id : status.getUser().getInstallmentIds()) {
            if (installments.get(id).getSumToPay() <= 0) {
                continue;
            }
            installmentList.add(id.toString());
        }
        installmentChoice.setItems(installmentList);

        installmentChoice.setOnAction(event -> {
            valueSlider.setMin(0);
            double maxValue = installments.get(new Id(Long.parseLong(installmentChoice.getValue()))).getSumToPay();
            valueSlider.setMax(maxValue);
            valueSlider.setShowTickLabels(true);
            valueSlider.setShowTickMarks(true);
            valueSlider.setBlockIncrement(maxValue/10);
        });

        transferButton.setOnAction(event -> {
            Map<Id, Bill> bills = dataBase.downloadMap(DataBase.BILLS_PART, Bill.class);
            double valueOnBill = bills.get(installments.get(new Id(Long.parseLong(installmentChoice.getValue())))
                    .getSourceBillId()).getMoney();
            if (valueOnBill < valueSlider.getValue()){
                errorLabel.setText("Not enough money to pay");
                return;
            }
            RepayInstallmentCommand installmentCommand = new RepayInstallmentCommand(new
                    Id(Long.parseLong(installmentChoice.getValue())), new ICommand.Type(false, true),
                    valueSlider.getValue());

            ProgramStatus programStatus = ProgramStatus.getInstance();
            CPU cpu = new CPU(programStatus.getUser());
            cpu.heldCommand(installmentCommand);
            transferButton.getScene().getWindow().hide();
        });
    }

}
