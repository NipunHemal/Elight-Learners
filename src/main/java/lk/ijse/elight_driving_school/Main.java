package lk.ijse.elight_driving_school;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import lk.ijse.elight_driving_school.util.DashboardNavigator;
import lk.ijse.elight_driving_school.util.ReferenceUtils;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        ReferenceUtils.primaryStage = stage;
        Parent parent = FXMLLoader.load(getClass().getResource("/view/auth/Login.fxml"));
        StackPane mainStackPane = new StackPane();
        mainStackPane.getChildren().add(parent);
        Scene scene = new Scene(mainStackPane);
        stage.setScene(scene);
        stage.setTitle("Elight Driving School");
        stage.show();
        ReferenceUtils.mainStackPane = mainStackPane;
    }
}
