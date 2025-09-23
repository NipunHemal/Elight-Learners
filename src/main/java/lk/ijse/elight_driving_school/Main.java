package lk.ijse.elight_driving_school;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lk.ijse.elight_driving_school.util.ReferenceUtils;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        ReferenceUtils.primaryStage = stage;
        Parent parent = FXMLLoader.load(getClass().getResource("/view/layout/DashboardLayout.fxml"));
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.setTitle("Elight Driving School");
        stage.show();
    }
}
