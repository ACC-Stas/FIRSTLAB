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
import main.banksystem.ProgramStatus;
import main.banksystem.containers.Id;

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
        for(Id id : ids) {
            billList.add(String.valueOf(id.getId()));
        }
        billFromChoice.setItems(billList);

        withdrawButton.setOnAction(event -> {
            String billFrom = billFromChoice.getValue();
            String value = valueField.getText();
        });

    }

}
