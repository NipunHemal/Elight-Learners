package lk.ijse.elight_driving_school.controller.pages;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import lk.ijse.elight_driving_school.controller.component.form.CourseFormController;
import lk.ijse.elight_driving_school.controller.component.form.InstructorFormController;
import lk.ijse.elight_driving_school.dto.CourseDTO;
import lk.ijse.elight_driving_school.dto.InstructorDTO;
import lk.ijse.elight_driving_school.dto.tm.InstructorTM;
import lk.ijse.elight_driving_school.enums.ServiceTypes;
import lk.ijse.elight_driving_school.mapper.CourseMapper;
import lk.ijse.elight_driving_school.mapper.InstructorMapper;
import lk.ijse.elight_driving_school.service.ServiceFactory;
import lk.ijse.elight_driving_school.service.custom.CourseService;
import lk.ijse.elight_driving_school.service.custom.InstructorService;
import lk.ijse.elight_driving_school.util.AlertUtils;
import lk.ijse.elight_driving_school.util.DialogUtil;
import lk.ijse.elight_driving_school.util.NotificationUtils;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class InstructorController implements Initializable {

    public TableView tableInstructor;
    @FXML
    private TableColumn<InstructorTM, String> colAvailability;
    @FXML
    private TableColumn<InstructorTM, String> colEmail;
    @FXML
    private TableColumn<InstructorTM, String> colFirstname;
    @FXML
    private TableColumn<InstructorTM, String> colId;
    @FXML
    private TableColumn<InstructorTM, String> colLastname;
    @FXML
    private TableColumn<InstructorTM, String> colSpecialization;
    @FXML
    private TableColumn<InstructorTM, String> cplContact;
    @FXML
    private TableColumn<InstructorTM, String> colAction;

    @FXML
    private TextField tstSearch;
    @FXML
    private Label txtCourseCount;

    InstructorService instructorService = ServiceFactory.getInstance().getService(ServiceTypes.INSTRUCTOR);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{
            initializeTable();
            loadInstructors();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void initializeTable(){
        colId.setCellValueFactory(new PropertyValueFactory<>("instructorId"));
        colFirstname.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        colLastname.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        cplContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colSpecialization.setCellValueFactory(new PropertyValueFactory<>("specialization"));
        colAvailability.setCellValueFactory(new PropertyValueFactory<>("availability"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("action"));
    }

    @FXML
    void btnAddOnAction(ActionEvent event) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/components/form/InstructorForm.fxml"));
            Parent customContent = loader.load();
            loader.<InstructorFormController>getController().init(this,null);
            DialogUtil.showCustom(null, null, customContent,
                    "Save", "Cancel",
                    () -> loader.<InstructorFormController>getController().handelSubmit(),
                    null);
        } catch (Exception e) {
            e.printStackTrace();
            NotificationUtils.showError("Error Loading Form", e.getMessage());
        }
    }

    public void loadInstructors(){
        try {
            tableInstructor.getItems().clear();
            List<InstructorDTO> courses = instructorService.getAllInstructors();
            tableInstructor.getItems().addAll(List.copyOf(courses.stream().map(i->{
                InstructorTM tm = InstructorMapper.toTM(i);
                tm.setAction(getAction(i));
                return tm;
            }).toList()));
            txtCourseCount.setText(String.valueOf(courses.size()));
        } catch (Exception e) {
            e.printStackTrace();
            NotificationUtils.showError("Error Loading Courses", e.getMessage());
        }
    }

    void handelDelete(String id){
        try{
            if (AlertUtils.showConform("Delete Instructor", "Are you sure you want to delete this instructor?")) {
                instructorService.deleteInstructors(id);
                AlertUtils.showSuccess("Success", "Instructor Deleted Successfully");
                loadInstructors();
            }
        } catch (Exception e) {
            e.printStackTrace();
            NotificationUtils.showError("Error", e.getMessage());
        }
    }

    Pane getAction(InstructorDTO dto){
        try {
            Pane action = new Pane();
            Button btnEdit = new Button("✏");
            btnEdit.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;");
            btnEdit.setPrefWidth(30);
            btnEdit.setLayoutX(40);
            btnEdit.setCursor(javafx.scene.Cursor.HAND);
            btnEdit.setOnAction(event -> {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/components/form/InstructorForm.fxml"));
                    Parent customContent = loader.load();

                    InstructorFormController controller = loader.getController();
                    controller.init(this, dto); // pass dto instead of null

                    DialogUtil.showCustom(
                            null, null, customContent,
                            "Update", "Cancel",
                            () -> controller.handelSubmit(),
                            null
                    );
                } catch (IOException e) {
                    e.printStackTrace();
                    NotificationUtils.showError("Error loading course form", e.getMessage());
                }
            });

            Button btnDelete = new Button("🗑");
            btnDelete.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-weight: bold;");
            btnDelete.setPrefWidth(30);
            btnDelete.setLayoutX(0);
            btnDelete.setCursor(javafx.scene.Cursor.HAND);
            btnDelete.setOnAction(event -> handelDelete(dto.getInstructorId()));

            action.getChildren().addAll(btnEdit, btnDelete);
            return action;
        } catch (Exception e) {
            e.printStackTrace();
            NotificationUtils.showError("Error load action : ", e.getMessage());
            return null;
        }
    }

}
