module FantasyRealmsTM {
    requires javafx.fxml;
    requires javafx.controls;
    requires java.desktop;

    opens client;

    exports interactive;
    exports client;
        }