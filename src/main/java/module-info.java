module org.example.demo1 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires java.sql;
    requires java.desktop;

    opens org.example.demo1 to javafx.fxml;
    exports org.example.demo1;
    exports tests;
    opens tests to javafx.fxml;
}