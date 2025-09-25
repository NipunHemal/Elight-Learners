package lk.ijse.elight_driving_school.controller.pages;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class UserController {

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colRole;

    @FXML
    private TableColumn<?, ?> colStatus;

    @FXML
    private TableColumn<?, ?> colUsername;

    @FXML
    private TableView<?> tableUser;

    @FXML
    private TextField tstSearch;

    @FXML
    private Label txtCourseCount;

    @FXML
    void btnAddOnAction(ActionEvent event) {

    }

}
