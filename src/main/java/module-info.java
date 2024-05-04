module Test{
    requires javafx.controls;
    requires javafx.fxml;
    requires  javafx.graphics;
    requires java.sql;
   // requires org.apache.pdfbox;
    requires java.desktop;
    requires twilio;
    requires java.mail;

    // requires javafx.web;
    // opens tasknest.controllers;
    opens tasknest.models;
    opens tasknest.services;
    opens tasknest.tests;
    opens tasknest.controllers.offer;
    exports tasknest.controllers.applications;
    opens tasknest.controllers.applications to javafx.fxml;
    opens dashboard.controllers;

}