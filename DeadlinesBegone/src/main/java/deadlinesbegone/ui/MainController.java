
package deadlinesbegone.ui;

import deadlinesbegone.domain.Course;
import deadlinesbegone.domain.DeadlinesBegoneService;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 * Main controller for the application's FXML scenes
 * 
 */
public class MainController implements Initializable {
    
    private DeadlinesBegoneService appService;
    private DeadlinesBegoneApp app;
    
    /**
     * FXML controller for TreeView scene
     */
    public TreeViewController treeViewController;
    
    /**
     * FXML controller for Undone scene
     */
    public UndoneController undoneController;
    
    /**
     * FXML controller for NewAssignment scene
     */
    public NewAssignmentController newAssignmentController;
    
    /**
     * FXML controller for NewPeriod scene
     */
    public NewPeriodController newPeriodController;
    
    @FXML
    private AnchorPane leftPane;
    
    @FXML
    private AnchorPane rightPane;
    
    public void setAppService(DeadlinesBegoneService appService) {
        this.appService = appService;
    }
    
    public void setApp(DeadlinesBegoneApp app) {
        this.app = app;
    }
    
    private void injectController(SubController controller) {
        controller.setMainController(this);
        controller.setAppService(appService);
    }
    
    private void setupControllers() {
        treeViewController = new TreeViewController();
        injectController(treeViewController);
        
        undoneController = new UndoneController();
        injectController(undoneController);
        
        newAssignmentController = new NewAssignmentController();
        injectController(newAssignmentController);
        
        newPeriodController = new NewPeriodController();
        injectController(newPeriodController);
    }
    
    /**
     * Sets TreeView scene to SplitPanes left pane. The scene contains courses,
     * assignments and other info in a TreeView format.
     * 
     * @throws IOException 
     */
    public void showTreeView() throws IOException {
        FXMLLoader treeViewLoader = new FXMLLoader(DeadlinesBegoneApp.class.getResource("/fxml/TreeView.fxml"));
        treeViewLoader.setController(treeViewController);
        VBox box = treeViewLoader.load();
        leftPane.getChildren().clear();
        leftPane.getChildren().add(box);
        box.prefWidthProperty().bind(leftPane.widthProperty());
        box.prefHeightProperty().bind(leftPane.heightProperty());
    }
    
    /**
     * Sets Undone scene to SplitPanes right pane. The scene contains undone
     * assignments in a TableView format.
     * 
     * @throws IOException 
     */
    public void showUndone() throws IOException {
        FXMLLoader undoneLoader = new FXMLLoader(DeadlinesBegoneApp.class.getResource("/fxml/Undone.fxml"));
        undoneLoader.setController(undoneController);
        VBox box = undoneLoader.load();
        rightPane.getChildren().clear();
        rightPane.getChildren().add(box);
        box.prefWidthProperty().bind(rightPane.widthProperty());
        box.prefHeightProperty().bind(rightPane.heightProperty());
    }
    
    /**
     * Sets NewAssignment scene to the SplitPanes right pane. The scene is for
     * assignment creation.
     * 
     * @param Assignment's Course
     * @throws IOException 
     */
    public void showNewAssignment(Course course) throws IOException {
        FXMLLoader newAssignmentLoader = new FXMLLoader(DeadlinesBegoneApp.class.getResource("/fxml/NewAssignment.fxml"));
        newAssignmentLoader.setController(newAssignmentController);
        newAssignmentController.setCourse(course);
        AnchorPane pane = newAssignmentLoader.load();
        rightPane.getChildren().clear();
        rightPane.getChildren().add(pane);    
    }
    
    private void showNewPeriod() throws IOException {
        FXMLLoader newPeriodLoader = new FXMLLoader(DeadlinesBegoneApp.class.getResource("/fxml/NewPeriod.fxml"));
        newPeriodLoader.setController(newPeriodController);
        AnchorPane pane = newPeriodLoader.load();
        rightPane.getChildren().clear();
        rightPane.getChildren().add(pane);  
    }
    
    /**
     * Sets TreeView scene to SplitPane's left pane and Undone scene to right pane.
     * 
     * @throws IOException 
     */
    public void showDefaultView() throws IOException {
        showTreeView();
        showUndone();
    }
    
    /**
     * Shows a GUI error alert.
     * 
     * @param Exception
     */
    public void error(Exception e) {
        Alert error = new Alert(Alert.AlertType.ERROR);
        error.setHeaderText("Something went wrong");
        error.setContentText(e.toString());
        error.showAndWait();
    }
    
    @FXML
    public void handleNewPeriod(ActionEvent event) throws IOException {
        showNewPeriod();
    }
    
    @FXML
    public void handleLoadPeriod(ActionEvent event) throws ClassNotFoundException, SQLException, IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select period");
        fileChooser.getExtensionFilters().add(new ExtensionFilter("Database files", "*.period"));
        File selectedPeriod = fileChooser.showOpenDialog(app.getStage());
        if (!(selectedPeriod == null)) {
            appService.loadDatabase(selectedPeriod.getAbsolutePath());
            showDefaultView();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupControllers();
        
        if (appService.databaseExists()) {
            try {
                showDefaultView();
            } catch (IOException ex) {
                System.out.println(ex);
            }
        } else {
            try {
                showNewPeriod();
            } catch (IOException ex) {
                System.out.println(ex);
            }
        }
    }

}
