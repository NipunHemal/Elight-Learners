module lk.ijse.elight_driving_school {
    requires javafx.controls;
    requires org.controlsfx.controls;
    requires javafx.fxml;
    requires org.kordamp.bootstrapfx.core;
    requires static lombok;
    requires com.jfoenix;
    requires jdk.compiler;
    requires jakarta.mail;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires java.naming;
    requires jbcrypt;

    opens lk.ijse.elight_driving_school to javafx.fxml;

    opens lk.ijse.elight_driving_school.controller to javafx.fxml;
    opens lk.ijse.elight_driving_school.controller.auth to javafx.fxml;
    opens lk.ijse.elight_driving_school.controller.layout to javafx.fxml;
    opens lk.ijse.elight_driving_school.controller.component to javafx.fxml;
    opens lk.ijse.elight_driving_school.controller.pages to javafx.fxml;

    exports lk.ijse.elight_driving_school;
}