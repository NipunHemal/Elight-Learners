package lk.ijse.elight_driving_school.controller.auth;

import javafx.event.ActionEvent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import lk.ijse.elight_driving_school.dto.UserDTO;
import lk.ijse.elight_driving_school.enums.ServiceTypes;
import lk.ijse.elight_driving_school.service.ServiceFactory;
import lk.ijse.elight_driving_school.service.custom.UserService;
import lk.ijse.elight_driving_school.util.*;

import java.io.IOException;

public class LoginController{
    public TextField usernameField;
    public PasswordField passwordField;
    public StackPane dialogPane;
    UserService userService = ServiceFactory.getInstance().getService(ServiceTypes.USER);

    public void onLogin(ActionEvent actionEvent) throws IOException {
        if (validate()){
            String username = usernameField.getText();
            String password = passwordField.getText();

            UserDTO user = userService.getUserByEmail(username);

            if (user == null){
                NotificationUtils.showError("Login Error","User not found for "+username);
                return;
            }

            if (user != null && PasswordUtils.checkPassword(password,user.getPassword())){
                AlertUtils.showSuccess("Login Success","Login Successfully");
                AuthUtil.setCurrentUser(user);
                DashboardNavigator.mainNavigator("/view/layout/DashboardLayout.fxml");
            } else {
                AlertUtils.showError("Login Error","Invalid Username or Password");
            }
        }
    }

    public boolean validate(){
        if (usernameField.getText().isEmpty()){
            NotificationUtils.showError("Validation Error","Username is required");
            usernameField.requestFocus();
            return false;
        } else if (passwordField.getText().isEmpty()) {
            NotificationUtils.showError("Validation Error","Password is required");
            passwordField.requestFocus();
            return false;
        }
        return true;
    }
}
