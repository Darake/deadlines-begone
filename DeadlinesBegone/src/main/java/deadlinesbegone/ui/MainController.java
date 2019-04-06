package deadlinesbegone.ui;

import deadlinesbegone.domain.Assignment;
import deadlinesbegone.domain.Course;
import deadlinesbegone.domain.DeadlinesBegoneService;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.TitledPane;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

public class MainController implements Initializable {
    private DeadlinesBegoneService appService;
    private TreeItem<String> root;
    private MainController mainController = this;

    @FXML
    private TreeView treeView;
    
    @FXML
    private AnchorPane content;
    
    public void setAppService(DeadlinesBegoneService appService) {
        this.appService = appService;
    }

    @FXML
    public void handleAddCourse(ActionEvent event) throws Exception {
        Optional<String> result = showTextInputDialog("Add course", 
                "Enter course name.");
        
        if (result.isPresent()) {
            Course course = appService.newCourse(new Course(null, result.get()));
            addCourseToTree(course);
        }
    }
    
    @FXML
    public Optional<String> showTextInputDialog(String title, String header) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle(title);
        dialog.setHeaderText(header);
        dialog.setContentText("Name:");
        return dialog.showAndWait();
    }
    
    @FXML
    private void addCourseToTree(Course course) {
        TreeItem<String> treeCourse = new TreeItem<String>(course.getName());
        root.getChildren().add(treeCourse);
    }
    
    @FXML
    public void addAssignmentToTree(Assignment assignment) {
        TreeItem<String> treeAssignment = new TreeItem<String>(assignment.getName());
        for (TreeItem<String> courseNode : root.getChildren()) {
            if (courseNode.getValue().contentEquals(assignment.getCourse().getName())) {
                courseNode.getChildren().add(treeAssignment);
                break;
            }
        }
    }
    
    private void loadCourses() throws SQLException {
        List<Course> courses = appService.getCourses();
        for (Course course : courses) {
            TreeItem<String> courseNode = new TreeItem<String>(course.getName());
            root.getChildren().add(courseNode);
        }
    }
    
    private void loadAssignments(TreeItem<String> allAssigments) throws SQLException {
        List<Assignment> assignments = appService.getAssignments();
        for (Assignment assignment : assignments) {
            addAssignmentToTree(assignment);
        }
    }
    
    public void clearContent() {
        content.getChildren().clear();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        root = new TreeItem<String>("Root");
        TreeItem<String> allAssigments = new TreeItem<String>("All assigments");
        treeView.setRoot(root);
        treeView.setShowRoot(false);
        
        try {
            loadCourses();
            loadAssignments(allAssigments);
        } catch (SQLException ex) {
            System.out.println(ex);
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        treeView.setCellFactory(new Callback<TreeView<String>, TreeCell<String>>() {
            @Override
            public TreeCell<String> call(TreeView<String> p) {
                return new TreeCellWithContextMenu(appService, content, mainController);
              
            }
        });
    }    
}
