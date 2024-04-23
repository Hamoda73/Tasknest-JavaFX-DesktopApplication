package org.example.demo1;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

public class HelloController {

    @FXML
    void printHelloWorld(MouseEvent event) {
        System.out.println("Hello World");
    }

}
