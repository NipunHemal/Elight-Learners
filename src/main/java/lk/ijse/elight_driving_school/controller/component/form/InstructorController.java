package lk.ijse.elight_driving_school.controller.component.form;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class InstructorController {

    @FXML
    private Button cancelButton;

    @FXML
    private Button clearButton;

    @FXML
    private ComboBox<?> cmbAvailability;

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

}
