
package deadlinesBegone;

import deadlinesBegone.domain.Course;
import deadlinesBegone.domain.DeadlinesBegoneService;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeCell;
import javafx.scene.layout.AnchorPane;

public class TreeCellWithContextMenu extends TreeCell<String> {
    
    private ContextMenu courseMenu;
    
    public TreeCellWithContextMenu(DeadlinesBegoneService appService, AnchorPane content, MainController mainController) {
        MenuItem newAssignment = new MenuItem("New Assignment");
        courseMenu = new ContextMenu();
        courseMenu.getItems().add(newAssignment);
        newAssignment.setOnAction(e -> {
            NewAssignmentController controller = new NewAssignmentController();
            controller.setAppService(appService);
            controller.setMainController(mainController);
            Course course;
            try {
                course = appService.getCourseByName(getTreeItem().getValue());
                controller.setCourse(course);
                FXMLLoader loader = new FXMLLoader(DeadlinesBegoneApp.class.getResource("/fxml/NewAssignment.fxml"));
                loader.setController(controller);
                AnchorPane pane;
                pane = loader.load();
                content.getChildren().clear();
                content.getChildren().add(pane);
            } catch (Exception ex) {
                System.out.println(ex);
                Alert error = new Alert(AlertType.ERROR);
                error.setHeaderText("Something went wrong");
                error.setContentText(ex.toString());
                error.showAndWait();
            }         
        });
    }
    
    @Override
    public void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        
        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            setText(getItem().toString());
            setGraphic(getTreeItem().getGraphic());
            if (getTreeItem().getParent().getValue().contentEquals("Root")) {
                setContextMenu(courseMenu);
            } else {
                setContextMenu(null);
            }
        }
    }
}
