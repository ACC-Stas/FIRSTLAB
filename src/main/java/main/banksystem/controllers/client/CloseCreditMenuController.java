package main.banksystem.controllers.client;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

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

    }

}
