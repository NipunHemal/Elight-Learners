package lk.ijse.elight_driving_school.controller.pages;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.elight_driving_school.dto.tm.StudentTM;
import lk.ijse.elight_driving_school.util.NotificationUtils;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class StudentController implements Initializable {

    public TableView<StudentTM> tableStudent;
    @FXML
    private TableColumn<StudentTM , String> colAction;

    @FXML
    private TableColumn<StudentTM , String> colAddress;

    @FXML
    private TableColumn<StudentTM , Date> colDob;

    @FXML
    private TableColumn<StudentTM , String> colEmail;

    @FXML
    private TableColumn<StudentTM , String> colFirstname;

    @FXML
    private TableColumn<StudentTM , String> colId;

    @FXML
    private TableColumn<StudentTM , String> colLastname;

    @FXML
    private TableColumn<StudentTM , String> colPhone;

    @FXML
    private TableColumn<StudentTM , Date> colRegDate;

    @FXML
    private TableColumn<StudentTM , String> colCourseId;



    @FXML
    private TextField tstSearch;

    @FXML
    private Label txtCourseCount;

    @FXML
    void btnAddOnAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{
            initializeTable();
            loadStudent();
        } catch (Exception e) {
            NotificationUtils.showError("Error", e.getMessage());
        }
    }

    private void loadStudent() {
        colId.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        colFirstname.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        colLastname.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colDob.setCellValueFactory(new PropertyValueFactory<>("dob"));
        colRegDate.setCellValueFactory(new PropertyValueFactory<>("registrationDate"));
        colCourseId.setCellValueFactory(new PropertyValueFactory<>("courseId"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("action"));
    }

    private void initializeTable() {
    }


}
