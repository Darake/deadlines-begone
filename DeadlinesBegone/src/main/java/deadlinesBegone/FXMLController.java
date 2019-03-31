package deadlinesBegone;

import deadlinesBegone.domain.Course;
import deadlinesBegone.domain.DeadlinesBegoneService;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;

public class FXMLController implements Initializable {
    private DeadlinesBegoneService appService;
    private DeadlinesBegoneApp application;

    @FXML
    private Accordion courses;
    
    public void setAppService(DeadlinesBegoneService appService) {
        this.appService = appService;
    }
    
    public void setApplication(DeadlinesBegoneApp application) {
        this.application = application;
    }

    @FXML
    public void handleAddCourse(ActionEvent event) throws Exception {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add course");
        dialog.setHeaderText("Enter course name.");
        dialog.setContentText("Name:");
        
        Optional<String> result = dialog.showAndWait();
        
        if (result.isPresent()) {
            this.appService.newCourse(new Course(result.get()));
            addCourse(result.get());
        }
    }
    
    @FXML
    private void addCourse(String name) {
        TitledPane pane = new TitledPane(name, new AnchorPane());
        pane.setAnimated(false);
        courses.getPanes().add(pane);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        List<Course> courses;
        try {
            courses = this.appService.getCourses();
            for (Course course : courses) {
                addCourse(course.getName());
            }
        } catch (Exception ex) {
            System.out.println("Exception " + ex);
        }
    }    
}
