module com.poo.chessgame1_2 {
        requires javafx.controls;
        exports com.poo.chessgame1_2;
        exports com.poo.chessgame1_2.view;
        requires javafx.fxml;

    requires javafx.media;

    opens com.poo.chessgame1_2 to javafx.fxml;
        opens com.poo.chessgame1_2.view to javafx.fxml;
        exports com.poo.chessgame1_2.model;
        opens com.poo.chessgame1_2.model to javafx.fxml;
    exports com.poo.chessgame1_2.controller;
    opens com.poo.chessgame1_2.controller to javafx.fxml;
}