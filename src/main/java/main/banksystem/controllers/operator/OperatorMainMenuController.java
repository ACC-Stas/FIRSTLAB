package main.banksystem.controllers.operator;

import java.net.URL;
import java.util.Map;
import java.util.Queue;
import java.util.ResourceBundle;
import java.util.Stack;
import java.util.regex.Pattern;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import main.banksystem.CPU;
import main.banksystem.DataBase;
import main.banksystem.ProgramStatus;
import main.banksystem.commands.*;
import main.banksystem.entities.*;
import main.banksystem.controllers.manager.ManagerMainMenuController;
import org.controlsfx.glyphfont.Glyph;

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
    private Button reloadButton;

    @FXML
    private Accordion historyAccordion;

    @FXML
    private Label operatorStatusLabel;

    @FXML
    private Button setCompanyButton;

    @FXML
    private TextField idField;

    @FXML
    private Accordion salaryAccordion;

    @FXML
    private Button toClientButton;

    @FXML
    void initialize() {

        setCompanyButton.setOnAction(event -> {
            newMenu(setCompanyButton, "/main/banksystem/operator/connect_menu.fxml");
        });

        reload(false);

        reloadButton.setOnAction(event -> {
            reload(true);
        });

        Pattern p = Pattern.compile("(\\d+)?");
        idField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!p.matcher(newValue).matches()) idField.setText(oldValue);
        });

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

        ProgramStatus status = ProgramStatus.getInstance();

        DataBase dataBase = DataBase.getInstance();
        Map<Id, Queue<ICommand>> commands = dataBase.downloadQueue(DataBase.QUEUE_PART, ICommand.class);
        Map<Id, Company> companies = dataBase.downloadMap(DataBase.COMPANY_PART, Company.class);


        if (commands == null) {
            return;
        }

        for (Map.Entry<Id, Queue<ICommand>> commandsEntry : commands.entrySet()) {
            for (ICommand command : commandsEntry.getValue()) {
                if (command.getClass() == PayEmployeesCommand.class) {
                    for (Company company : companies.values()){
                        if (company.getBankID().getId().equals(status.getCompany())){
                            TitledPane titledPane = new TitledPane();
                            titledPane.getStylesheets().add(OperatorMainMenuController
                                    .class.getResource("/main/banksystem/pane_sheet.css").toExternalForm());
                            titledPane.setText(command.getDescription());

                            VBox content = new VBox();

                            Label list = new Label(((PayEmployeesCommand) command).getCommands().toString());

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

                            content.getChildren().add(list);
                            content.getChildren().add(approve);
                            content.getChildren().add(disapprove);
                            titledPane.setContent(content);

                            salaryAccordion.getPanes().addAll(titledPane);
                        }
                    }
                }
            }
        }
    }

    void createUserAccordion(Id id){
        historyAccordion.getPanes().clear();
        DataBase dataBase = DataBase.getInstance();
        Map<Id, Stack<ICommand>> commands = dataBase.downloadStack(DataBase.STACK_PART, ICommand.class);
        Map<Id, Bill> bills = dataBase.downloadMap(DataBase.BILLS_PART, Bill.class);

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

                ProgramStatus status = ProgramStatus.getInstance();
                command.undo();
                command.setDescription("Action: " + command.getDescription() + "was canceled by " + status
                        .getUser().getIdx().toString());

                if (!commands.containsKey(new Id(-3))) {
                    commands.put(new Id(-3), new Stack<>());
                }
                var commandStack = commands.get(new Id(-3));
                commandStack.push(command);

                commands.get(id).remove(command);
                dataBase.saveStack(id, DataBase.STACK_PART, commands.get(id));
                dataBase.saveStack(new Id(-3), DataBase.STACK_PART, commands.get(new Id(-3)));

                historyAccordion.getPanes().remove(titledPane);

                initialize();
            });

            content.getChildren().add(cancel);
            titledPane.setContent(content);

            historyAccordion.getPanes().addAll(titledPane);
        }
    }

    void reload(boolean need_initialize) {

        ProgramStatus status = ProgramStatus.getInstance();
        Id userId = status.getUser().getIdx();

        DataBase dataBase = DataBase.getInstance();
        Map<Id, Company> companies = dataBase.downloadMap(DataBase.COMPANY_PART, Company.class);
        for (Company company : companies.values()) {
            for (Id specialistId : company.getSpecialistIds()) {
                if (userId.equals(specialistId)) {
                    status.setCompany(company.getPAN());
                    operatorStatusLabel.setTextFill(Color.GREEN);
                    operatorStatusLabel.setText("Вы привязаны к предприятию " + company.getjName());
                }
            }
        }

        if (status.getCompany() != null) {

            setCompanyButton.setVisible(false);
            setCompanyButton.setManaged(false);

            findButton.setVisible(true);

            createSalaryAccordion();

            if (need_initialize) {
                initialize();
            }
        }
    }
}
