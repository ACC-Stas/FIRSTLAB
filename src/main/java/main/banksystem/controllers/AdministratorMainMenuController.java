package main.banksystem.controllers;

import java.net.URL;
import java.util.Map;
import java.util.Queue;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import main.banksystem.CPU;
import main.banksystem.DataBase;
import main.banksystem.commands.ICommand;
import main.banksystem.containers.Id;
import main.banksystem.containers.User;

import static main.banksystem.controllers.SwitchMenu.switchMenu;

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
    private Button getEncryptedLogsButton;

    @FXML
    private Button getUnencryptedLogsButton;

    @FXML
    private Accordion historyAccordion;

    @FXML
    private TextField idUserField;

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



        toClientButton.setOnAction(event -> {
            switchMenu(toClientButton, "/main/banksystem/client/client_main_menu.fxml");
        });

        toOperatorButton.setOnAction(event -> {
            switchMenu(toClientButton, "/main/banksystem/operator/operator_main_menu.fxml");
        });

        toManagerButton.setOnAction(event -> {
            switchMenu(toClientButton, "/main/banksystem/manager/manager_main_menu.fxml");
        });

        toSpecialistButton.setOnAction(event -> {
            switchMenu(toClientButton, "/main/banksystem/specialist/specialist_main_menu.fxml");
        });
    }

    void createCompanyRegistrationAccordion(){
        registrationAccordion.getPanes().clear();

        DataBase dataBase = DataBase.getInstance();
        Map<Id, Queue<ICommand>> queues = dataBase.downloadQueue(DataBase.QUEUE_PART, ICommand.class);

        if (queues.containsKey(DataBase.INIT_COMPANY_ID)) {
            for (ICommand command : queues.get(DataBase.INIT_COMPANY_ID)) {
                TitledPane titledPane = new TitledPane();
                titledPane.getStylesheets().add(ManagerMainMenuController.class.getResource("/main/banksystem/pane_sheet.css").toExternalForm());
                titledPane.setText(command.getDescription());

                VBox content = new VBox();
                Button approve = new Button("Approve");
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
