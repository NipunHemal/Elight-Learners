package lk.ijse.elight_driving_school.controller.component.form;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class PaymentFormController {

    @FXML
    private Button cancelButton;

    @FXML
    private Button clearButton;

    @FXML
    private ComboBox<?> cmbPaymentMethod;

    @FXML
    private ComboBox<?> cmbStudentId;

    @FXML
    private Label emailError;

    @FXML
    private Label lblFormSubTitle;

    @FXML
    private Label lblFormTitle;

    @FXML
    private DatePicker paymentDatePicker;

    @FXML
    private Button saveButton;

    @FXML
    private TextField txtAmount;

    @FXML
    private TextField txtStatus;

}