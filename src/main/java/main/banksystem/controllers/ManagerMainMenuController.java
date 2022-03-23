package main.banksystem.controllers;

import java.net.URL;

import java.util.Map;
import java.util.Queue;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import main.banksystem.DataBase;
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
        createRegistrationAccordion();

    }

    void createRegistrationAccordion(){
        registrationAccordion.getPanes().clear();

        DataBase dataBase = DataBase.GetInstance();
        Map<Id, Queue<ICommand>> queues = dataBase.DownloadQueue(DataBase.QUEUE_PART, ICommand.class);

        if (queues.containsKey(DataBase.INIT_USER_ID)) {
            for (ICommand command : queues.get(DataBase.INIT_USER_ID)) {
                TitledPane titledPane = new TitledPane();
                titledPane.getStylesheets().add(ManagerMainMenuController.class.getResource("/main/banksystem/pane_sheet.css").toExternalForm());
                titledPane.setText(command.getDescription());

                VBox content = new VBox();
                Button approve = new Button("Approve");
                approve.setOnAction(event -> {

                    command.execute();

                    queues.get(DataBase.INIT_USER_ID).remove(command);
                    dataBase.SaveQueue(DataBase.INIT_USER_ID, DataBase.QUEUE_PART, queues.get(DataBase.INIT_USER_ID));
                    registrationAccordion.getPanes().remove(titledPane);

                    initialize();
                });

                Button disapprove = new Button("Disapprove");
                disapprove.setOnAction(event -> {

                    queues.get(DataBase.INIT_USER_ID).remove(command);
                    dataBase.SaveQueue(DataBase.INIT_USER_ID, DataBase.QUEUE_PART, queues.get(DataBase.INIT_USER_ID));
                    registrationAccordion.getPanes().remove(titledPane);

                    initialize();
                });

                content.getChildren().add(approve);
                content.getChildren().add(disapprove);
                titledPane.setContent(content);

                registrationAccordion.getPanes().addAll(titledPane);
            }
        }
    }
}
