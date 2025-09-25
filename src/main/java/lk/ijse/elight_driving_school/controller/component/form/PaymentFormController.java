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
import lk.ijse.elight_driving_school.controller.pages.PaymentController;
import lk.ijse.elight_driving_school.dto.PaymentsDTO;
import lk.ijse.elight_driving_school.dto.StudentDTO;
import lk.ijse.elight_driving_school.enums.ServiceTypes;
import lk.ijse.elight_driving_school.service.ServiceFactory;
import lk.ijse.elight_driving_school.service.custom.PaymentsService;
import lk.ijse.elight_driving_school.service.custom.StudentService;
import lk.ijse.elight_driving_school.util.AlertUtils;
import lk.ijse.elight_driving_school.util.NotificationUtils;
import lk.ijse.elight_driving_school.util.ValidationUtils;

import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class PaymentFormController implements Initializable {

    @FXML
    private Button cancelButton;

    @FXML
    private Button clearButton;

    @FXML
    private ComboBox<String> cmbPaymentMethod;

    @FXML
    private ComboBox<StudentDTO> cmbStudentId;

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

    PaymentController mainPaymentController;
    PaymentsDTO paymentsDTO;

    PaymentsService paymentsService = ServiceFactory.getInstance().getService(ServiceTypes.PAYMENTS);
    StudentService studentService = ServiceFactory.getInstance().getService(ServiceTypes.STUDENT);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            initComboBoxes();
        } catch (Exception e) {
            e.printStackTrace();
            NotificationUtils.showError("Error", "Error Loading Payment Form Data");
        }
    }

    public void init(PaymentController paymentController , PaymentsDTO paymentsDTO) {
        this.mainPaymentController = paymentController;
        this.paymentsDTO = paymentsDTO;
        handelUpdateCreate();
    }

    private void handelUpdateCreate(){
        if (mainPaymentController != null) {
            lblFormTitle.setText("Update Payment");
            txtAmount.setText(String.valueOf(paymentsDTO.getAmount()));
            txtStatus.setText(paymentsDTO.getStatus());
            paymentDatePicker.setValue(LocalDate.parse(paymentsDTO.getPaymentDate().toString()));
            cmbPaymentMethod.getSelectionModel().select(paymentsDTO.getPaymentMethod());
            cmbStudentId.getSelectionModel().select(Integer.parseInt(paymentsDTO.getStudentId()));
        }  else {
            lblFormTitle.setText("Add New Payment");
            clearFields();
        }
    }

    public void clearFields() {
        txtAmount.clear();
        txtStatus.clear();
        paymentDatePicker.setValue(null);
        cmbPaymentMethod.getSelectionModel().clearSelection();
        cmbStudentId.getSelectionModel().clearSelection();
    }

    public boolean validateForm() {
        boolean isValidAmount = ValidationUtils.validateInput(txtAmount, "empty") && ValidationUtils.validateInput(txtAmount, "numeric");
        boolean isValidStatus = ValidationUtils.validateInput(txtStatus, "empty");
        boolean isValidDate = paymentDatePicker.getValue() != null;
        Object selectedPaymentMethod = cmbPaymentMethod.getSelectionModel().getSelectedItem();
        Object selectedStudent = cmbStudentId.getSelectionModel().getSelectedItem();
        if (!(isValidAmount && isValidStatus && isValidDate && selectedPaymentMethod != null && selectedStudent != null)) {
            return false;
        }
        return true;
    }

    public void handeSubmit(){
        if (mainPaymentController != null) {
            updateProject();
        } else {
            saveProject();
        }
    }

    public void saveProject() {
        String paymentMethod = cmbPaymentMethod.getSelectionModel().getSelectedItem().toString();
        StudentDTO selectedStudent = (StudentDTO) cmbStudentId.getSelectionModel().getSelectedItem();
        Date paymentDate = java.sql.Date.valueOf(paymentDatePicker.getValue());
        double amount = Double.parseDouble(txtAmount.getText());
        String status = txtStatus.getText();
        if (validateForm()) {
            PaymentsDTO paymentsDTO = PaymentsDTO.builder()
                    .paymentDate(paymentDate)
                    .amount(amount)
                    .paymentMethod(paymentMethod)
                    .status(status)
                    .studentId(selectedStudent.getStudentId())
                    .build();
            try {
                boolean isSave = paymentsService.savePayments(paymentsDTO);
                if (isSave) {
                    AlertUtils.showSuccess("Success", "Payment added successfully.");
                    clearFields();
                } else {
                    AlertUtils.showError("Error", "Failed to add payment.");
                }
            } catch (Exception e) {
                AlertUtils.showError("Error", "Failed to add payment : " + e.getMessage());
                throw new RuntimeException(e);
            }
        } else {
            NotificationUtils.showError("Validation Error", "Please fill all the fields correctly.");
        }
    }

    public void updateProject() {
        String paymentMethod = cmbPaymentMethod.getSelectionModel().getSelectedItem().toString();
        StudentDTO selectedStudent = (StudentDTO) cmbStudentId.getSelectionModel().getSelectedItem();
        Date paymentDate = java.sql.Date.valueOf(paymentDatePicker.getValue());
        double amount = Double.parseDouble(txtAmount.getText());
        String status = txtStatus.getText();
        if (validateForm()) {
            PaymentsDTO paymentsDTO = PaymentsDTO.builder()
                    .paymentDate(paymentDate)
                    .amount(amount)
                    .paymentMethod(paymentMethod)
                    .status(status)
                    .studentId(selectedStudent.getStudentId())
                    .build();
            try {
                boolean isUpdate = paymentsService.updatePayments(paymentsDTO);
                if (isUpdate) {
                    AlertUtils.showSuccess("Success", "Payment update successfully.");
                    clearFields();
                } else {
                    AlertUtils.showError("Error", "Failed to update payment.");
                }
            } catch (Exception e) {
                AlertUtils.showError("Error", "Failed to update payment : " + e.getMessage());
                throw new RuntimeException(e);
            }
        } else {
            NotificationUtils.showError("Validation Error", "Please fill all the fields correctly.");
        }
    }

    public void initComboBoxes() {
        try {
            ObservableList<String> paymentMethods = FXCollections.observableArrayList("Cash", "Card", "Online");
            cmbPaymentMethod.setItems(paymentMethods);
            List<StudentDTO> students = studentService.getAllStudents();
            ObservableList<StudentDTO> studentList = FXCollections.observableArrayList(students);
            cmbStudentId.setItems(studentList);
            cmbStudentId.setCellFactory(lv -> new javafx.scene.control.ListCell<>() {
                @Override
                protected void updateItem(StudentDTO item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty || item == null ? null : item.getFirstName() + " " + item.getLastName());
                }
            });
            cmbStudentId.setButtonCell(new javafx.scene.control.ListCell<StudentDTO>() {
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