package main.banksystem.controllers.client;

import java.net.URL;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

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
import main.banksystem.entities.*;

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
                bankList.add(company.getjName());
            }
        }
        bankChoice.setItems(bankList);

        Pattern p = Pattern.compile("(\\d+\\.?\\d*)?");
        valueField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!p.matcher(newValue).matches()) valueField.setText(oldValue);
        });

        createButton.setOnAction(event -> {
            Company bank = null;
            String jName = bankChoice.getValue();

            for (Company company : companies.values()) {
                if (company.getIsBank() && Objects.equals(jName, company.getjName())) {
                    bank = company;
                }
            }

            if (bank == null) {
                errorLabel.setText("No bank found");
                return;
            }

            double value = -1;
            try {
                value = Double.parseDouble(valueField.getText());
            } catch (Exception e) {
                errorLabel.setText("Invalid sum");
                return;
            }
            if (value < 0) {
                errorLabel.setText("Invalid sum");
                return;
            }
            IndexGenerator indexGenerator = IndexGenerator.getInstance();
            Bill bill = new Bill(new Id(indexGenerator.generateIdx(IndexGenerator.BILLS_IDX)), value, BillConditions.ACTIVE);

            ProgramStatus programStatus = ProgramStatus.getInstance();
            ICommand.Type type = new ICommand.Type(false, true);
            BuildBillCommand command = new BuildBillCommand(bill, type, programStatus.getUser().getIdx(), bank.getPAN());

            CPU cpu = new CPU(programStatus.getUser());
            cpu.heldCommand(command);
            createButton.getScene().getWindow().hide();
        });
    }

}
