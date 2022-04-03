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
import main.banksystem.entities.*;
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
    private Button reloadButton;

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
        createDepositAccordion();
        createSalaryAccordion();
        createCreditAccordion();
        createInstallmentAccordion();

        ProgramStatus programStatus = ProgramStatus.getInstance();
        idLabel.setText(programStatus.getUser().getIdx().toString());

        reloadButton.setOnAction(event -> {
            DataBase dataBase = DataBase.getInstance();
            Map<Id, User> users = dataBase.downloadMap(DataBase.USER_PART, User.class);
            programStatus.setUser(users.get(programStatus.getUser().getIdx()));
            initialize();
        });
        createDepositButton.setOnAction(event ->{
            newMenu(createDepositButton, "/main/banksystem/client/create_deposit_menu.fxml");
        });
        closeDepositButton.setOnAction(event ->{
            newMenu(closeDepositButton, "/main/banksystem/client/close_deposit_menu.fxml");
        });
        createCreditButton.setOnAction(event ->{
            newMenu(createCreditButton, "/main/banksystem/client/create_credit_menu.fxml");
        });
        closeCreditButton.setOnAction(event ->{
            newMenu(closeCreditButton, "/main/banksystem/client/close_credit_menu.fxml");
        });
        createBillButton.setOnAction(event ->{
            newMenu(createBillButton, "/main/banksystem/client/create_bill_menu.fxml");
        });
        createInstallmentButton.setOnAction(event ->{
            newMenu(createInstallmentButton, "/main/banksystem/client/create_installment_menu.fxml");
        });
        closeInstallmentButton.setOnAction(event ->{
            newMenu(closeInstallmentButton, "/main/banksystem/client/close_installment_menu.fxml");
        });
        withdrawBillButton.setOnAction(event ->{
            newMenu(withdrawBillButton, "/main/banksystem/client/withdraw_menu.fxml");
        });
        transferBillButton.setOnAction(event ->{
            newMenu(transferBillButton, "/main/banksystem/client/transfer_menu.fxml");
        });
        createSalaryButton.setOnAction(event ->{
            newMenu(createSalaryButton, "/main/banksystem/client/hiring_menu.fxml");
        });
        closeSalaryButton.setOnAction(event ->{
            newMenu(closeSalaryButton, "/main/banksystem/client/firing_menu.fxml");
        });
        freezeBillButton.setOnAction(event ->{
            newMenu(closeSalaryButton, "/main/banksystem/client/freeze_menu.fxml");
        });
        unfreezeBillButton.setOnAction(event ->{
            newMenu(closeSalaryButton, "/main/banksystem/client/unfreeze_menu.fxml");
        });
        closeBillButton.setOnAction(event ->{
            newMenu(closeSalaryButton, "/main/banksystem/client/block_menu.fxml");
        });
    }

    void createBillAccordion() {
        billsAccordion.getPanes().clear();

        DataBase dataBase = DataBase.getInstance();
        Map<Id, Company> banks = dataBase.downloadMap(DataBase.COMPANY_PART, Company.class);
        Map<Id, Bill> bills = dataBase.downloadMap(DataBase.BILLS_PART, Bill.class);

        ProgramStatus programStatus = ProgramStatus.getInstance();
        for (Id id : programStatus.getUser().getBillIds()) {
            TitledPane titledPane = new TitledPane();
            titledPane.getStylesheets().add(ManagerMainMenuController
                    .class.getResource("/main/banksystem/pane_sheet.css").toExternalForm());
            titledPane.setText("ID счёта: " + id.toString());

            VBox content = new VBox();
            Label value = new Label("Сумма: " + bills.get(id).getMoney());
            Label status = new Label("Статус: " + bills.get(id).getBillConditions().toString());

            String bankName = "Банкн не найден";
            for (Company bank : banks.values()) {
                for (Id billIdI : bank.getBillsIds()) {
                    if (billIdI.equals(id)) {
                        bankName = bank.getjName();
                        break;
                    }
                }
            }
            Label bank = new Label(String.format("Банк: %s", bankName));

            content.getChildren().add(value);
            content.getChildren().add(status);
            content.getChildren().add(bank);
            titledPane.setContent(content);

            billsAccordion.getPanes().addAll(titledPane);
        }


    }

    void createSalaryAccordion() {
        salaryAccordion.getPanes().clear();
        DataBase dataBase = DataBase.getInstance();
        Map<Id, SalaryProject> salaries = dataBase.downloadMap(DataBase.SALARY_PART, SalaryProject.class);
        Map<Id, Company> companies = dataBase.downloadMap(DataBase.COMPANY_PART, Company.class);

        ProgramStatus programStatus = ProgramStatus.getInstance();
        for (Id id : programStatus.getUser().getSalaryIds()) {
            SalaryProject salary = salaries.get(id);
            TitledPane titledPane = new TitledPane();
            titledPane.getStylesheets().add(ManagerMainMenuController
                    .class.getResource("/main/banksystem/pane_sheet.css").toExternalForm());
            titledPane.setText("ID зарплатного проекта: " + id.toString());

            VBox content = new VBox();
            Label value = new Label("Сумма: " + String.valueOf(salary.getSum()));
            Label bill = new Label("Привязанный счёт: " + String.valueOf(salary.getBillToId()));
            Label place = new Label("Компания не найдена");
            for (Company company : companies.values()){
                if (company.getBillCompanyId() == salary.getBillFromId()){
                    place.setText(String.format("Банк: %s", company.getjName()));
                    break;
                }
            }

            content.getChildren().add(value);
            content.getChildren().add(bill);
            content.getChildren().add(place);

            titledPane.setContent(content);

            salaryAccordion.getPanes().addAll(titledPane);
        }
    }

    void createCreditAccordion(){
        creditAccordion.getPanes().clear();

        DataBase dataBase = DataBase.getInstance();
        Map<Id, Credit> credits = dataBase.downloadMap(DataBase.CREDIT_PART, Credit.class);
        Map<Id, Company> banks = dataBase.downloadMap(DataBase.COMPANY_PART, Company.class);

        ProgramStatus programStatus = ProgramStatus.getInstance();
        for (Id id : programStatus.getUser().getCreditIds()) {
            if (credits.get(id).getSumToPay() <= 0) {
                continue;
            }
            TitledPane titledPane = new TitledPane();
            titledPane.getStylesheets().add(ManagerMainMenuController
                    .class.getResource("/main/banksystem/pane_sheet.css").toExternalForm());
            titledPane.setText("ID кредита: " + id.toString());

            VBox content = new VBox();
            Label sum = new Label("Осталось выплатить: " + credits.get(id).getSumToPay());
            Label bill = new Label("Привязанный счёт: " + credits.get(id).getSourceBillId().toString());

            String bankName = "Банк не найден";
            for (Company bank : banks.values()) {
                if (bank.getBillCompanyId() == credits.get(id).getBankBillId()) {
                    bankName = bank.getjName();
                    break;
                }
            }
            Label bank = new Label(String.format("Банк: %s", bankName));

            content.getChildren().add(sum);
            content.getChildren().add(bill);
            content.getChildren().add(bank);
            titledPane.setContent(content);

            creditAccordion.getPanes().addAll(titledPane);
        }
    }

    void createInstallmentAccordion(){
        installmentAccordion.getPanes().clear();

        DataBase dataBase = DataBase.getInstance();
        Map<Id, Installment> installments = dataBase.downloadMap(DataBase.INSTALLMENT_PART, Installment.class);
        Map<Id, Company> companies = dataBase.downloadMap(DataBase.COMPANY_PART, Company.class);

        ProgramStatus programStatus = ProgramStatus.getInstance();
        for (Id id : programStatus.getUser().getInstallmentIds()) {
            if (installments.get(id).getSumToPay() <= 0) {
                continue;
            }
            TitledPane titledPane = new TitledPane();
            titledPane.getStylesheets().add(ManagerMainMenuController
                    .class.getResource("/main/banksystem/pane_sheet.css").toExternalForm());
            titledPane.setText("ID рассрочки: " + id.toString());

            VBox content = new VBox();
            Label sum = new Label("Осталось выплатить: " + installments.get(id).getSumToPay());
            Label bill = new Label("Привязанный счёт: " + installments.get(id).getSourceBillId().toString());

            String companyName = "Компания не найдена";
            for (Company company : companies.values()) {
                if (company.getBillCompanyId() == installments.get(id).getCompanyBillId()) {
                    companyName = company.getjName();
                    break;
                }
            }
            Label place = new Label(String.format("Компания: %s", companyName));

            content.getChildren().add(sum);
            content.getChildren().add(bill);
            content.getChildren().add(place);
            titledPane.setContent(content);

            installmentAccordion.getPanes().addAll(titledPane);
        }
    }

    void createDepositAccordion() {
        depositAccordion.getPanes().clear();
        DataBase dataBase = DataBase.getInstance();
        Map<Id, Deposit> deposits = dataBase.downloadMap(DataBase.DEPOSIT_PART, Deposit.class);
        Map<Id, Company> banks = dataBase.downloadMap(DataBase.COMPANY_PART, Company.class);

        ProgramStatus programStatus = ProgramStatus.getInstance();
        for (Id id : programStatus.getUser().getDepositIds()) {
            Deposit deposit = deposits.get(id);
            TitledPane titledPane = new TitledPane();
            titledPane.getStylesheets().add(ManagerMainMenuController
                    .class.getResource("/main/banksystem/pane_sheet.css").toExternalForm());
            titledPane.setText("ID вклада: " + id.toString());

            VBox content = new VBox();
            Label value = new Label("Значение: " + String.valueOf(deposit.getValue()));
            Label bill = new Label("Привязанный счёт: " + String.valueOf(deposit.getBillId()));

            String bankName = "Банк не найден";
            for (Company bank : banks.values()) {
                if (bank.getBillCompanyId() == deposits.get(id).getBankBillId()) {
                    bankName = bank.getjName();
                    break;
                }
            }
            Label bank = new Label(String.format("Банк: %s", bankName));

            content.getChildren().add(value);
            content.getChildren().add(bill);
            content.getChildren().add(bank);
            titledPane.setContent(content);

            depositAccordion.getPanes().addAll(titledPane);
        }
    }

}
