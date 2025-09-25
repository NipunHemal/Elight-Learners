package lk.ijse.elight_driving_school.controller.pages;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import lk.ijse.elight_driving_school.dto.tm.PaymentsTM;
import lk.ijse.elight_driving_school.util.NotificationUtils;

import java.net.URL;
import java.util.Date;
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

    }

    @FXML
    void btnAddOnAction(ActionEvent event) {

    }

}
