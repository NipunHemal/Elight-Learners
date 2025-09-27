package lk.ijse.elight_driving_school.controller.layout;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import lk.ijse.elight_driving_school.util.AlertUtils;
import lk.ijse.elight_driving_school.util.AuthUtil;
import lk.ijse.elight_driving_school.util.DashboardNavigator;
import lk.ijse.elight_driving_school.util.NotificationUtils;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardLayoutController implements Initializable {
    public StackPane breadcrumbContainer;
    public AnchorPane dashboardContainer;
    public Button btnDashboard;
    public Button btnInstructor;
    public Button btnStudent;
    public Button btnLesson;
    public Button btnPayment;
    public Button btnUser;
    public Button btnCourse;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DashboardNavigator.init(dashboardContainer, breadcrumbContainer);
        try {
            DashboardNavigator.navigate("Dashboard");
            recreation();
        } catch (Exception e) {
            e.printStackTrace();
            NotificationUtils.showError("Error", e.getMessage());
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

    public void onActionPayment(ActionEvent actionEvent) {
        DashboardNavigator.navigate("Payment");
    }

    public void onActionUser(ActionEvent actionEvent) {
        DashboardNavigator.navigate("User");
    }

    public void recreation(){
        if (AuthUtil.getCurrentUser() == null){
            DashboardNavigator.mainNavigator("/view/auth/Login.fxml");
        }

        if (AuthUtil.getRole() != "ADMIN"){
            btnCourse.setVisible(false);
            btnInstructor.setVisible(false);
            btnUser.setVisible(false);
        }
    }
}
