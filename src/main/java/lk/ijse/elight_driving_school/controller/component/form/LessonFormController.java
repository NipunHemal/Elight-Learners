package lk.ijse.elight_driving_school.controller.component.form;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import lk.ijse.elight_driving_school.dto.CourseDTO;
import lk.ijse.elight_driving_school.dto.InstructorDTO;
import lk.ijse.elight_driving_school.dto.StudentDTO;
import lk.ijse.elight_driving_school.dto.LessonsDTO;
import lk.ijse.elight_driving_school.enums.ServiceTypes;
import lk.ijse.elight_driving_school.service.ServiceFactory;
import lk.ijse.elight_driving_school.service.custom.CourseService;
import lk.ijse.elight_driving_school.service.custom.InstructorService;
import lk.ijse.elight_driving_school.service.custom.StudentService;
import lk.ijse.elight_driving_school.service.custom.LessonsService;
import lk.ijse.elight_driving_school.util.AlertUtils;
import lk.ijse.elight_driving_school.util.NotificationUtils;
import lk.ijse.elight_driving_school.util.ValidationUtils;

import java.net.URL;
import java.sql.Time;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class LessonFormController implements Initializable {

    @FXML
    private Button cancelButton;

    @FXML
    private Button clearButton;

    @FXML
    private ComboBox<?> cmbCourse;

    @FXML
    private ComboBox<?> cmbInstrucre;

    @FXML
    private ComboBox<?> cmbStudent;

    @FXML
    private Label lblFormSubTitle;

    @FXML
    private Label lblFormTitle;

    @FXML
    private DatePicker lessonDatePicker;

    @FXML
    private Button saveButton;

    @FXML
    private TextField txtEndTime;

    @FXML
    private TextField txtStartTime;

    @FXML
    private TextField txtStatus;

    LessonsService lessonsService = ServiceFactory.getInstance().getService(ServiceTypes.LESSONS);
    CourseService courseService = ServiceFactory.getInstance().getService(ServiceTypes.COURSE);
    InstructorService instructorService = ServiceFactory.getInstance().getService(ServiceTypes.INSTRUCTOR);
    StudentService studentService = ServiceFactory.getInstance().getService(ServiceTypes.STUDENT);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            initComboBoxes();
        } catch (Exception e) {
            e.printStackTrace();
            NotificationUtils.showError("Error", "Error Loading Lesson Form Data");
        }
    }

    public void clearFields() {
        txtStartTime.clear();
        txtEndTime.clear();
        txtStatus.clear();
        lessonDatePicker.setValue(null);
        cmbCourse.getSelectionModel().clearSelection();
        cmbInstrucre.getSelectionModel().clearSelection();
        cmbStudent.getSelectionModel().clearSelection();
    }

    public boolean validateForm() {
        boolean isValidStartTime = ValidationUtils.validateInput(txtStartTime, "empty");
        boolean isValidEndTime = ValidationUtils.validateInput(txtEndTime, "empty");
        boolean isValidStatus = ValidationUtils.validateInput(txtStatus, "empty");
        boolean isValidDate = lessonDatePicker.getValue() != null;
        Object selectedCourse = cmbCourse.getSelectionModel().getSelectedItem();
        Object selectedInstructor = cmbInstrucre.getSelectionModel().getSelectedItem();
        Object selectedStudent = cmbStudent.getSelectionModel().getSelectedItem();
        if (!(isValidStartTime && isValidEndTime && isValidStatus && isValidDate && selectedCourse != null && selectedInstructor != null && selectedStudent != null)) {
            return false;
        }
        return true;
    }

    public void saveProject() {
        CourseDTO selectedCourse = (CourseDTO) cmbCourse.getSelectionModel().getSelectedItem();
        InstructorDTO selectedInstructor = (InstructorDTO) cmbInstrucre.getSelectionModel().getSelectedItem();
        StudentDTO selectedStudent = (StudentDTO) cmbStudent.getSelectionModel().getSelectedItem();
        Date lessonDate = java.sql.Date.valueOf(lessonDatePicker.getValue());
        Time startTime = Time.valueOf(txtStartTime.getText());
        Time endTime = Time.valueOf(txtEndTime.getText());
        String status = txtStatus.getText();
        if (validateForm()) {
            LessonsDTO lessonsDTO = LessonsDTO.builder()
                    .courseId(selectedCourse.getCourseId())
                    .instructorId(selectedInstructor.getInstructorId())
                    .studentId(selectedStudent.getStudentId())
                    .lessonDate(lessonDate)
                    .startTime(startTime)
                    .endTime(endTime)
                    .status(status)
                    .build();
            try {
                boolean isSave = lessonsService.saveLessons(lessonsDTO);
                if (isSave) {
                    AlertUtils.showSuccess("Success", "Lesson added successfully.");
                    clearFields();
                } else {
                    AlertUtils.showError("Error", "Failed to add lesson.");
                }
            } catch (Exception e) {
                AlertUtils.showError("Error", "Failed to add lesson : " + e.getMessage());
                throw new RuntimeException(e);
            }
        } else {
            NotificationUtils.showError("Validation Error", "Please fill all the fields correctly.");
        }
    }

    public void initComboBoxes() {
        try {
            List<CourseDTO> courses = courseService.getAllCourses();
            ObservableList<CourseDTO> courseList = FXCollections.observableArrayList(courses);
            cmbCourse.setItems(courseList);
            cmbCourse.setCellFactory(lv -> new javafx.scene.control.ListCell<>() {
                @Override
                protected void updateItem(CourseDTO item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty || item == null ? null : item.getCourseName());
                }
            });
            cmbCourse.setButtonCell(new javafx.scene.control.ListCell<CourseDTO>() {
                @Override
                protected void updateItem(CourseDTO item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty || item == null ? null : item.getCourseName());
                }
            });

            List<InstructorDTO> instructors = instructorService.getAllInstructors();
            ObservableList<InstructorDTO> instructorList = FXCollections.observableArrayList(instructors);
            cmbInstrucre.setItems(instructorList);
            cmbInstrucre.setCellFactory(lv -> new javafx.scene.control.ListCell<>() {
                @Override
                protected void updateItem(InstructorDTO item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty || item == null ? null : item.getFirstName() + " " + item.getLastName());
                }
            });
            cmbInstrucre.setButtonCell(new javafx.scene.control.ListCell<InstructorDTO>() {
                @Override
                protected void updateItem(InstructorDTO item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty || item == null ? null : item.getFirstName() + " " + item.getLastName());
                }
            });

            List<StudentDTO> students = studentService.getAllStudents();
            ObservableList<StudentDTO> studentList = FXCollections.observableArrayList(students);
            cmbStudent.setItems(studentList);
            cmbStudent.setCellFactory(lv -> new javafx.scene.control.ListCell<>() {
                @Override
                protected void updateItem(StudentDTO item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty || item == null ? null : item.getFirstName() + " " + item.getLastName());
                }
            });
            cmbStudent.setButtonCell(new javafx.scene.control.ListCell<StudentDTO>() {
                @Override
                protected void updateItem(StudentDTO item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty || item == null ? null : item.getFirstName() + " " + item.getLastName());
                }
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}