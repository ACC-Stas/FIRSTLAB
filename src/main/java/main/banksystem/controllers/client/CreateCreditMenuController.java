package main.banksystem.controllers.client;

import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.Queue;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import main.banksystem.DataBase;
import main.banksystem.ProgramStatus;
import main.banksystem.containers.Bill;
import main.banksystem.containers.Credit;
import main.banksystem.containers.Id;

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
    private Button transferButton;

    @FXML
    private TextField valueField;

    @FXML
    void initialize() {
        ProgramStatus status = ProgramStatus.getInstance();
        ArrayList<Id> ids = status.getUser().getBillIds();

        DataBase dataBase = DataBase.getInstance();

        for(Id id : ids) {
            billList.add(String.valueOf(id.getId()));
        }
        billChoice.setItems(billList);


    }

}
