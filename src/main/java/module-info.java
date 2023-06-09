module com.kaya.gestionetudiants {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires poi;
    requires poi.ooxml;
    requires com.fasterxml.jackson.databind;
    requires com.google.gson;
    requires org.json;


    opens com.kaya.gestionetudiants to javafx.fxml;
    exports com.kaya.gestionetudiants;

    exports com.kaya.gestionetudiants.controller;
    opens com.kaya.gestionetudiants.controller to javafx.fxml;

    exports com.kaya.gestionetudiants.jdbc.entities;
    opens com.kaya.gestionetudiants.jdbc.entities to javafx.fxml;
}