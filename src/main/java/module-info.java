module lk.ijse.elight_driving_school {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens lk.ijse.elight_driving_school to javafx.fxml;
    exports lk.ijse.elight_driving_school;
}