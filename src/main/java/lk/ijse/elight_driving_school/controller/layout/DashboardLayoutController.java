package lk.ijse.elight_driving_school.controller.layout;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import lk.ijse.elight_driving_school.util.AlertUtils;
import lk.ijse.elight_driving_school.util.DashboardNavigator;
import lk.ijse.elight_driving_school.util.NotificationUtils;
import org.controlsfx.control.Notifications;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardLayoutController implements Initializable {
    public StackPane breadcrumbContainer;
    public AnchorPane dashboardContainer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DashboardNavigator.init(dashboardContainer, breadcrumbContainer);
        try {
            DashboardNavigator.navigate("Dashboard");
        } catch (Exception e) {
            NotificationUtils.showError("Error", e.getMessage());
//            Notifications.create().title("Error").text(e.getMessage()).show();
            e.printStackTrace();
        }
    }

    public void onActionInstructor(ActionEvent actionEvent) {
        DashboardNavigator.navigate("Instructor");
    }

    public void onActionStudents(ActionEvent actionEvent) {
        DashboardNavigator.navigate("Student");
    }

    public void onActionCourses(ActionEvent actionEvent) {
        DashboardNavigator.navigate("Courses");
    }

    public void onActionLessons(ActionEvent actionEvent) {
        DashboardNavigator.navigate("Lessons");
    }

    public void onActionLogout(ActionEvent actionEvent) {
        boolean isLogout = AlertUtils.showConform("Logout", "Are you sure you want to logout?");
        if (isLogout){
            DashboardNavigator.mainNavigator("/view/auth/Login.fxml");
        }
    }
}
