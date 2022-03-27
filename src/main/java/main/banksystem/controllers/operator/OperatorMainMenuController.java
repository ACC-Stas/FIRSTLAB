package main.banksystem.controllers.operator;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Stack;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import main.banksystem.DataBase;
import main.banksystem.commands.ICommand;
import main.banksystem.containers.Id;
import main.banksystem.containers.User;
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
        Map<Id, Stack<ICommand>> salaries = dataBase.downloadStack(DataBase.STACK_PART, ICommand.class);
    }

    void createUserAccordion(Id id){
        historyAccordion.getPanes().clear();
        DataBase dataBase = DataBase.getInstance();
        Map<Id, Stack<ICommand>> commands = dataBase.downloadStack(DataBase.STACK_PART, ICommand.class);

        if (!commands.containsKey(id)) {
            return;
        }

        for (ICommand command : commands.get(id)) {
            //if ne user
            TitledPane titledPane = new TitledPane();
            titledPane.getStylesheets().add(ManagerMainMenuController.class.getResource("/main/banksystem/pane_sheet.css").toExternalForm());
            titledPane.setText(command.getDescription());

            VBox content = new VBox();
            Button cancel = new Button("Cancel");
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
