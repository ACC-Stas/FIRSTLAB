package main.banksystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.Scene;

import java.io.IOException;
import java.util.Objects;

public class BankSystem extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/main/banksystem/login_menu.fxml")));
        stage.setScene(new Scene(root));
        stage.setTitle("BankSystem");
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}