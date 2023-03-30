module teamproject.teamprojectproduct {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens team15 to javafx.fxml;
    exports team15;
    exports team15.controllers;
    opens team15.controllers to javafx.fxml;
}