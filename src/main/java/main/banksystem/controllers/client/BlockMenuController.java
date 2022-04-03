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
import main.banksystem.CPU;
import main.banksystem.DataBase;
import main.banksystem.ProgramStatus;
import main.banksystem.commands.ICommand;
import main.banksystem.commands.SetBillStatusCommand;
import main.banksystem.entities.Bill;
import main.banksystem.entities.BillConditions;
import main.banksystem.entities.Id;

public class BlockMenuController {

    ObservableList<String> billList = FXCollections.observableArrayList();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ChoiceBox<String> billChoice;

    @FXML
    private Label errorLabel;

    @FXML
    private Button blockButton;

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

        blockButton.setOnAction(event -> {
            SetBillStatusCommand command = new SetBillStatusCommand(new Id(Long.parseLong(billChoice.getValue())),
                    BillConditions.ACTIVE, BillConditions.BLOCKED, new ICommand.Type(false, true));
            CPU cpu = new CPU(status.getUser());
            cpu.heldCommand(command);

            blockButton.getScene().getWindow().hide();
        });
    }

}
