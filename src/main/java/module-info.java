module Calculator {
    requires javafx.controls;
    requires javafx.fxml;

    opens Calculator to javafx.fxml;
}
