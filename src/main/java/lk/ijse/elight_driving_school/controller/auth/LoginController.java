package lk.ijse.elight_driving_school.controller.auth;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import lk.ijse.elight_driving_school.util.ReferenceUtils;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public TextField usernameField;
    public PasswordField passwordField;
    public StackPane dialogPane;

    public void onLogin(ActionEvent actionEvent) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ReferenceUtils.dialogPane = dialogPane;
    }
}
