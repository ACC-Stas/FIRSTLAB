package main.banksystem.controllers.client;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;

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
        createBillButton.setOnAction(event ->{
            newMenu("/main/banksystem/client/create_bill_menu.fxml");
        });
        withdrawBillButton.setOnAction(event ->{
            newMenu("/main/banksystem/client/withdraw_menu.fxml");
        });
        transferBillButton.setOnAction(event ->{
            newMenu("/main/banksystem/client/transfer_menu.fxml");
        });
    }

}
