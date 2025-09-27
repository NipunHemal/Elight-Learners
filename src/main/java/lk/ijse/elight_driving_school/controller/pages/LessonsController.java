package lk.ijse.elight_driving_school.controller.pages;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import lk.ijse.elight_driving_school.controller.component.form.InstructorFormController;
import lk.ijse.elight_driving_school.controller.component.form.LessonFormController;
import lk.ijse.elight_driving_school.dto.InstructorDTO;
import lk.ijse.elight_driving_school.dto.LessonsDTO;
import lk.ijse.elight_driving_school.dto.tm.LessonsTM;
import lk.ijse.elight_driving_school.enums.ServiceTypes;
import lk.ijse.elight_driving_school.mapper.LessonMapper;
import lk.ijse.elight_driving_school.service.ServiceFactory;
import lk.ijse.elight_driving_school.service.custom.LessonsService;
import lk.ijse.elight_driving_school.util.AlertUtils;
import lk.ijse.elight_driving_school.util.DialogUtil;
import lk.ijse.elight_driving_school.util.NotificationUtils;

import java.io.IOException;
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

    LessonsService lessonsService = ServiceFactory.getInstance().getService(ServiceTypes.LESSONS);

    @FXML
    void btnAddOnAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/components/form/LessonForm.fxml"));
            Parent customContent = loader.load();
            loader.<LessonFormController>getController().init(this, null);
            DialogUtil.showCustom(null, null, customContent,
                    "Save", "Cancel",
                    () -> loader.<LessonFormController>getController().saveProject(),
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
        try {
            tableLesson.getItems().clear();
            java.util.List<LessonsDTO> lessons = lessonsService.getAllLessons();
            tableLesson.getItems().addAll(lessons.stream().map(l->{
                LessonsTM tm = LessonMapper.toTM(l);
                tm.setAction(getAction(l));
                return tm;
            }).toList());
            txtCourseCount.setText(String.valueOf(lessons.size()));
        } catch (Exception e) {
            NotificationUtils.showError("Error Loading Lessons", e.getMessage());
        }
    }

    void handelDelete(String id){
        try{
            if (AlertUtils.showConform("Delete Instructor", "Are you sure you want to delete this lesson?")) {
                lessonsService.deleteLessons(id);
                AlertUtils.showSuccess("Success", "Lesson Deleted Successfully");
                loadLessons();
            }
        } catch (Exception e) {
            e.printStackTrace();
            NotificationUtils.showError("Error", e.getMessage());
        }
    }

    Pane getAction(LessonsDTO dto){
        try {
            Pane action = new Pane();
            Button btnEdit = new Button("✏");
            btnEdit.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;");
            btnEdit.setPrefWidth(30);
            btnEdit.setLayoutX(40);
            btnEdit.setCursor(javafx.scene.Cursor.HAND);
            btnEdit.setOnAction(event -> {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/components/form/LessonForm.fxml"));
                    Parent customContent = loader.load();

                    LessonFormController controller = loader.getController();
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
            btnDelete.setOnAction(event -> handelDelete(dto.getLessonId()));

            action.getChildren().addAll(btnEdit, btnDelete);
            return action;
        } catch (Exception e) {
            e.printStackTrace();
            NotificationUtils.showError("Error load action : ", e.getMessage());
            return null;
        }
    }
}
