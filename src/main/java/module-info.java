module erijtestpi {

    requires javafx.controls;
    requires javafx.fxml;
    requires  javafx.graphics;
    requires java.sql;
    requires com.fasterxml.jackson.databind;
    opens Tasknest.controllers;
    opens Tasknest.models;
    opens Tasknest.services;
    opens Tasknest.tests;

}