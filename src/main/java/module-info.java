module erijtestpi {

    requires javafx.controls;
    requires javafx.fxml;
    requires  javafx.graphics;
    requires java.sql;
    requires com.fasterxml.jackson.databind;
    requires java.desktop;
    requires itextpdf;
    requires javafx.swing;
    requires java.mail;
    requires activation;
    //opens Tasknest.controllers;
    opens tasknest.models;
    opens tasknest.services;
    opens tasknest.tests;
    opens tasknest.controllers.CV;
    opens tasknest.controllers.Skill;
    opens dashboard.controllers;
    //opens tasknest.controllers.Skill;

}