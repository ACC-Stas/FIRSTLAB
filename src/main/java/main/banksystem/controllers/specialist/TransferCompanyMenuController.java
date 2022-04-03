package main.banksystem.controllers.specialist;

import java.net.URL;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import main.banksystem.CPU;
import main.banksystem.DataBase;
import main.banksystem.ProgramStatus;
import main.banksystem.builders.TransferBuilder;
import main.banksystem.commands.ICommand;
import main.banksystem.commands.TransferCommand;
import main.banksystem.entities.Company;
import main.banksystem.entities.Id;

public class TransferCompanyMenuController {

    ObservableList<String> companyList = FXCollections.observableArrayList();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField billField;

    @FXML
    private ChoiceBox<String> companyChoice;

    @FXML
    private Label errorLabel;

    @FXML
    private TabPane toTabPane;

    @FXML
    private Button transferButton;

    @FXML
    private TextField valueField;

    @FXML
    void initialize() {

        ProgramStatus status = ProgramStatus.getInstance();

        DataBase dataBase = DataBase.getInstance();
        Map<Id, Company> companies = dataBase.downloadMap(DataBase.COMPANY_PART, Company.class);
        for (Company company : companies.values()) {
            companyList.add(company.getjName());
        }
        companyChoice.setItems(companyList);

        transferButton.setOnAction(event -> {
            TransferBuilder transferBuilder = new TransferBuilder();
            transferBuilder.buildValue(Double.parseDouble(valueField.getText()));
            if (toTabPane.getSelectionModel().getSelectedIndex() == 0){
                String JName = companyChoice.getValue();
                Company company = null;
                for (Company companyI : companies.values()) {
                    if (Objects.equals(JName, companyI.getjName())) {
                        company = companyI;
                        break;
                    }
                }

                if (company == null) {
                    errorLabel.setText(String.format("No company %s", JName));
                    return;
                }

                transferBuilder.buildBillToId(company.getBillCompanyId());
            }
            else {
                transferBuilder.buildBillToId(new Id(Long.parseLong(billField.getText())));
            }
            transferBuilder.buildBillFromId(companies.get(status.getCompany()).getBillCompanyId());
            TransferBuilder.Result transfer = transferBuilder.getTransfer();

            if (!transfer.valid) {
                errorLabel.setText(transfer.description);
            }
            else {
                ICommand.Type type = new ICommand.Type(false, true);
                TransferCommand command = new TransferCommand(transfer.transfer, type);

                CPU cpu = new CPU(status.getUser());
                cpu.heldCommand(command);
                transferButton.getScene().getWindow().hide();
            }
        });

    }

}
