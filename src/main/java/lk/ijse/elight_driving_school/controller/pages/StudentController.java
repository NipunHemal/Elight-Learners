package lk.ijse.elight_driving_school.controller.pages;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import lk.ijse.elight_driving_school.controller.component.form.CourseFormController;
import lk.ijse.elight_driving_school.controller.component.form.StudentFormController;
import lk.ijse.elight_driving_school.dto.StudentDTO;
import lk.ijse.elight_driving_school.dto.tm.StudentTM;
import lk.ijse.elight_driving_school.enums.ServiceTypes;
import lk.ijse.elight_driving_school.mapper.StudentMapper;
import lk.ijse.elight_driving_school.service.ServiceFactory;
import lk.ijse.elight_driving_school.service.custom.StudentService;
import lk.ijse.elight_driving_school.util.AlertUtils;
import lk.ijse.elight_driving_school.util.DialogUtil;
import lk.ijse.elight_driving_school.util.NotificationUtils;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class StudentController implements Initializable {

    public TableView<StudentTM> tableStudent;
    @FXML
    private TableColumn<StudentTM , String> colAction;

    @FXML
    private TableColumn<StudentTM , String> colAddress;

    @FXML
    private TableColumn<StudentTM , Date> colDob;

    @FXML
    private TableColumn<StudentTM , String> colEmail;

    @FXML
    private TableColumn<StudentTM , String> colFirstname;

    @FXML
    private TableColumn<StudentTM , String> colId;

    @FXML
    private TableColumn<StudentTM , String> colLastname;

    @FXML
    private TableColumn<StudentTM , String> colPhone;

    @FXML
    private TableColumn<StudentTM , Date> colRegDate;

    @FXML
    private TableColumn<StudentTM , String> colCourseId;

    @FXML
    private TextField tstSearch;

    @FXML
    private Label txtCourseCount;

    StudentService studentService = ServiceFactory.getInstance().getService(ServiceTypes.STUDENT);

    @FXML
    void btnAddOnAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/components/form/StudentForm.fxml"));
            Parent customContent = loader.load();
            loader.<StudentFormController>getController().init(this,null);
            DialogUtil.showCustom(null, null, customContent,
                    "Save", "Cancel",
                    () -> loader.<StudentFormController>getController().handelSubmit(),
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
            loadStudent();
        } catch (Exception e) {
            e.printStackTrace();
            NotificationUtils.showError("Error", e.getMessage());
        }
    }

    private void initializeTable() {
        colId.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        colFirstname.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        colLastname.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colDob.setCellValueFactory(new PropertyValueFactory<>("dob"));
        colRegDate.setCellValueFactory(new PropertyValueFactory<>("registrationDate"));
        colCourseId.setCellValueFactory(new PropertyValueFactory<>("courseId"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("action"));
    }

    private void loadStudent() {
        try {
            tableStudent.getItems().clear();
            java.util.List<StudentDTO> students = studentService.getAllStudents();
            tableStudent.getItems().addAll(students.stream().map(s->{
                StudentTM tm = StudentMapper.toTM(s);
                tm.setAction(getAction(s));
                return tm;
            }).toList());
            txtCourseCount.setText(String.valueOf(students.size()));
        } catch (Exception e) {
            e.printStackTrace();
            NotificationUtils.showError("Error Loading Students", e.getMessage());
        }
    }

    void handelDelete(String id){
        try{
            if (AlertUtils.showConform("Delete Student", "Are you sure you want to delete this student?")) {
                AlertUtils.showSuccess("Success", "Lesson Deleted Successfully");
                studentService.deleteStudents(id);
                loadStudent();
            }
        } catch (Exception e) {
            e.printStackTrace();
            NotificationUtils.showError("Error", e.getMessage());
        }
    }

    Pane getAction(StudentDTO dto){
        try {
            Pane action = new Pane();
            Button btnEdit = new Button("✏");
            btnEdit.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;");
            btnEdit.setPrefWidth(30);
            btnEdit.setLayoutX(40);
            btnEdit.setCursor(javafx.scene.Cursor.HAND);
            btnEdit.setOnAction(event -> {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/components/form/StudentForm.fxml"));
                    Parent customContent = loader.load();

                    StudentFormController controller = loader.getController();
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
            btnDelete.setOnAction(event -> handelDelete(dto.getStudentId()));

            action.getChildren().addAll(btnEdit, btnDelete);
            return action;
        } catch (Exception e) {
            e.printStackTrace();
            NotificationUtils.showError("Error load action : ", e.getMessage());
            return null;
        }
    }

}
