package lk.ijse.elight_driving_school.controller.component.form;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.fxml.Initializable;
import lk.ijse.elight_driving_school.controller.pages.StudentController;
import lk.ijse.elight_driving_school.dto.StudentDTO;
import lk.ijse.elight_driving_school.enums.ServiceTypes;
import lk.ijse.elight_driving_school.service.ServiceFactory;
import lk.ijse.elight_driving_school.service.custom.StudentService;
import lk.ijse.elight_driving_school.util.AlertUtils;
import lk.ijse.elight_driving_school.util.NotificationUtils;
import lk.ijse.elight_driving_school.util.ValidationUtils;

import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;

public class StudentFormController implements Initializable {

    @FXML
    private Button cancelButton;

    @FXML
    private Button clearButton;

    @FXML
    private DatePicker dobPicker;

    @FXML
    private Label emailError;

    @FXML
    private Label lblFormSubTitle;

    @FXML
    private Label lblFormTitle;

    @FXML
    private DatePicker registstaionDatePicker;

    @FXML
    private Button saveButton;

    @FXML
    private Button saveButton1;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtFirstName;

    @FXML
    private TextField txtLastName;

    StudentController studentController;
    StudentDTO mainStudentDTO;

    StudentService studentService = ServiceFactory.getInstance().getService(ServiceTypes.STUDENT);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // If you need to initialize combo boxes, do it here
    }

    public void init(StudentController studentController,StudentDTO studentDTO){
        this.studentController = studentController;
        this.mainStudentDTO = studentDTO;
        handelUpdateCreate();
    }

    private void handelUpdateCreate(){
        if (mainStudentDTO != null) {
            lblFormTitle.setText("Update Course");
            txtContact.setText(mainStudentDTO.getPhone());
            txtFirstName.setText(mainStudentDTO.getFirstName());
            txtLastName.setText(mainStudentDTO.getLastName());
            txtEmail.setText(mainStudentDTO.getEmail());
            txtAddress.setText(mainStudentDTO.getAddress());
            dobPicker.setValue(LocalDate.parse(mainStudentDTO.getDob().toString()));
            registstaionDatePicker.setValue(LocalDate.parse(mainStudentDTO.getRegistrationDate().toString()));
        }  else {
            lblFormTitle.setText("Add New Course");
            clearFields();
        }
    }

    public void clearFields() {
        txtFirstName.clear();
        txtLastName.clear();
        txtEmail.clear();
        txtContact.clear();
        txtAddress.clear();
        dobPicker.setValue(null);
        registstaionDatePicker.setValue(null);
    }

    public boolean validateForm() {
        boolean isValidFirstName = ValidationUtils.validateInput(txtFirstName, "empty");
        boolean isValidLastName = ValidationUtils.validateInput(txtLastName, "empty");
        boolean isValidEmail = ValidationUtils.validateInput(txtEmail, "email");
        boolean isValidContact = ValidationUtils.validateInput(txtContact, "empty") && ValidationUtils.validateInput(txtContact, "numeric");
        boolean isValidAddress = ValidationUtils.validateInput(txtAddress, "empty");
        boolean isValidDob = dobPicker.getValue() != null;
        boolean isValidRegDate = registstaionDatePicker.getValue() != null;
        if (!(isValidFirstName && isValidLastName && isValidEmail && isValidContact && isValidAddress && isValidDob && isValidRegDate)) {
            return false;
        }
        return true;
    }

    public void handelSubmit(){
        if (mainStudentDTO == null) {
            saveProject();
        } else {
            updateProject();
        }
    }

    public void saveProject() {
        String firstName = txtFirstName.getText();
        String lastName = txtLastName.getText();
        String email = txtEmail.getText();
        String contact = txtContact.getText();
        String address = txtAddress.getText();
        Date dob = java.sql.Date.valueOf(dobPicker.getValue());
        Date regDate = java.sql.Date.valueOf(registstaionDatePicker.getValue());
        if (validateForm()) {
            StudentDTO studentDTO = StudentDTO.builder()
                    .firstName(firstName)
                    .lastName(lastName)
                    .email(email)
                    .phone(contact)
                    .address(address)
                    .dob(dob)
                    .registrationDate(regDate)
                    .build();
            try {
                boolean isSave = studentService.saveStudents(studentDTO);
                if (isSave) {
                    AlertUtils.showSuccess("Success", "Student added successfully.");
                    clearFields();
                } else {
                    AlertUtils.showError("Error", "Failed to add student.");
                }
            } catch (Exception e) {
                AlertUtils.showError("Error", "Failed to add student : " + e.getMessage());
                throw new RuntimeException(e);
            }
        } else {
            NotificationUtils.showError("Validation Error", "Please fill all the fields correctly.");
        }
    }

    public void updateProject() {
        String firstName = txtFirstName.getText();
        String lastName = txtLastName.getText();
        String email = txtEmail.getText();
        String contact = txtContact.getText();
        String address = txtAddress.getText();
        Date dob = java.sql.Date.valueOf(dobPicker.getValue());
        Date regDate = java.sql.Date.valueOf(registstaionDatePicker.getValue());
        if (validateForm()) {
            StudentDTO studentDTO = StudentDTO.builder()
                    .studentId(mainStudentDTO.getStudentId())
                    .firstName(firstName)
                    .lastName(lastName)
                    .email(email)
                    .phone(contact)
                    .address(address)
                    .dob(dob)
                    .registrationDate(regDate)
                    .build();
            try {
                boolean isSave = studentService.saveStudents(studentDTO);
                if (isSave) {
                    AlertUtils.showSuccess("Success", "Student added successfully.");
                    clearFields();
                } else {
                    AlertUtils.showError("Error", "Failed to add student.");
                }
            } catch (Exception e) {
                AlertUtils.showError("Error", "Failed to add student : " + e.getMessage());
                throw new RuntimeException(e);
            }
        } else {
            NotificationUtils.showError("Validation Error", "Please fill all the fields correctly.");
        }
    }
}