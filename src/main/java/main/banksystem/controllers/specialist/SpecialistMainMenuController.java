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
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import main.banksystem.CPU;
import main.banksystem.DataBase;
import main.banksystem.ProgramStatus;
import main.banksystem.commands.BuildSalaryProjectCommand;
import main.banksystem.commands.ICommand;
import main.banksystem.controllers.SwitchMenu;
import main.banksystem.entities.Bill;
import main.banksystem.entities.Company;
import main.banksystem.entities.Id;
import main.banksystem.entities.User;

import static main.banksystem.controllers.SwitchMenu.*;
import static main.banksystem.controllers.SwitchMenu.switchMenu;

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
    private Button salaryButton;

    @FXML
    private Button reloadButton;

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
        reload();

        reloadButton.setOnAction(event -> {
            reload();
        });
        setCompanyButton.setOnAction(event -> {
            newMenu(setCompanyButton, "/main/banksystem/specialist/connect_menu.fxml");
        });
        toClientButton.setOnAction(event -> {
            switchMenu(toClientButton, "/main/banksystem/client/client_main_menu.fxml");
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

    void reload() {
        ProgramStatus status = ProgramStatus.getInstance();
        Id userId = status.getUser().getIdx();

        DataBase dataBase = DataBase.getInstance();
        Map<Id, Company> companies = dataBase.downloadMap(DataBase.COMPANY_PART, Company.class);
        for (Company company : companies.values()) {
            for (Id specialistId : company.getSpecialistIds()) {
                if (userId.equals(specialistId)) {
                    status.setCompany(company.getPAN());
                }
            }
        }

        if (status.getCompany() != null) {
            Map<Id, Bill> bills = dataBase.downloadMap(DataBase.BILLS_PART, Bill.class);
            companyMoneyLabel.setText(String.valueOf(bills.get(companies.get(status.getCompany())
                    .getBillCompanyId()).getMoney()));
            specialistStatusLabel.setTextFill(Color.GREEN);
            specialistStatusLabel.setText("Вы привязаны к предприятию.");
            setCompanyButton.setVisible(false);
            setCompanyButton.setManaged(false);
        }

    }
}
