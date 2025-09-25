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
import lk.ijse.elight_driving_school.dto.tm.LessonsTM;
import lk.ijse.elight_driving_school.util.NotificationUtils;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class LessonsController implements Initializable {

    public TableView<LessonsTM> tableLesson;
    @FXML
    private TableColumn<LessonsTM , Date> Date;

    @FXML
    private TableColumn<LessonsTM , Pane> colAction;

    @FXML
    private TableColumn<LessonsTM , String > colCourseId;

    @FXML
    private TableColumn<LessonsTM , String > colEndTime;

    @FXML
    private TableColumn<LessonsTM , String > colId;

    @FXML
    private TableColumn<LessonsTM , String > colInstructorId;

    @FXML
    private TableColumn<LessonsTM , String > colStartTime;

    @FXML
    private TableColumn<LessonsTM , String > colStatus;

    @FXML
    private TableColumn<LessonsTM , String > colStudentId;


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
            loadLessons();
        } catch (Exception e) {
            NotificationUtils.showError("Error", e.getMessage());
        }
    }

    private void initializeTable() {
        colId.setCellValueFactory(new PropertyValueFactory<>("lessonId"));
        colStudentId.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        colInstructorId.setCellValueFactory(new PropertyValueFactory<>("instructorId"));
        colCourseId.setCellValueFactory(new PropertyValueFactory<>("courseId"));
        Date.setCellValueFactory(new PropertyValueFactory<>("lessonDate"));
        colStartTime.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        colEndTime.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("action"));
    }

    private void loadLessons() {

    }
}
