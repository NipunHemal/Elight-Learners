package lk.ijse.elight_driving_school.controller.component.form;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import lk.ijse.elight_driving_school.controller.pages.UserController;
import lk.ijse.elight_driving_school.dto.UserDTO;
import lk.ijse.elight_driving_school.enums.Role;
import lk.ijse.elight_driving_school.enums.ServiceTypes;
import lk.ijse.elight_driving_school.service.ServiceFactory;
import lk.ijse.elight_driving_school.service.custom.UserService;
import lk.ijse.elight_driving_school.util.AlertUtils;
import lk.ijse.elight_driving_school.util.DialogUtil;
import lk.ijse.elight_driving_school.util.NotificationUtils;
import lk.ijse.elight_driving_school.util.ValidationUtils;

import java.net.URL;
import java.util.ResourceBundle;

public class UserFormController implements Initializable {
    public Label lblFormTitle;
    @FXML
    private TextField txtUserName;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtPassword;
    @FXML
    private TextField txtConfirmPassword;
    @FXML
    private TextField txtContact;
    @FXML
    private ComboBox<Role> cmbRole;
    @FXML
    private ComboBox<String> cmbStatus;

    UserController userController;
    UserDTO mainUserDto;

    UserService userService = ServiceFactory.getInstance().getService(ServiceTypes.USER);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Role> roles = FXCollections.observableArrayList(Role.values());
        cmbRole.setItems(roles);
        ObservableList<String> statuses = FXCollections.observableArrayList("Active", "Inactive");
        cmbStatus.setItems(statuses);
    }

    public void init(UserController userController, UserDTO userDTO) {
        this.userController = userController;
        this.mainUserDto = userDTO;
        handelCreateUpdate();
    }

    public void handelCreateUpdate(){
        if (mainUserDto != null) {
            lblFormTitle.setText("Update Course");
            txtUserName.setText(mainUserDto.getUsername());
            txtEmail.setText(mainUserDto.getEmail());
            txtPassword.setText(mainUserDto.getPassword());
            txtConfirmPassword.setText(mainUserDto.getPassword());
            txtContact.setVisible(false);
            cmbRole.getSelectionModel().select(mainUserDto.getRole());
            cmbStatus.getSelectionModel().select(mainUserDto.getStatus());
        }  else {
            lblFormTitle.setText("Add New Course");
            clearFields();
        }
    }

    public void clearFields() {
        txtUserName.clear();
        txtEmail.clear();
        txtPassword.clear();
        txtConfirmPassword.clear();
        txtContact.clear();
        cmbRole.getSelectionModel().clearSelection();
        cmbStatus.getSelectionModel().clearSelection();
    }

    public boolean validateForm() {
        boolean isValidUserName = ValidationUtils.validateInput(txtUserName, "empty");
        boolean isValidEmail = ValidationUtils.validateInput(txtEmail, "email");
        boolean isValidPassword = ValidationUtils.validateInput(txtPassword, "empty");
        boolean isValidConfirmPassword = txtPassword.getText().equals(txtConfirmPassword.getText());
        boolean isValidContact = ValidationUtils.validateInput(txtContact, "empty") && ValidationUtils.validateInput(txtContact, "numeric");
        boolean isValidRole = cmbRole.getSelectionModel().getSelectedItem() != null;
        boolean isValidStatus = cmbStatus.getSelectionModel().getSelectedItem() != null;
        if (!(isValidUserName && isValidEmail && isValidPassword && isValidConfirmPassword && isValidContact && isValidRole && isValidStatus)) {
            return false;
        }
        return true;
    }

    public void saveUser() {
        String username = txtUserName.getText();
        String email = txtEmail.getText();
        String password = txtPassword.getText();
        Role role = cmbRole.getSelectionModel().getSelectedItem();
        String status = cmbStatus.getSelectionModel().getSelectedItem();
        if (validateForm()) {
            UserDTO userDTO = UserDTO.builder()
                    .username(username)
                    .email(email)
                    .password(password)
                    .role(role)
                    .status(status)
                    .build();
            try {
                boolean isSave = userService.saveUsers(userDTO);
                if (isSave) {
                    userController.initialize(null, null);
                    DialogUtil.close();
                    AlertUtils.showSuccess("Success", "User added successfully.");
                    clearFields();
                    if (userController != null) userController.initialize(null, null);
                } else {
                    AlertUtils.showError("Error", "Failed to add user.");
                }
            } catch (Exception e) {
                AlertUtils.showError("Error", "Failed to add user : " + e.getMessage());
                throw new RuntimeException(e);
            }
        } else {
            NotificationUtils.showError("Validation Error", "Please fill all the fields correctly.");
        }
    }

    public void updateUser() {
        String username = txtUserName.getText();
        String email = txtEmail.getText();
        String password = txtPassword.getText();
        Role role = cmbRole.getSelectionModel().getSelectedItem();
        String status = cmbStatus.getSelectionModel().getSelectedItem();
        if (validateForm()) {
            UserDTO userDTO = UserDTO.builder()
                    .userId(mainUserDto.getUserId())
                    .username(username)
                    .email(email)
                    .password(password)
                    .role(role)
                    .status(status)
                    .build();
            try {
                boolean isUpdate = userService.updateUsers(userDTO);
                if (isUpdate) {
                    userController.initialize(null, null);
                    DialogUtil.close();
                    AlertUtils.showSuccess("Success", "User updated successfully.");
                    clearFields();
                    if (userController != null) userController.initialize(null, null);
                } else {
                    AlertUtils.showError("Error", "Failed to update user.");
                }
            } catch (Exception e) {
                AlertUtils.showError("Error", "Failed to update user : " + e.getMessage());
                throw new RuntimeException(e);
            }
        } else {
            NotificationUtils.showError("Validation Error", "Please fill all the fields correctly.");
        }
    }

    public void handelSubmit() {
        if (mainUserDto != null) {
            updateUser();
        } else {
            saveUser();
        }
    }
}
