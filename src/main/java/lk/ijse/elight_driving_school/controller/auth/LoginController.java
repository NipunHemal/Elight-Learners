package lk.ijse.elight_driving_school.controller.auth;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import lk.ijse.elight_driving_school.util.AlertUtils;
import lk.ijse.elight_driving_school.util.DashboardNavigator;
import lk.ijse.elight_driving_school.util.DialogUtil;
import lk.ijse.elight_driving_school.util.ReferenceUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public TextField usernameField;
    public PasswordField passwordField;
    public StackPane dialogPane;

    public void onLogin(ActionEvent actionEvent) throws IOException {
        DashboardNavigator.mainNavigator("/view/layout/DashboardLayout.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        ReferenceUtils.mainStackPane = dialogPane;
    }
}
