module FantasyRealmsTM {
        requires javafx.fxml;
        requires javafx.controls;
    requires java.desktop;

    opens bonuses;
    opens maluses;
    opens sample;

    exports bonuses;
    exports maluses;
    exports interactive;
        }