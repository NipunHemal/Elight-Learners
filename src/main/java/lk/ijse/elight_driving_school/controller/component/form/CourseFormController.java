package lk.ijse.elight_driving_school.controller.component.form;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import lk.ijse.elight_driving_school.controller.pages.CoursesController;
import lk.ijse.elight_driving_school.dto.CourseDTO;
import lk.ijse.elight_driving_school.enums.ServiceTypes;
import lk.ijse.elight_driving_school.service.ServiceFactory;
import lk.ijse.elight_driving_school.service.custom.CourseService;
import lk.ijse.elight_driving_school.util.AlertUtils;
import lk.ijse.elight_driving_school.util.DialogUtil;
import lk.ijse.elight_driving_school.util.NotificationUtils;
import lk.ijse.elight_driving_school.util.ValidationUtils;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CourseFormController implements Initializable {

    public Label lblFormTitle;
    @FXML
    private ComboBox<CourseDTO> cmbInstrucre;

    @FXML
    private TextField txtCourseName;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtDuration;

    @FXML
    private TextField txtFree;

    CoursesController coursesController;
    CourseDTO mainCourseDTO;
    CourseService courseService = ServiceFactory.getInstance().getService(ServiceTypes.COURSE);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{
            initInstructorCombo();
        } catch (Exception e) {
            e.printStackTrace();
            NotificationUtils.showError("Error", "Error Loading Courses");
        }
    }

    public void init(CoursesController coursesController,CourseDTO courseDTO) {
        this.coursesController = coursesController;
        this.mainCourseDTO = courseDTO;
        handelUpdateCreate();
    }

    private void handelUpdateCreate(){
        if (mainCourseDTO != null) {
            lblFormTitle.setText("Update Course");
            txtCourseName.setText(mainCourseDTO.getCourseName());
            txtDescription.setText(mainCourseDTO.getDescription());
            txtDuration.setText(mainCourseDTO.getDuration());
            txtFree.setText(String.valueOf(mainCourseDTO.getFree()));
            cmbInstrucre.getSelectionModel().select(mainCourseDTO);
        }  else {
            lblFormTitle.setText("Add New Course");
            clearFields();
        }
    }

    public void clearFields() {
        txtCourseName.clear();
        txtDescription.clear();
        txtDuration.clear();
        txtFree.clear();
        cmbInstrucre.getSelectionModel().clearSelection();
    }

    public boolean validateForm() {
        CourseDTO selectedInstructor = cmbInstrucre.getSelectionModel().getSelectedItem();

        boolean isValidName = ValidationUtils.validateInput(txtCourseName, "empty");
        boolean isValidDescription = ValidationUtils.validateInput(txtDescription, "empty");
        boolean isValidDuration = ValidationUtils.validateInput(txtDuration, "empty");
        boolean isValidFree = ValidationUtils.validateInput(txtFree, "empty") && ValidationUtils.validateInput(txtFree, "numeric");

        if(!(isValidName && isValidDescription && isValidDuration && isValidFree && selectedInstructor != null)) {
            return false;
        }
        return true;
    }


    public void handelSubmit(){
        if (mainCourseDTO != null) {
            updateProject();
        } else {
            saveProject();
        }
    }

    public void saveProject() {
        String name = txtCourseName.getText();
        String description = txtDescription.getText();
        String duration = txtDuration.getText();
        Double free = Double.parseDouble(txtFree.getText());
        CourseDTO selectedInstructor = cmbInstrucre.getSelectionModel().getSelectedItem();

        if (validateForm()) {
            CourseDTO courseDTO = new CourseDTO().builder()
                    .courseName(name)
                    .description(description)
                    .duration(duration)
                    .free(free)
                    .instructorId(selectedInstructor != null ? selectedInstructor.getInstructorId() : "0")
                    .build();
            try {
                boolean isSave = courseService.saveCourses(courseDTO);
                if (isSave) {
                    coursesController.initialize(null, null);
                    AlertUtils.showSuccess("Success", "Course added successfully.");
                    clearFields();
                    coursesController.initialize(null, null);
                    DialogUtil.close();
                } else {
                    AlertUtils.showError("Error", "Failed to add course.");
                }
            } catch (Exception e) {
                AlertUtils.showError("Error", "Failed to add course : " + e.getMessage());
                throw new RuntimeException(e);
            }
        } else {
            NotificationUtils.showError("Validation Error", "Please fill all the fields correctly.");
        }
    }

    public void updateProject() {
        String name = txtCourseName.getText();
        String description = txtDescription.getText();
        String duration = txtDuration.getText();
        Double free = Double.parseDouble(txtFree.getText());
        CourseDTO selectedInstructor = cmbInstrucre.getSelectionModel().getSelectedItem();

        if (validateForm()) {
            CourseDTO courseDTO = new CourseDTO().builder()
                    .courseName(name)
                    .description(description)
                    .duration(duration)
                    .free(free)
                    .instructorId(selectedInstructor != null ? selectedInstructor.getInstructorId() : "0")
                    .build();
            try {
                boolean isUpdate = courseService.updateCourses(courseDTO);
                if (isUpdate) {
                    coursesController.initialize(null, null);
                    AlertUtils.showSuccess("Success", "Course update successfully.");
                    clearFields();
                    coursesController.initialize(null, null);
                    DialogUtil.close();
                } else {
                    AlertUtils.showError("Error", "Failed to update course.");
                }
            } catch (Exception e) {
                AlertUtils.showError("Error", "Failed to update course : " + e.getMessage());
                throw new RuntimeException(e);
            }
        } else {
            NotificationUtils.showError("Validation Error", "Please fill all the fields correctly.");
        }
    }

    public void initInstructorCombo(){
        try{
            List<CourseDTO> courseDTOS = courseService.getAllCourses();
            ObservableList<CourseDTO> courseObservableList = FXCollections.observableArrayList(courseDTOS);
            cmbInstrucre.setItems(courseObservableList);

            // Show only courseName in dropdown
            cmbInstrucre.setCellFactory(lv -> new ListCell<>() {
                @Override
                protected void updateItem(CourseDTO item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty || item == null ? null : item.getCourseName());
                }
            });

            // Show only courseName when selected
            cmbInstrucre.setButtonCell(new ListCell<CourseDTO>() {
                @Override
                protected void updateItem(CourseDTO item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty || item == null ? null : item.getCourseName());
                }
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
