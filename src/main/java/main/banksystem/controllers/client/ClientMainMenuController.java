package main.banksystem.controllers.client;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import main.banksystem.DataBase;
import main.banksystem.ProgramStatus;
import main.banksystem.entities.Bill;
import main.banksystem.entities.Id;
import main.banksystem.entities.SalaryProject;
import main.banksystem.controllers.manager.ManagerMainMenuController;

import static main.banksystem.controllers.SwitchMenu.newMenu;

public class ClientMainMenuController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Accordion billsAccordion;

    @FXML
    private Button closeBillButton;

    @FXML
    private Button closeCreditButton;

    @FXML
    private Button closeDepositButton;

    @FXML
    private Button closeInstallmentButton;

    @FXML
    private Button closeSalaryButton;

    @FXML
    private Button createBillButton;

    @FXML
    private Button createCreditButton;

    @FXML
    private Button createDepositButton;

    @FXML
    private Button createInstallmentButton;

    @FXML
    private Button createSalaryButton;

    @FXML
    private Accordion creditAccordion;

    @FXML
    private Accordion depositAccordion;

    @FXML
    private Button freezeBillButton;

    @FXML
    private Label idLabel;

    @FXML
    private Accordion installmentAccordion;

    @FXML
    private Accordion salaryAccordion;

    @FXML
    private Button transferBillButton;

    @FXML
    private Button unfreezeBillButton;

    @FXML
    private Button withdrawBillButton;

    @FXML
    void initialize() {
        createBillAccordion();
        createSalaryAccordion();

        ProgramStatus programStatus = ProgramStatus.getInstance();
        idLabel.setText(programStatus.getUser().getIdx().toString());


        createCreditButton.setOnAction(event ->{
            newMenu(createBillButton, "/main/banksystem/client/create_credit_menu.fxml");
        });
        createBillButton.setOnAction(event ->{
            newMenu(createBillButton, "/main/banksystem/client/create_bill_menu.fxml");
        });
        withdrawBillButton.setOnAction(event ->{
            newMenu(withdrawBillButton, "/main/banksystem/client/withdraw_menu.fxml");
        });
        transferBillButton.setOnAction(event ->{
            newMenu(transferBillButton, "/main/banksystem/client/transfer_menu.fxml");
        });
    }

    void createBillAccordion() {
        billsAccordion.getPanes().clear();

        DataBase dataBase = DataBase.getInstance();
        Map<Id, Bill> bills = dataBase.downloadMap(DataBase.BILLS_PART, Bill.class);

        ProgramStatus programStatus = ProgramStatus.getInstance();
        for (Id id : programStatus.getUser().getBillIds()) {
            TitledPane titledPane = new TitledPane();
            titledPane.getStylesheets().add(ManagerMainMenuController.class.getResource("/main/banksystem/pane_sheet.css").toExternalForm());
            titledPane.setText(id.toString());

            VBox content = new VBox();
            Label value = new Label("Value: " + bills.get(id).getMoney());
            Label status = new Label("Status: " + bills.get(id).getBillConditions().toString());

            content.getChildren().add(value);
            content.getChildren().add(status);
            titledPane.setContent(content);

            billsAccordion.getPanes().addAll(titledPane);
        }


    }

    void createSalaryAccordion() {
        salaryAccordion.getPanes().clear();
        DataBase dataBase = DataBase.getInstance();
        Map<Id, SalaryProject> salaries = dataBase.downloadMap(DataBase.SALARY_PART, SalaryProject.class);

        ProgramStatus programStatus = ProgramStatus.getInstance();
        for (Id id : programStatus.getUser().getSalaryIds()) {
            SalaryProject salary = salaries.get(id);
            TitledPane titledPane = new TitledPane();
            titledPane.getStylesheets().add(ManagerMainMenuController.class.getResource("/main/banksystem/pane_sheet.css").toExternalForm());
            titledPane.setText(id.toString());

            VBox content = new VBox();
            Label value = new Label("Значение: " + String.valueOf(salary.getSum()));
            Label bill = new Label("Привязанный счёт: " + String.valueOf(salary.getBillToId()));

            content.getChildren().add(value);
            content.getChildren().add(bill);
            titledPane.setContent(content);

            salaryAccordion.getPanes().addAll(titledPane);
        }
    }

    void createDepositAccordion() {
        salaryAccordion.getPanes().clear();
        DataBase dataBase = DataBase.getInstance();
        Map<Id, SalaryProject> salaries = dataBase.downloadMap(DataBase.SALARY_PART, SalaryProject.class);

        ProgramStatus programStatus = ProgramStatus.getInstance();
        for (Id id : programStatus.getUser().getSalaryIds()) {
            SalaryProject salary = salaries.get(id);
            TitledPane titledPane = new TitledPane();
            titledPane.getStylesheets().add(ManagerMainMenuController.class.getResource("/main/banksystem/pane_sheet.css").toExternalForm());
            titledPane.setText(id.toString());

            VBox content = new VBox();
            Label value = new Label("Значение: " + String.valueOf(salary.getSum()));
            Label bill = new Label("Привязанный счёт: " + String.valueOf(salary.getBillToId()));

            content.getChildren().add(value);
            content.getChildren().add(bill);
            titledPane.setContent(content);

            salaryAccordion.getPanes().addAll(titledPane);
        }
    }

}
