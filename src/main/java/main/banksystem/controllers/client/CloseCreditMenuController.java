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
import main.banksystem.entities.Id;

public class CloseCreditMenuController {

    ObservableList<String> creditList = FXCollections.observableArrayList();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ChoiceBox<String> creditChoice;

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
        Map<Id, Credit> credits = dataBase.downloadMap(DataBase.CREDIT_PART, Credit.class);

        for (Id id : status.getUser().getCreditIds()) {
            if (credits.get(id).getSumToPay() <= 0) {
                continue;
            }
            creditList.add(id.toString());
        }
        creditChoice.setItems(creditList);

        creditChoice.setOnAction(event -> {
            valueSlider.setMin(0);
            double maxValue = credits.get(new Id(Long.parseLong(creditChoice.getValue()))).getSumToPay();
            valueSlider.setMax(maxValue);
            valueSlider.setShowTickLabels(true);
            valueSlider.setShowTickMarks(true);
            valueSlider.setMajorTickUnit(maxValue / 5);
            valueSlider.setMinorTickCount(5);
        });

        transferButton.setOnAction(event -> {
            Map<Id, Bill> bills = dataBase.downloadMap(DataBase.BILLS_PART, Bill.class);
            if (creditChoice.getValue() == null) {
                errorLabel.setText("Choose credit");
                return;
            }

            double valueOnBill = bills.get(credits.get(new Id(Long.parseLong(creditChoice.getValue())))
                    .getSourceBillId()).getMoney();
            if (valueOnBill < valueSlider.getValue()){
                errorLabel.setText("Not enough money to pay");
                return;
            }
            RepayCreditCommand creditCommand = new RepayCreditCommand(new Id(Long.parseLong(creditChoice.getValue())),
                    new ICommand.Type(false, true), valueSlider.getValue());

            ProgramStatus programStatus = ProgramStatus.getInstance();
            CPU cpu = new CPU(programStatus.getUser());
            cpu.heldCommand(creditCommand);
            transferButton.getScene().getWindow().hide();
        });
    }

}
