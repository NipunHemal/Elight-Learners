package lk.ijse.elight_driving_school.controller.pages;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import lk.ijse.elight_driving_school.dto.tm.UserTM;
import lk.ijse.elight_driving_school.util.NotificationUtils;

import java.net.URL;
import java.util.ResourceBundle;

public class UserController implements Initializable {

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

    @FXML
    void btnAddOnAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{
            initializeTable();
            loadUser();
        } catch (Exception e) {
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
    }

}
