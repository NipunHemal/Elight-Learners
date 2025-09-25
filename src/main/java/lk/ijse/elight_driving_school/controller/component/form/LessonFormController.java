package lk.ijse.elight_driving_school.controller.component.form;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class LessonFormController {

    @FXML
    private Button cancelButton;

    @FXML
    private Button clearButton;

    @FXML
    private ComboBox<?> cmbCourse;

    @FXML
    private ComboBox<?> cmbInstrucre;

    @FXML
    private ComboBox<?> cmbStudent;

    @FXML
    private Label lblFormSubTitle;

    @FXML
    private Label lblFormTitle;

    @FXML
    private DatePicker lessonDatePicker;

    @FXML
    private Button saveButton;

    @FXML
    private TextField txtEndTime;

    @FXML
    private TextField txtStartTime;

    @FXML
    private TextField txtStatus;

}