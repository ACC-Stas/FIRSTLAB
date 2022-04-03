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
import main.banksystem.ProgramStatus;
import main.banksystem.builders.TransferBuilder;
import main.banksystem.commands.ICommand;
import main.banksystem.commands.TransferCommand;
import main.banksystem.entities.Bill;
import main.banksystem.entities.BillConditions;
import main.banksystem.entities.Id;

public class TransferMenuController {

    ObservableList<String> billList = FXCollections.observableArrayList();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ChoiceBox<String> billFromChoice;

    @FXML
    private TextField billToField;

    @FXML
    private Label errorLabel;

    @FXML
    private Button transferButton;

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
        billFromChoice.setItems(billList);

        transferButton.setOnAction(event -> {
            TransferBuilder transferBuilder = new TransferBuilder();
            transferBuilder.buildBillFromId(billFromChoice.getValue());
            transferBuilder.buildBillToId(billToField.getText());
            transferBuilder.buildValue(valueField.getText());
            TransferBuilder.Result transfer = transferBuilder.getTransfer();
            if (!transfer.valid) {
                errorLabel.setText(transfer.description);
            }
            else {
                ICommand.Type type = new ICommand.Type(false, true);
                TransferCommand command = new TransferCommand(transfer.transfer, type);

                CPU cpu = new CPU(status.getUser());
                cpu.heldCommand(command);
                if (Objects.equals(command.getDescription(), "Everything is good")) {
                    transferButton.getScene().getWindow().hide();
                } else {
                    errorLabel.setText(command.getDescription());
                }
            }
        });

    }

}
