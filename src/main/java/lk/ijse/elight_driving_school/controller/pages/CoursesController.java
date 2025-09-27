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
import lk.ijse.elight_driving_school.controller.component.form.CourseFormController;
import lk.ijse.elight_driving_school.dto.CourseDTO;
import lk.ijse.elight_driving_school.dto.tm.CourseTM;
import lk.ijse.elight_driving_school.enums.ServiceTypes;
import lk.ijse.elight_driving_school.mapper.CourseMapper;
import lk.ijse.elight_driving_school.service.ServiceFactory;
import lk.ijse.elight_driving_school.service.custom.CourseService;
import lk.ijse.elight_driving_school.util.AlertUtils;
import lk.ijse.elight_driving_school.util.DialogUtil;
import lk.ijse.elight_driving_school.util.NotificationUtils;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CoursesController implements Initializable {

    @FXML
    private TableColumn<CourseTM, Pane> colAction;
    @FXML
    private TableColumn<CourseTM, String> colDescription;
    @FXML
    private TableColumn<CourseTM, String> colId;
    @FXML
    private TableColumn<CourseTM, String> colName;
    @FXML
    private TableColumn<CourseTM, Double> colFree;
    @FXML
    private TableView<CourseTM> tableCourse;

    @FXML
    private TextField tstSearch;
    @FXML
    private Label txtCourseCount;

    CourseService courseService = ServiceFactory.getInstance().getService(ServiceTypes.COURSE);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{
            initializeTable();
            loadCourses();
        } catch (Exception e) {
            NotificationUtils.showError("Error", e.getMessage());
        }
    }

    public void initializeTable(){
        colId.setCellValueFactory(new PropertyValueFactory<>("courseId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        colFree.setCellValueFactory(new PropertyValueFactory<>("fee"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("action"));
    }

    @FXML
    void btnAddOnAction(ActionEvent event) {
       try{
           FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/components/form/CourseForm.fxml"));
           Parent customContent = loader.load();
           loader.<CourseFormController>getController().init(this,null);
           DialogUtil.showCustom(null, null, customContent,
                   "Save", "Cancel",
                   () -> loader.<CourseFormController>getController().handelSubmit(),
                   null);
       } catch (Exception e) {
           e.printStackTrace();
           NotificationUtils.showError("Error Loading Form", e.getMessage());
       }
    }

    public void loadCourses(){
        try {
            tableCourse.getItems().clear();
            List<CourseDTO> courses = courseService.getAllCourses();
            tableCourse.getItems().addAll(List.copyOf(courses.stream().map(c->{
                CourseTM tm = CourseMapper.toTM(c);
                tm.setAction(getAction(c));
                return tm;
            }).toList()));
            txtCourseCount.setText(String.valueOf(courses.size()));
        } catch (Exception e) {
            e.getMessage();
            NotificationUtils.showError("Error Loading Courses", e.getMessage());
        }
    }

    void handelDelete(String id){
        try{
            if (AlertUtils.showConform("Delete Course", "Are you sure you want to delete this course?")) {
                courseService.deleteCourses(id);
                AlertUtils.showSuccess("Success", "Course Deleted Successfully");
                loadCourses();
            }
        } catch (Exception e) {
            e.printStackTrace();
            NotificationUtils.showError("Error", e.getMessage());
        }
    }

    Pane getAction(CourseDTO dto){
        try {
            Pane action = new Pane();
            Button btnEdit = new Button("✏");
            btnEdit.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;");
            btnEdit.setPrefWidth(30);
            btnEdit.setLayoutX(40);
            btnEdit.setCursor(javafx.scene.Cursor.HAND);
            btnEdit.setOnAction(event -> {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/components/form/CourseForm.fxml"));
                    Parent customContent = loader.load();

                    CourseFormController controller = loader.getController();
                    controller.init(this, dto); // pass dto instead of null

                    DialogUtil.showCustom(
                            null, null, customContent,
                            "Update", "Cancel",
                            () -> controller.handelSubmit(),
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
            btnDelete.setOnAction(event -> handelDelete(dto.getCourseId()));

            action.getChildren().addAll(btnEdit, btnDelete);
            return action;
        } catch (Exception e) {
            e.printStackTrace();
            NotificationUtils.showError("Error load action : ", e.getMessage());
            return null;
        }
    }
}
