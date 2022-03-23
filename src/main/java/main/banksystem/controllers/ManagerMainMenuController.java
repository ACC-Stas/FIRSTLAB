package main.banksystem.controllers;

import java.net.URL;
import java.util.Map;
import java.util.Queue;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import main.banksystem.DataBase;
import main.banksystem.StringConverter;
import main.banksystem.commands.ICommand;
import main.banksystem.containers.Id;

public class ManagerMainMenuController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Accordion creditsAndInstallmentAccordion;

    @FXML
    private Label errorLabel;

    @FXML
    private Button findButton;

    @FXML
    private Accordion historyAccordion;

    @FXML
    private TextField idSpecialistField;

    @FXML
    private Accordion registrationAccordion;

    @FXML
    private Button toClientButton;

    @FXML
    private Button toManagerButton;

    @FXML
    void initialize() {
        for (int i = 0; i < 2; i++) {
            TitledPane titledPane = new TitledPane();
            titledPane.setText("Клиент " + i.toString());

            VBox content1 = new VBox();
            content1.getChildren().add(new Label("Java Swing Tutorial"));
            content1.getChildren().add(new Label("JavaFx Tutorial"));
            content1.getChildren().add(new Label("Java IO Tutorial"));

            titledPane.setContent(content1);
            registrationAccordion.getPanes().addAll(titledPane);
        }
    }

}
