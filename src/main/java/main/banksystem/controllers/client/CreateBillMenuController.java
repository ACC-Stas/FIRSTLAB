package main.banksystem.controllers.client;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import main.banksystem.CPU;
import main.banksystem.DataBase;
import main.banksystem.IndexGenerator;
import main.banksystem.ProgramStatus;
import main.banksystem.commands.BuildBillCommand;
import main.banksystem.commands.ICommand;
import main.banksystem.containers.*;

public class CreateBillMenuController {

    ObservableList<String> bankList = FXCollections.observableArrayList();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ChoiceBox<String> bankChoice;

    @FXML
    private Button createButton;

    @FXML
    private Label errorLabel;

    @FXML
    private TextField valueField;

    @FXML
    void initialize() {

        DataBase dataBase = DataBase.getInstance();
        Map<Id, Company> companies = dataBase.downloadMap(DataBase.COMPANY_PART, Company.class);
        for (Company company : companies.values()) {
            if (company.getIsBank()) {
                bankList.add(company.getPAN().toString());
            }
        }
        bankChoice.setItems(bankList);

        createButton.setOnAction(event -> {
            double value = -1;
            try {
                value = Double.parseDouble(valueField.getText());
            }
            catch (Exception e) {
                errorLabel.setText("Invalid input");
            }
            if (value < 0){
                errorLabel.setText("Invalid input");
            }
            IndexGenerator indexGenerator = IndexGenerator.getInstance();
            Bill bill = new Bill(new Id(indexGenerator.generateIdx(IndexGenerator.BILLS_IDX)), value, BillConditions.ACTIVE);

            ICommand.Type type = new ICommand.Type(true, false);
            BuildBillCommand command = new BuildBillCommand(bill, type);

            ProgramStatus programStatus = ProgramStatus.getInstance();
            CPU cpu = new CPU(programStatus.getUser());
            cpu.heldCommand(command);
        });
    }

}
