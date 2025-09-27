package lk.ijse.elight_driving_school.controller.component.form;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import lk.ijse.elight_driving_school.controller.pages.CoursesController;
import lk.ijse.elight_driving_school.dto.CourseDTO;
import lk.ijse.elight_driving_school.dto.InstructorDTO;
import lk.ijse.elight_driving_school.enums.ServiceTypes;
import lk.ijse.elight_driving_school.service.ServiceFactory;
import lk.ijse.elight_driving_school.service.custom.CourseService;
import lk.ijse.elight_driving_school.service.custom.InstructorService;
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
    private ComboBox<InstructorDTO> cmbInstrucre;

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
    InstructorDTO mainInstructorDTO;
    CourseService courseService = ServiceFactory.getInstance().getService(ServiceTypes.COURSE);
    InstructorService instructorService = ServiceFactory.getInstance().getService(ServiceTypes.INSTRUCTOR);

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
            cmbInstrucre.getSelectionModel().select(mainInstructorDTO);
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
        InstructorDTO selectedInstructor = cmbInstrucre.getSelectionModel().getSelectedItem();

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
        Double free = (txtFree.getText() == null || txtFree.getText().isEmpty()) ? null : Double.parseDouble(txtFree.getText());
        InstructorDTO selectedInstructor = cmbInstrucre.getSelectionModel().getSelectedItem();

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
                    DialogUtil.close();
                    AlertUtils.showSuccess("Success", "Course added successfully.");
                    clearFields();
                } else {
                    AlertUtils.showError("Error", "Failed to add course.");
                }
            } catch (Exception e) {
                e.printStackTrace();
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
        InstructorDTO selectedInstructor = cmbInstrucre.getSelectionModel().getSelectedItem();

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
                    DialogUtil.close();
                    AlertUtils.showSuccess("Success", "Course update successfully.");
                    clearFields();
                } else {
                    AlertUtils.showError("Error", "Failed to update course.");
                }
            } catch (Exception e) {
                e.printStackTrace();
                AlertUtils.showError("Error", "Failed to update course : " + e.getMessage());
                throw new RuntimeException(e);
            }
        } else {
            NotificationUtils.showError("Validation Error", "Please fill all the fields correctly.");
        }
    }

    public void initInstructorCombo(){
        try{
            List<InstructorDTO> instructorDTOS = instructorService.getAllInstructors();
            ObservableList<InstructorDTO> instructorDTOObservableList = FXCollections.observableArrayList(instructorDTOS);
            cmbInstrucre.setItems(instructorDTOObservableList);

            // Show only courseName in dropdown
            cmbInstrucre.setCellFactory(lv -> new ListCell<>() {
                @Override
                protected void updateItem(InstructorDTO item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty || item == null ? null : item.getFirstName());
                }
            });

            // Show only courseName when selected
            cmbInstrucre.setButtonCell(new ListCell<InstructorDTO>() {
                @Override
                protected void updateItem(InstructorDTO item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty || item == null ? null : item.getFirstName());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }
}
