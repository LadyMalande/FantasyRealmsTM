module FantasyRealmsTM {
    requires javafx.fxml;
    requires javafx.controls;
    requires java.desktop;
    requires javafx.web;

    opens client;

    exports interactive;
    exports client;
        }