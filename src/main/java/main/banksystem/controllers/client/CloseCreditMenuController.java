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
import main.banksystem.DataBase;
import main.banksystem.ProgramStatus;
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
            creditList.add(String.valueOf(credits.get(id).getSumToPay()));
        }
        creditChoice.setItems(creditList);

        creditChoice.setOnAction(event -> {
            valueSlider.setMin(0);
            valueSlider.setMax(credits.get(new Id(Long.parseLong(creditChoice.getValue()))).getSumToPay());
        });
    }

}
