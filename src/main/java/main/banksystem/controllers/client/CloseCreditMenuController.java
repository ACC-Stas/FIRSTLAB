package main.banksystem.controllers.client;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import main.banksystem.DataBase;
import main.banksystem.ProgramStatus;
import main.banksystem.containers.Credit;
import main.banksystem.containers.Id;

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
        Id id = status.getUser().getIdx();

        DataBase dataBase = DataBase.getInstance();
        Map<Id, List<Credit>> credits = dataBase.downloadList(DataBase.CREDIT_PART, Credit.class);

        for(Credit credit : credits.get(id)) {
            creditList.add("Осталось выплатить " + credit.getSumToPay() + "$");
        }
        creditChoice.setItems(creditList);


    }

}
