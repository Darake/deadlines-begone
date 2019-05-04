
package deadlinesbegone.ui;

import deadlinesbegone.domain.Assignment;
import deadlinesbegone.domain.Course;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class NewAssignmentController extends SubController {
    
    private Course course;
    
    @FXML
    private Label courseLabel;

    @FXML
    private TextField name;

    @FXML
    private DatePicker deadline;
    
    public void setCourse(Course course) {
        this.course = course;
    }
    
    public void handleAddAssignment(ActionEvent event) throws Exception {
        Assignment assignment = new Assignment(null, name.getText(), deadline.getValue().toString(), course, false);
        assignment = appService.newAssignment(assignment);
        mainController.treeViewController.addAssignmentToTree(assignment);
        mainController.showUndone();
    }
    
    public void handleCancel(ActionEvent event) throws Exception {
        mainController.showUndone();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        courseLabel.setText("Add an assignment for " + course.getName());
    }

}
