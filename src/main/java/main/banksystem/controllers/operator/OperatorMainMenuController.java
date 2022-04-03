package main.banksystem.controllers.operator;

import java.net.URL;
import java.util.Map;
import java.util.Queue;
import java.util.ResourceBundle;
import java.util.Stack;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import main.banksystem.CPU;
import main.banksystem.DataBase;
import main.banksystem.commands.BuildCreditCommand;
import main.banksystem.commands.BuildInstallmentCommand;
import main.banksystem.commands.ICommand;
import main.banksystem.commands.PayEmployeesCommand;
import main.banksystem.entities.Id;
import main.banksystem.entities.User;
import main.banksystem.controllers.manager.ManagerMainMenuController;

import static main.banksystem.controllers.SwitchMenu.newMenu;

public class OperatorMainMenuController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label errorLabel;

    @FXML
    private Button findButton;

    @FXML
    private Accordion historyAccordion;

    @FXML
    private TextField idField;

    @FXML
    private Accordion salaryAccordion;

    @FXML
    private Button toClientButton;

    @FXML
    void initialize() {

        createSalaryAccordion();

        findButton.setOnAction(event -> {
            DataBase dataBase = DataBase.getInstance();
            Map<Id, User> users = dataBase.downloadMap(DataBase.USER_PART, User.class);
            Id idx = null;
            try {
                idx = new Id(Long.parseLong(idField.getText()));
            } catch (IllegalArgumentException e) {
                errorLabel.setText("Wrong input");
            }

            if (users.containsKey(idx)) {
                createUserAccordion(idx);
                errorLabel.setText("");
            }
            else {
                errorLabel.setText("Wrong ID");
            }
        });

        toClientButton.setOnAction(event -> {
            newMenu(toClientButton, "/main/banksystem/client/client_main_menu.fxml");
        });

    }

    void createSalaryAccordion() {
        salaryAccordion.getPanes().clear();

        DataBase dataBase = DataBase.getInstance();
        Map<Id, Queue<ICommand>> commands = dataBase.downloadQueue(DataBase.QUEUE_PART, ICommand.class);

        if (commands == null) {
            return;
        }

        for (Map.Entry<Id, Queue<ICommand>> commandsEntry : commands.entrySet()) {
            for (ICommand command : commandsEntry.getValue()) {
                if (command.getClass() == PayEmployeesCommand.class) {
                    TitledPane titledPane = new TitledPane();
                    titledPane.getStylesheets().add(OperatorMainMenuController
                            .class.getResource("/main/banksystem/pane_sheet.css").toExternalForm());
                    titledPane.setText(command.getDescription());

                    VBox content = new VBox();
                    Button approve = new Button("Approve");
                    approve.getStylesheets().add(OperatorMainMenuController
                            .class.getResource("/main/banksystem/button_sheet.css").toExternalForm());
                    approve.setOnAction(event -> {

                        command.setType(new ICommand.Type(false, true));
                        User user = new User();
                        user.setIdx(commandsEntry.getKey());
                        CPU cpu = new CPU(user);
                        cpu.heldCommand(command);

                        commandsEntry.getValue().remove(command);
                        dataBase.saveQueue(commandsEntry.getKey(), DataBase.QUEUE_PART, commandsEntry.getValue());
                        salaryAccordion.getPanes().remove(titledPane);

                        initialize();
                    });

                    Button disapprove = new Button("Disapprove");
                    disapprove.getStylesheets().add(ManagerMainMenuController
                            .class.getResource("/main/banksystem/button_sheet.css").toExternalForm());
                    disapprove.setOnAction(event -> {

                        commandsEntry.getValue().remove(command);
                        dataBase.saveQueue(commandsEntry.getKey(), DataBase.QUEUE_PART, commandsEntry.getValue());
                        salaryAccordion.getPanes().remove(titledPane);

                        initialize();
                    });

                    content.getChildren().add(approve);
                    content.getChildren().add(disapprove);
                    titledPane.setContent(content);

                    salaryAccordion.getPanes().addAll(titledPane);
                }
            }
        }
    }

    void createUserAccordion(Id id){
        historyAccordion.getPanes().clear();
        DataBase dataBase = DataBase.getInstance();
        Map<Id, Stack<ICommand>> commands = dataBase.downloadStack(DataBase.STACK_PART, ICommand.class);

        if (!commands.containsKey(id)) {
            return;
        }

        for (ICommand command : commands.get(id)) {
            //if no user
            TitledPane titledPane = new TitledPane();
            titledPane.getStylesheets().add(ManagerMainMenuController
                    .class.getResource("/main/banksystem/pane_sheet.css").toExternalForm());
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
}
