module Test{
    requires javafx.controls;
    requires javafx.fxml;
    requires  javafx.graphics;
    requires java.sql;
    requires com.google.gson;
    opens tasknest.controllers;
    opens tasknest.models;
    opens tasknest.services;
    opens tasknest.tests;
}