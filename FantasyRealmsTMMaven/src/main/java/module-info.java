module FantasyRealmsTM {
        requires javafx.fxml;
    requires java.desktop;
    requires javafx.graphics;
    requires javafx.controls;

    opens client;

    exports client;
    exports interactive;
        }

