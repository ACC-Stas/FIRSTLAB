package main.banksystem.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class SwitchMenu {
    static public void switchMenu(Button button, String path){
        button.getScene().getWindow().hide();

        FXMLLoader loader = new FXMLLoader(SwitchMenu.class.getResource(path));

        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setTitle("BankSystem");
        stage.setScene(new Scene(root));
        stage.show();
    }
    static public void newMenu(String path){
        FXMLLoader loader = new FXMLLoader(SwitchMenu.class.getResource(path));

        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setTitle("BankSystem");
        stage.setScene(new Scene(root));
        stage.show();
    }
}
