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
import javafx.scene.control.TextField;
import main.banksystem.DataBase;
import main.banksystem.ProgramStatus;
import main.banksystem.entities.Bill;
import main.banksystem.entities.BillConditions;
import main.banksystem.entities.Id;

public class WithdrawMenuController {

    ObservableList<String> billList = FXCollections.observableArrayList();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ChoiceBox<String> billFromChoice;

    @FXML
    private Label errorLabel;

    @FXML
    private TextField valueField;

    @FXML
    private Button withdrawButton;

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

        withdrawButton.setOnAction(event -> {

            double value = -1;
            try {
                value = Double.parseDouble(valueField.getText());
            }
            catch (Exception e) {
                errorLabel.setText("Invalid sum");
                return;
            }

            if (billFromChoice.getValue() == null) {
                errorLabel.setText("No bill found");
                return;
            }

            Bill bill = bills.get(new Id(Long.parseLong(billFromChoice.getValue())));

            if (value < 0){
                errorLabel.setText("Invalid sum");
                return;
            }

            if (bill.getMoney() < value) {
                errorLabel.setText("Not enough money");
                return;
            }

            bill.setMoney(bill.getMoney() - value);
            dataBase.save(bill.getId(), DataBase.BILLS_PART, bill);

            withdrawButton.getScene().getWindow().hide();
        });

    }

}
