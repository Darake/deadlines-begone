package deadlinesbegone.ui;

import deadlinesbegone.domain.AbstractNamedObject;
import deadlinesbegone.domain.Assignment;
import deadlinesbegone.domain.Course;
import deadlinesbegone.domain.DeadlinesBegoneService;
import deadlinesbegone.domain.TreeViewObject;
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
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

public class MainController implements Initializable {
    public DeadlinesBegoneService appService;
    private TreeItem<AbstractNamedObject> root;
    private MainController mainController = this;
    public Image checkmark = new Image(getClass().getResourceAsStream("/checkmark.png"));

    @FXML
    public TreeView treeView;
    
    @FXML
    public AnchorPane content;
    
    public void setAppService(DeadlinesBegoneService appService) {
        this.appService = appService;
    }
    
    public void changeViewToNewAssignment(Course course) throws IOException {
        NewAssignmentController controller = new NewAssignmentController();
        controller.setAppService(appService);
        controller.setMainController(this);
        controller.setCourse(course);
        FXMLLoader loader = new FXMLLoader(DeadlinesBegoneApp.class.getResource("/fxml/NewAssignment.fxml"));
        loader.setController(controller);
        AnchorPane pane = loader.load();
        content.getChildren().clear();
        content.getChildren().add(pane);    
    }
    
    public void changeViewToUndone() throws IOException {
        UndoneController controller = new UndoneController();
        controller.setMainController(this);
        FXMLLoader loader = new FXMLLoader(DeadlinesBegoneApp.class.getResource("/fxml/Undone.fxml"));
        loader.setController(controller);
        VBox box = loader.load();
        
        content.getChildren().clear();
        content.getChildren().add(box);
        box.prefWidthProperty().bind(content.widthProperty());
        box.prefHeightProperty().bind(content.heightProperty());
    }

    @FXML
    public void handleAddCourse(ActionEvent event) throws Exception {
        Optional<String> result = showTextInputDialog("Add course", 
                "Enter course name.");
        
        if (result.isPresent()) {
            Course course = appService.newCourse(result.get());
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
        TreeItem<AbstractNamedObject> treeCourse = new TreeItem<AbstractNamedObject>(course);
        root.getChildren().add(treeCourse);
        treeView.refresh();
    }
    
    @FXML
    public void addAssignmentToTree(Assignment assignment) {
        TreeItem<AbstractNamedObject> treeAssignment = new TreeItem<AbstractNamedObject>(assignment);
        if (assignment.getCompleted()) {
            treeAssignment.setGraphic(new ImageView(checkmark));
        }
        TreeViewObject deadline = new TreeViewObject(assignment.getId(), assignment.getDeadline());
        TreeItem<AbstractNamedObject> treeDeadline = new TreeItem<AbstractNamedObject>(deadline);
        treeAssignment.getChildren().add(treeDeadline);
        for (TreeItem<AbstractNamedObject> courseNode : root.getChildren()) {
            if (courseNode.getValue().toString().contentEquals(assignment.getCourse().getName())) {
                courseNode.getChildren().add(treeAssignment);
                break;
            }
        }
    }
    
    private void loadCourses() throws SQLException {
        List<Course> courses = appService.getCourses();
        for (Course course : courses) {
            TreeItem<AbstractNamedObject> courseNode = new TreeItem<AbstractNamedObject>(course);
            root.getChildren().add(courseNode);
        }
    }
    
    private void loadAssignments() throws SQLException {
        List<Assignment> assignments = appService.getAssignments();
        for (Assignment assignment : assignments) {
            addAssignmentToTree(assignment);
        }
    }
    
    public void clearContent() {
        content.getChildren().clear();
    }
    
    public void error(Exception e) {
        Alert error = new Alert(Alert.AlertType.ERROR);
        error.setHeaderText("Something went wrong");
        error.setContentText(e.toString());
        error.showAndWait();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        root = new TreeItem<AbstractNamedObject>(new TreeViewObject(null, "Root"));
        treeView.setRoot(root);
        treeView.setShowRoot(false);
        treeView.setStyle("-fx-focus-color: transparent;");
        
        try {
            loadCourses();
            loadAssignments();
        } catch (SQLException ex) {
            System.out.println(ex);
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        treeView.setCellFactory(new Callback<TreeView<String>, TreeCell<AbstractNamedObject>>() {
            @Override
            public TreeCell<AbstractNamedObject> call(TreeView<String> p) {
                return new TreeCellWithContextMenu(mainController);
              
            }
        });
        
        try {
            changeViewToUndone();
        } catch (IOException ex) {
            System.out.println(ex);
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
}
