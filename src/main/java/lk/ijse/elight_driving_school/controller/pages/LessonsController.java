package lk.ijse.elight_driving_school.controller.pages;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class LessonsController {

    public TableView tableLesson;
    @FXML
    private TableColumn<?, ?> Date;

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TableColumn<?, ?> colCourseId;

    @FXML
    private TableColumn<?, ?> colEndTime;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colInstructorId;

    @FXML
    private TableColumn<?, ?> colStartTime;

    @FXML
    private TableColumn<?, ?> colStatus;

    @FXML
    private TableColumn<?, ?> colStudentId;

    @FXML
    private TableView<?> tableCourse;

    @FXML
    private TextField tstSearch;

    @FXML
    private Label txtCourseCount;

    @FXML
    void btnAddOnAction(ActionEvent event) {

    }

}
