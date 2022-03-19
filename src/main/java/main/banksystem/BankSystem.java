package main.banksystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import main.banksystem.controllers.*;

import java.io.IOException;
import java.util.Objects;

public class BankSystem extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("login_menu.fxml")));
        stage.setScene(new Scene(root, 700, 400));
        stage.setTitle("BankSystem");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}