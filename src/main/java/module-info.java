module main.banksystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.opencsv;

    opens main.banksystem to javafx.fxml;
    exports main.banksystem;
    exports main.banksystem.controllers;
    opens main.banksystem.controllers to javafx.fxml;
}