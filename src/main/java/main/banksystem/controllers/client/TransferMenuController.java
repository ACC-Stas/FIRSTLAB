package main.banksystem.controllers.client;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import main.banksystem.CPU;
import main.banksystem.ProgramStatus;
import main.banksystem.commands.BuildBillCommand;
import main.banksystem.commands.ICommand;
import main.banksystem.commands.TransferCommand;
import main.banksystem.containers.Id;
import main.banksystem.containers.Transfer;

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
        for(Id id : ids) {
            billList.add(String.valueOf(id.getId()));
        }
        billFromChoice.setItems(billList);

        transferButton.setOnAction(event -> {
            Transfer transfer = new Transfer();
            ICommand.Type type = new ICommand.Type(false, true);
            TransferCommand command = new TransferCommand(transfer, type);

            CPU cpu = new CPU(status.getUser());
            cpu.heldCommand(command);
            transferButton.getScene().getWindow().hide();
        });

    }

}
