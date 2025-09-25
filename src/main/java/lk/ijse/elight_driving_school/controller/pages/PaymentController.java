package lk.ijse.elight_driving_school.controller.pages;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import lk.ijse.elight_driving_school.controller.component.form.CourseFormController;
import lk.ijse.elight_driving_school.controller.component.form.PaymentFormController;
import lk.ijse.elight_driving_school.dto.PaymentsDTO;
import lk.ijse.elight_driving_school.dto.tm.PaymentsTM;
import lk.ijse.elight_driving_school.enums.ServiceTypes;
import lk.ijse.elight_driving_school.mapper.InstructorMapper;
import lk.ijse.elight_driving_school.mapper.PaymentMapper;
import lk.ijse.elight_driving_school.service.ServiceFactory;
import lk.ijse.elight_driving_school.service.custom.PaymentsService;
import lk.ijse.elight_driving_school.util.DialogUtil;
import lk.ijse.elight_driving_school.util.NotificationUtils;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class PaymentController implements Initializable {

    public TableView<PaymentsTM> tablePayment;
    @FXML
    private TableColumn<PaymentsTM , Pane> colAction;

    @FXML
    private TableColumn<PaymentsTM , Double> colAmount;

    @FXML
    private TableColumn<PaymentsTM , String> colId;

    @FXML
    private TableColumn<PaymentsTM , Date> colPayDate;

    @FXML
    private TableColumn<PaymentsTM , String> colPayMethod;

    @FXML
    private TableColumn<PaymentsTM , String> colStatus;

    @FXML
    private TableColumn<PaymentsTM , String> colStudentId;

    @FXML
    private TextField tstSearch;

    @FXML
    private Label txtCourseCount;

    PaymentsService paymentsService = ServiceFactory.getInstance().getService(ServiceTypes.PAYMENTS);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{
            initializeTable();
            loadPayments();
        } catch (Exception e) {
            NotificationUtils.showError("Error", e.getMessage());
        }
    }

    private void initializeTable() {
        colId.setCellValueFactory(new PropertyValueFactory<>("paymentId"));
        colPayDate.setCellValueFactory(new PropertyValueFactory<>("paymentDate"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colPayMethod.setCellValueFactory(new PropertyValueFactory<>("paymentMethod"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colStudentId.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("action"));
    }

    private void loadPayments() {
        try {
            tablePayment.getItems().clear();
            java.util.List<PaymentsDTO> payments = paymentsService.getAllPayments();
            tablePayment.getItems().addAll(payments.stream().map(PaymentMapper::toTM).toList());
//            tablePayment.getItems().addAll(List.copyOf(payments.stream().map(PaymentMapper::toTM).toList()));
            txtCourseCount.setText(String.valueOf(payments.size()));

        } catch (Exception e) {
            NotificationUtils.showError("Error Loading Payments", e.getMessage());
        }
    }

    @FXML
    void btnAddOnAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/components/form/PaymentForm.fxml"));
            Parent customContent = loader.load();
            loader.<PaymentFormController>getController().init(this,null);
            DialogUtil.showCustom(null, null, customContent,
                    "Save", "Cancel",
                    () -> loader.<PaymentFormController>getController().saveProject(),
                    null);
        } catch (Exception e) {
            e.printStackTrace();
            NotificationUtils.showError("Error Loading Form", e.getMessage());
        }
    }

}
