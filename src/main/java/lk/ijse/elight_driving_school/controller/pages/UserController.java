package lk.ijse.elight_driving_school.controller.pages;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import lk.ijse.elight_driving_school.controller.component.form.InstructorFormController;
import lk.ijse.elight_driving_school.controller.component.form.UserFormController;
import lk.ijse.elight_driving_school.dto.UserDTO;
import lk.ijse.elight_driving_school.dto.tm.StudentTM;
import lk.ijse.elight_driving_school.dto.tm.UserTM;
import lk.ijse.elight_driving_school.enums.ServiceTypes;
import lk.ijse.elight_driving_school.mapper.UserMapper;
import lk.ijse.elight_driving_school.service.ServiceFactory;
import lk.ijse.elight_driving_school.service.custom.UserService;
import lk.ijse.elight_driving_school.util.AlertUtils;
import lk.ijse.elight_driving_school.util.DialogUtil;
import lk.ijse.elight_driving_school.util.NotificationUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserController implements Initializable {

    public TableView<UserTM> tableUser;
    @FXML
    private TableColumn<UserTM, Pane> colAction;

    @FXML
    private TableColumn<UserTM , String> colEmail;

    @FXML
    private TableColumn<UserTM , String> colId;

    @FXML
    private TableColumn<UserTM , String> colRole;

    @FXML
    private TableColumn<UserTM , String> colStatus;

    @FXML
    private TableColumn<UserTM , String> colUsername;

    @FXML
    private TextField tstSearch;

    @FXML
    private Label txtCourseCount;

    private final UserService userService = ServiceFactory.getInstance().getService(ServiceTypes.USER);

    @FXML
    void btnAddOnAction(ActionEvent event) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/components/form/UserForm.fxml"));
            Parent customContent = loader.load();
            loader.<UserFormController>getController().init(this,null);
            DialogUtil.showCustom(null, null, customContent,
                    "Save", "Cancel",
                    () -> loader.<UserFormController>getController().handelSubmit(),
                    null);
        } catch (Exception e) {
            e.printStackTrace();
            NotificationUtils.showError("Error Loading Form", e.getMessage());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{
            initializeTable();
            loadUser();
        } catch (Exception e) {
            e.printStackTrace();
            NotificationUtils.showError("Error", e.getMessage());
        }
    }

    private void initializeTable() {
        colId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        colUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colRole.setCellValueFactory(new PropertyValueFactory<>("role"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("action"));

    }

    private void loadUser() {
        try {
            tableUser.getItems().clear();
            java.util.List<UserDTO> users = userService.getAllUsers();
            tableUser.getItems().addAll(users.stream().map(u->{
                UserTM tm = UserMapper.toTM(u);
                tm.setAction(getAction(u));
                return tm;
            }).toList());
            txtCourseCount.setText(String.valueOf(users.size()));
        } catch (Exception e) {
            e.printStackTrace();
            NotificationUtils.showError("Error Loading Users", e.getMessage());
        }
    }

    void handelDelete(String id){
        try{
            if (AlertUtils.showConform("Delete User", "Are you sure you want to delete this user?")) {
                userService.deleteUsers(id);
                AlertUtils.showSuccess("Success", "User Deleted Successfully");
                loadUser();
            }
        } catch (Exception e) {
            e.printStackTrace();
            NotificationUtils.showError("Error", e.getMessage());
        }
    }

    Pane getAction(UserDTO dto){
        try {
            Pane action = new Pane();
            Button btnEdit = new Button("✏");
            btnEdit.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;");
            btnEdit.setPrefWidth(30);
            btnEdit.setLayoutX(40);
            btnEdit.setCursor(javafx.scene.Cursor.HAND);
            btnEdit.setOnAction(event -> {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/components/form/UserForm.fxml"));
                    Parent customContent = loader.load();

                    UserFormController controller = loader.getController();
                    controller.init(this, dto); // pass dto instead of null

                    DialogUtil.showCustom(
                            null, null, customContent,
                            "Update", "Cancel",
                            controller::handelSubmit,
                            null
                    );
                } catch (IOException e) {
                    e.printStackTrace();
                    NotificationUtils.showError("Error loading course form", e.getMessage());
                }
            });

            Button btnDelete = new Button("🗑");
            btnDelete.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-weight: bold;");
            btnDelete.setPrefWidth(30);
            btnDelete.setLayoutX(0);
            btnDelete.setCursor(javafx.scene.Cursor.HAND);
            btnDelete.setOnAction(event -> handelDelete(dto.getUserId()));

            action.getChildren().addAll(btnEdit, btnDelete);
            return action;
        } catch (Exception e) {
            e.printStackTrace();
            NotificationUtils.showError("Error load action : ", e.getMessage());
            return null;
        }
    }
}
