package main.banksystem.controllers.specialist;

import java.net.URL;
import java.util.Map;
import java.util.Queue;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import main.banksystem.CPU;
import main.banksystem.DataBase;
import main.banksystem.commands.BuildCreditCommand;
import main.banksystem.commands.BuildInstallmentCommand;
import main.banksystem.commands.BuildSalaryProjectCommand;
import main.banksystem.commands.ICommand;
import main.banksystem.controllers.SwitchMenu;
import main.banksystem.controllers.manager.ManagerMainMenuController;
import main.banksystem.entities.Id;
import main.banksystem.entities.User;

public class SpecialistMainMenuController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label companyMoneyLabel;

    @FXML
    private Button createCompanyTransferButton;

    @FXML
    private Label errorLabel;

    @FXML
    private Button findButton1;

    @FXML
    private Accordion newStaffAccordion;

    @FXML
    private Accordion salaryAccordion;

    @FXML
    private Button setCompanyButton;

    @FXML
    private Label specialistStatusLabel;

    @FXML
    private Button toClientButton;

    @FXML
    private Accordion transferAccordion;

    @FXML
    void initialize() {
        createSalaryProjectAccordion();

        setCompanyButton.setOnAction(event -> {
            SwitchMenu.newMenu(setCompanyButton, "/main/banksystem/specialist/connect_menu.fxml");
        });


    }

    void createSalaryProjectAccordion(){
        salaryAccordion.getPanes().clear();

        DataBase dataBase = DataBase.getInstance();
        Map<Id, Queue<ICommand>> commands = dataBase.downloadQueue(DataBase.QUEUE_PART, ICommand.class);

        for (Map.Entry<Id, Queue<ICommand>> commandsEntry : commands.entrySet()) {
            for (ICommand command : commandsEntry.getValue()) {
                if (command.getClass() == BuildSalaryProjectCommand.class) {
                    TitledPane titledPane = new TitledPane();
                    titledPane.getStylesheets().add(SpecialistMainMenuController.class.getResource("/main/banksystem/pane_sheet.css").toExternalForm());
                    titledPane.setText(command.getDescription());

                    VBox content = new VBox();
                    Button approve = new Button("Approve");
                    approve.getStylesheets().add(SpecialistMainMenuController
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
                    disapprove.getStylesheets().add(SpecialistMainMenuController
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

}
