package main.banksystem.controllers.administrator;

import java.net.URL;
import java.util.Map;
import java.util.Queue;
import java.util.ResourceBundle;
import java.util.Stack;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import main.banksystem.CPU;
import main.banksystem.DataBase;
import main.banksystem.commands.ICommand;
import main.banksystem.entities.Id;
import main.banksystem.entities.User;
import main.banksystem.controllers.manager.ManagerMainMenuController;

import static main.banksystem.controllers.SwitchMenu.newMenu;

public class AdministratorMainMenuController {

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
    private TextField idUserField;

    @FXML
    private Text logText;

    @FXML
    private Accordion registrationAccordion;

    @FXML
    private Button toClientButton;

    @FXML
    private Button toManagerButton;

    @FXML
    private Button toOperatorButton;

    @FXML
    private Button toSpecialistButton;

    @FXML
    void initialize() {
        createCompanyRegistrationAccordion();

        DataBase dataBase = DataBase.getInstance();

        String text = "";
        Map<Id, Stack<ICommand>> map = dataBase.downloadStack(DataBase.STACK_PART, ICommand.class);
        for (Map.Entry<Id, Stack<ICommand>> stack : map.entrySet()){
            text += "ID = " + stack.getKey() + " - " + stack.getValue().toString() + "\n";
        }
        logText.setText(text);

        findButton.setOnAction(event -> {
            Map<Id, User> users = dataBase.downloadMap(DataBase.USER_PART, User.class);
            Id idx = null;
            try {
                idx = new Id(Long.parseLong(idUserField.getText()));
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

        toOperatorButton.setOnAction(event -> {
            newMenu(toOperatorButton, "/main/banksystem/operator/operator_main_menu.fxml");
        });

        toManagerButton.setOnAction(event -> {
            newMenu(toManagerButton, "/main/banksystem/manager/manager_main_menu.fxml");
        });

        toSpecialistButton.setOnAction(event -> {
            newMenu(toSpecialistButton, "/main/banksystem/specialist/specialist_main_menu.fxml");
        });
    }

    void createUserAccordion(Id id){
        historyAccordion.getPanes().clear();
        DataBase dataBase = DataBase.getInstance();
        Map<Id, Stack<ICommand>> commands = dataBase.downloadStack(DataBase.STACK_PART, ICommand.class);

        if (!commands.containsKey(id)) {
            return;
        }

        for (ICommand command : commands.get(id)) {
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

    void createCompanyRegistrationAccordion(){
        registrationAccordion.getPanes().clear();

        DataBase dataBase = DataBase.getInstance();
        Map<Id, Queue<ICommand>> queues = dataBase.downloadQueue(DataBase.QUEUE_PART, ICommand.class);

        if (queues.containsKey(DataBase.INIT_COMPANY_ID)) {
            for (ICommand command : queues.get(DataBase.INIT_COMPANY_ID)) {
                TitledPane titledPane = new TitledPane();
                titledPane.getStylesheets().add(ManagerMainMenuController
                        .class.getResource("/main/banksystem/pane_sheet.css").toExternalForm());
                titledPane.setText(command.getDescription());

                VBox content = new VBox();
                Button approve = new Button("Approve");
                approve.getStylesheets().add(ManagerMainMenuController
                        .class.getResource("/main/banksystem/button_sheet.css").toExternalForm());
                approve.setOnAction(event -> {

                    ICommand.Type commandType = new ICommand.Type(false, true);
                    command.setType(commandType);
                    User user = new User();
                    user.setIdx(DataBase.INIT_COMPANY_ID);
                    CPU cpu = new CPU(user);
                    cpu.heldCommand(command);

                    queues.get(DataBase.INIT_COMPANY_ID).remove(command);
                    dataBase.saveQueue(DataBase.INIT_COMPANY_ID, DataBase.QUEUE_PART, queues.get(DataBase.INIT_COMPANY_ID));
                    registrationAccordion.getPanes().remove(titledPane);

                    initialize();
                });

                Button disapprove = new Button("Disapprove");
                disapprove.getStylesheets().add(ManagerMainMenuController
                        .class.getResource("/main/banksystem/button_sheet.css").toExternalForm());
                disapprove.setOnAction(event -> {

                    queues.get(DataBase.INIT_COMPANY_ID).remove(command);
                    dataBase.saveQueue(DataBase.INIT_COMPANY_ID, DataBase.QUEUE_PART, queues.get(DataBase.INIT_COMPANY_ID));
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
