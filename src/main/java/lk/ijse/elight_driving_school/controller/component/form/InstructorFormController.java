package lk.ijse.elight_driving_school.controller.component.form;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import lk.ijse.elight_driving_school.controller.pages.InstructorController;
import lk.ijse.elight_driving_school.dto.InstructorDTO;
import lk.ijse.elight_driving_school.enums.ServiceTypes;
import lk.ijse.elight_driving_school.service.ServiceFactory;
import lk.ijse.elight_driving_school.service.custom.InstructorService;
import lk.ijse.elight_driving_school.util.AlertUtils;
import lk.ijse.elight_driving_school.util.NotificationUtils;
import lk.ijse.elight_driving_school.util.ValidationUtils;

import java.net.URL;
import java.util.ResourceBundle;

public class InstructorFormController implements Initializable {

    @FXML
    private Button cancelButton;

    @FXML
    private Button clearButton;

    @FXML
    private ComboBox<String> cmbAvailability;

    @FXML
    private Label emailError;

    @FXML
    private Label lblFormSubTitle;

    @FXML
    private Label lblFormTitle;

    @FXML
    private Button saveButton;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtFName;

    @FXML
    private TextField txtLName;
    @FXML
    private TextField txtSpecialization;

    InstructorController instructorController;
    InstructorDTO instructorDTO;
    InstructorService instructorService = ServiceFactory.getInstance().getService(ServiceTypes.INSTRUCTOR);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            initInstructorCombo();
        } catch (Exception e) {
            e.printStackTrace();
            NotificationUtils.showError("Error", "Error Loading Instructors");
        }
    }

    public void init(InstructorController instructorController,InstructorDTO instructorDTO) {
        this.instructorController = instructorController;
        this.instructorDTO = instructorDTO;
        handelUpdateCreate();
    }

    private void handelUpdateCreate(){
        if (instructorDTO != null) {
            lblFormTitle.setText("Update Instructor");
            txtFName.setText(instructorDTO.getFirstName());
            txtLName.setText(instructorDTO.getLastName());
            txtEmail.setText(instructorDTO.getEmail());
            txtContact.setText(instructorDTO.getContact());
            txtSpecialization.setText(instructorDTO.getSpecialization());
            cmbAvailability.getSelectionModel().select(instructorDTO.getAvailability());
        }  else {
            lblFormTitle.setText("Add New Instructor");
            clearFields();
        }
    }


    public void clearFields() {
        txtFName.clear();
        txtLName.clear();
        txtEmail.clear();
        txtContact.clear();
        txtSpecialization.clear();
        cmbAvailability.getSelectionModel().clearSelection();
    }

    public boolean validateForm() {
        boolean isValidFName = ValidationUtils.validateInput(txtFName, "empty");
        boolean isValidLName = ValidationUtils.validateInput(txtLName, "empty");
        boolean isValidEmail = ValidationUtils.validateInput(txtEmail, "email");
        boolean isValidContact = ValidationUtils.validateInput(txtContact, "empty") && ValidationUtils.validateInput(txtContact, "numeric");
        boolean isValidSpecialization = ValidationUtils.validateInput(txtSpecialization, "empty");
        Object selectedAvailability = cmbAvailability.getSelectionModel().getSelectedItem();
        if (!(isValidFName && isValidLName && isValidEmail && isValidContact && isValidSpecialization && selectedAvailability != null)) {
            return false;
        }
        return true;
    }

    public void handelSubmit(){
        if (instructorDTO != null) {
            updateProject();
        } else {
            saveProject();
        }
    }

    public void saveProject() {
        String fName = txtFName.getText();
        String lName = txtLName.getText();
        String email = txtEmail.getText();
        String contact = txtContact.getText();
        String specialization = txtSpecialization.getText();
        String availability = cmbAvailability.getSelectionModel().getSelectedItem() != null ? cmbAvailability.getSelectionModel().getSelectedItem().toString() : "";
        if (validateForm()) {
            InstructorDTO instructorDTO = new InstructorDTO().builder()
                    .firstName(fName)
                    .lastName(lName)
                    .email(email)
                    .contact(contact)
                    .specialization(specialization)
                    .availability(availability)
                    .build();
            try {
                boolean isSave = instructorService.saveInstructors(instructorDTO);
                if (isSave) {
                    instructorController.initialize(null, null);
                    AlertUtils.showSuccess("Success", "Instructor added successfully.");
                    clearFields();
                    instructorController.initialize(null, null);
                } else {
                    AlertUtils.showError("Error", "Failed to add instructor.");
                }
            } catch (Exception e) {
                AlertUtils.showError("Error", "Failed to add instructor : " + e.getMessage());
                throw new RuntimeException(e);
            }
        } else {
            NotificationUtils.showError("Validation Error", "Please fill all the fields correctly.");
        }
    }

    public void updateProject() {
        String fName = txtFName.getText();
        String lName = txtLName.getText();
        String email = txtEmail.getText();
        String contact = txtContact.getText();
        String specialization = txtSpecialization.getText();
        String availability = cmbAvailability.getSelectionModel().getSelectedItem() != null ? cmbAvailability.getSelectionModel().getSelectedItem().toString() : "";
        if (validateForm()) {
            InstructorDTO instructorDTO = new InstructorDTO().builder()
                    .firstName(fName)
                    .lastName(lName)
                    .email(email)
                    .contact(contact)
                    .specialization(specialization)
                    .availability(availability)
                    .build();
            try {
                boolean isUpdate = instructorService.updateInstructors(instructorDTO);
                if (isUpdate) {
                    instructorController.initialize(null, null);
                    AlertUtils.showSuccess("Success", "Instructor update successfully.");
                    clearFields();
                    instructorController.initialize(null, null);
                } else {
                    AlertUtils.showError("Error", "Failed to update instructor.");
                }
            } catch (Exception e) {
                AlertUtils.showError("Error", "Failed to update instructor : " + e.getMessage());
                throw new RuntimeException(e);
            }
        } else {
            NotificationUtils.showError("Validation Error", "Please fill all the fields correctly.");
        }
    }


    public void initInstructorCombo() {
        try {
            // Example: populate with static availability options
            ObservableList<String> availabilityOptions = FXCollections.observableArrayList("Available", "Unavailable");
            cmbAvailability.setItems(availabilityOptions);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
