package main.banksystem.controllers.manager;

import java.net.URL;

import java.util.*;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import main.banksystem.CPU;
import main.banksystem.DataBase;
import main.banksystem.commands.BuildCreditCommand;
import main.banksystem.commands.BuildInstallmentCommand;
import main.banksystem.commands.ICommand;
import main.banksystem.containers.*;

import static main.banksystem.controllers.SwitchMenu.switchMenu;

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
    private Button toOperatorButton;

    @FXML
    void initialize() {
        createRegistrationAccordion();
        createCreditAccordion();
        toClientButton.setOnAction(event -> {
            switchMenu(toClientButton, "/main/banksystem/client/client_main_menu.fxml");
        });

        toOperatorButton.setOnAction(event -> {
            switchMenu(toClientButton, "/main/banksystem/operator/operator_main_menu.fxml");
        });

        findButton.setOnAction(event -> {
            DataBase dataBase = DataBase.getInstance();
            Map<Id, User> users = dataBase.downloadMap(DataBase.USER_PART, User.class);
            Id idx = null;
            try {
                idx = new Id(Long.parseLong(idSpecialistField.getText()));
            } catch (IllegalArgumentException e) {
                errorLabel.setText("Wrong input");
            }

            if (users.containsKey(idx) && users.get(idx).getRole() == Role.SPECIALIST) {
                createSpecialistAccordion(idx);
            }
            else {
                errorLabel.setText("Wrong ID");
            }
        });
    }

    void createSpecialistAccordion(Id id){
        historyAccordion.getPanes().clear();
        DataBase dataBase = DataBase.getInstance();
        Map<Id, Stack<ICommand>> commands = dataBase.downloadStack(DataBase.STACK_PART, ICommand.class);

        if (!commands.containsKey(id)) {
            return;
        }

        for (ICommand command : commands.get(id)) {
            TitledPane titledPane = new TitledPane();
            titledPane.getStylesheets().add(ManagerMainMenuController.class.getResource("/main/banksystem/pane_sheet.css").toExternalForm());
            titledPane.setText(command.getDescription());

            VBox content = new VBox();
            Button cancel = new Button("Cancel");
            cancel.getStylesheets().add(ManagerMainMenuController
                    .class.getResource("/main/banksystem/button_sheet.css").toExternalForm());
            cancel.setOnAction(event -> {

                command.undo();

                commands.get(id).remove(command);
                dataBase.saveStack(id, DataBase.STACK_PART, commands.get(id));
                historyAccordion.getPanes().remove(titledPane);

                initialize();
            });

            content.getChildren().add(cancel);
            titledPane.setContent(content);

            historyAccordion.getPanes().addAll(titledPane);
        }
    }

    void createCreditAccordion(){
        creditsAndInstallmentAccordion.getPanes().clear();

        DataBase dataBase = DataBase.getInstance();
        Map<Id, Queue<ICommand>> commands = dataBase.downloadQueue(DataBase.QUEUE_PART, ICommand.class);

        if (commands == null) {
            return;
        }

        for (Map.Entry<Id, Queue<ICommand>> commandsEntry : commands.entrySet()) {
            for (ICommand command : commandsEntry.getValue()) {
                if (command.getClass() == BuildCreditCommand.class ||
                        command.getClass() == BuildInstallmentCommand.class) {
                    TitledPane titledPane = new TitledPane();
                    titledPane.getStylesheets().add(ManagerMainMenuController.class.getResource("/main/banksystem/pane_sheet.css").toExternalForm());
                    titledPane.setText(command.getDescription());

                    VBox content = new VBox();
                    Button approve = new Button("Approve");
                    approve.getStylesheets().add(ManagerMainMenuController
                            .class.getResource("/main/banksystem/button_sheet.css").toExternalForm());
                    approve.setOnAction(event -> {

                        command.setType(new ICommand.Type(false, true));
                        User user = new User();
                        user.setIdx(commandsEntry.getKey());
                        CPU cpu = new CPU(user);
                        cpu.heldCommand(command);

                        commandsEntry.getValue().remove(command);
                        dataBase.saveQueue(commandsEntry.getKey(), DataBase.QUEUE_PART, commandsEntry.getValue());
                        creditsAndInstallmentAccordion.getPanes().remove(titledPane);

                        initialize();
                    });

                    Button disapprove = new Button("Disapprove");
                    disapprove.getStylesheets().add(ManagerMainMenuController
                            .class.getResource("/main/banksystem/button_sheet.css").toExternalForm());
                    disapprove.setOnAction(event -> {

                        commandsEntry.getValue().remove(command);
                        dataBase.saveQueue(commandsEntry.getKey(), DataBase.QUEUE_PART, commandsEntry.getValue());
                        creditsAndInstallmentAccordion.getPanes().remove(titledPane);

                        initialize();
                    });

                    content.getChildren().add(approve);
                    content.getChildren().add(disapprove);
                    titledPane.setContent(content);

                    creditsAndInstallmentAccordion.getPanes().addAll(titledPane);
                }
            }
        }
    }

    void createRegistrationAccordion(){
        registrationAccordion.getPanes().clear();

        DataBase dataBase = DataBase.getInstance();
        Map<Id, Queue<ICommand>> queues = dataBase.downloadQueue(DataBase.QUEUE_PART, ICommand.class);

        if (queues.containsKey(DataBase.INIT_USER_ID)) {
            for (ICommand command : queues.get(DataBase.INIT_USER_ID)) {
                TitledPane titledPane = new TitledPane();
                titledPane.getStylesheets().add(ManagerMainMenuController.class.getResource("/main/banksystem/pane_sheet.css").toExternalForm());
                titledPane.setText(command.getDescription());

                VBox content = new VBox();
                Button approve = new Button("Approve");
                approve.getStylesheets().add(ManagerMainMenuController
                        .class.getResource("/main/banksystem/button_sheet.css").toExternalForm());
                approve.setOnAction(event -> {

                    ICommand.Type commandType = new ICommand.Type(false, true);
                    command.setType(commandType);
                    User user = new User();
                    user.setIdx(DataBase.INIT_USER_ID);
                    CPU cpu = new CPU(user);
                    cpu.heldCommand(command);

                    queues.get(DataBase.INIT_USER_ID).remove(command);
                    dataBase.saveQueue(DataBase.INIT_USER_ID, DataBase.QUEUE_PART, queues.get(DataBase.INIT_USER_ID));
                    registrationAccordion.getPanes().remove(titledPane);

                    initialize();
                });

                Button disapprove = new Button("Disapprove");
                disapprove.getStylesheets().add(ManagerMainMenuController
                        .class.getResource("/main/banksystem/button_sheet.css").toExternalForm());
                disapprove.setOnAction(event -> {

                    queues.get(DataBase.INIT_USER_ID).remove(command);
                    dataBase.saveQueue(DataBase.INIT_USER_ID, DataBase.QUEUE_PART, queues.get(DataBase.INIT_USER_ID));
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
