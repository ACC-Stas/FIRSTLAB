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

    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.datatype.jsr310;
    requires com.google.common;

    opens main.banksystem to javafx.fxml;
    exports main.banksystem;
    exports main.banksystem.controllers;
    opens main.banksystem.controllers to javafx.fxml;
    exports main.banksystem.commands;
    opens main.banksystem.commands to javafx.fxml;
    exports main.banksystem.containers;
    opens main.banksystem.containers to javafx.fxml;
    exports main.banksystem.builders;
    opens main.banksystem.builders to javafx.fxml;
    exports main.banksystem.controllers.client;
    opens main.banksystem.controllers.client to javafx.fxml;
    exports main.banksystem.controllers.administrator;
    opens main.banksystem.controllers.administrator to javafx.fxml;
    exports main.banksystem.controllers.manager;
    opens main.banksystem.controllers.manager to javafx.fxml;
    exports main.banksystem.controllers.operator;
    opens main.banksystem.controllers.operator to javafx.fxml;
    exports main.banksystem.controllers.specialist;
    opens main.banksystem.controllers.specialist to javafx.fxml;
}