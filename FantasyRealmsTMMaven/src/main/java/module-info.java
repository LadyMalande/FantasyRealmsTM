module FantasyRealmsTM {
        requires javafx.fxml;
    requires java.desktop;
    requires javafx.graphics;
    requires javafx.controls;

    opens bonuses;
    opens maluses;
    opens sample;

    exports bonuses;
    exports maluses;
    exports interactive;
        }

