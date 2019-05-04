
package deadlinesbegone.ui;

import deadlinesbegone.domain.AbstractNamedObject;
import deadlinesbegone.domain.Assignment;
import deadlinesbegone.domain.Course;
import deadlinesbegone.domain.DeadlinesBegoneService;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Comparator;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class TreeCellWithContextMenu extends TreeCell<AbstractNamedObject> {
    
    private ContextMenu courseMenu;
    private ContextMenu assignmentMenu;
    private ImageView checkmarkView = new ImageView(new Image(getClass().getResourceAsStream("/checkmark.png")));

    public TreeCellWithContextMenu(MainController mainController, DeadlinesBegoneService appService) {
        MenuItem newAssignment = new MenuItem("New Assignment");
        newAssignment.setOnAction(e -> {
            try {
                mainController.showNewAssignment((Course) getTreeItem().getValue());
            } catch (IOException ex) {
                mainController.error(ex);
            }
        });
        MenuItem deleteCourse = new MenuItem("Delete");
        deleteCourse.setOnAction(e -> {
            TreeItem item = (TreeItem) mainController.treeViewController.treeView.getSelectionModel().getSelectedItem();
            item.getParent().getChildren().remove(item);
            try {
                appService.deleteCourse((Course) item.getValue());
                mainController.showUndone();
            } catch (SQLException ex) {
                mainController.error(ex);
            } catch (IOException ex) {
                mainController.error(ex);
            }
        });
        courseMenu = new ContextMenu();
        courseMenu.getItems().add(newAssignment);
        courseMenu.getItems().add(deleteCourse);
        
        MenuItem markDone = new MenuItem("Mark done");
        markDone.setOnAction(e -> {
            getTreeItem().setGraphic(checkmarkView);
            setGraphic(checkmarkView);
            try {
                appService.markAssignmentDone((Assignment) getTreeItem().getValue());
                mainController.showUndone();
            } catch (SQLException ex) {
                mainController.error(ex);
            } catch (IOException ex) {
                mainController.error(ex);
            }
        });
        MenuItem deleteAssignment = new MenuItem("Delete");
        deleteAssignment.setOnAction(e -> {
            TreeItem item = (TreeItem) mainController.treeViewController.treeView.getSelectionModel().getSelectedItem();
            item.getParent().getChildren().remove(item);
            try {
                appService.deleteAssignment((Assignment) item.getValue());
                mainController.showUndone();
            } catch (SQLException ex) {
                mainController.error(ex);
            } catch (IOException ex) {
                mainController.error(ex);
            }
        });
        assignmentMenu = new ContextMenu();
        assignmentMenu.getItems().add(markDone);
        assignmentMenu.getItems().add(deleteAssignment);
    }
    
    @Override
    public void updateItem(AbstractNamedObject item, boolean empty) {
        super.updateItem(item, empty);
        
        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            setText(getItem().toString());
            setGraphic(getTreeItem().getGraphic());
            if (getTreeItem().getParent().getValue().toString().contentEquals("Root")) {
                setContextMenu(courseMenu);
                getTreeItem().getChildren().sort(Comparator.comparing(a -> a.getChildren().get(0).getValue().toString()));
            } else if (getTreeItem().getParent().getParent().getValue().toString().contentEquals("Root")) {
                setContextMenu(assignmentMenu);
                Assignment assignment = (Assignment) getTreeItem().getValue();
                if (assignment.getCompleted()) {
                    getTreeItem().setGraphic(checkmarkView);
                    setGraphic(checkmarkView);
                }
            } else {
                setContextMenu(null);
            }
        }
    }
}
