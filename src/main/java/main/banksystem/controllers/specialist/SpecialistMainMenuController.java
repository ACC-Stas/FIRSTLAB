package main.banksystem.controllers.specialist;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import main.banksystem.controllers.SwitchMenu;

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
        setCompanyButton.setOnAction(event -> {
            SwitchMenu.newMenu(setCompanyButton, "/main/banksystem/specialist/connect_menu.fxml");
        });
    }

}
