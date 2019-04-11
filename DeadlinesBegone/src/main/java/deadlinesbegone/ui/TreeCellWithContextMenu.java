
package deadlinesbegone.ui;

import deadlinesbegone.domain.AbstractNamedObject;
import deadlinesbegone.domain.Assignment;
import deadlinesbegone.domain.Course;
import java.io.IOException;
import java.sql.SQLException;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.image.ImageView;

public class TreeCellWithContextMenu extends TreeCell<AbstractNamedObject> {
    
    private ContextMenu courseMenu;
    private ContextMenu assignmentMenu;

    public TreeCellWithContextMenu(MainController mainController) {

        MenuItem newAssignment = new MenuItem("New Assignment");
        newAssignment.setOnAction(e -> {
            try {
                mainController.changeViewToNewAssignment((Course) getTreeItem().getValue());
            } catch (IOException ex) {
                mainController.error(ex);
            }
        });
        MenuItem deleteCourse = new MenuItem("Delete");
        deleteCourse.setOnAction(e -> {
            TreeItem item = (TreeItem) mainController.treeView.getSelectionModel().getSelectedItem();
            item.getParent().getChildren().remove(item);
            try {
                mainController.appService.deleteCourse((Course) item.getValue());
            } catch (SQLException ex) {
                mainController.error(ex);
            }
        });
        courseMenu = new ContextMenu();
        courseMenu.getItems().add(newAssignment);
        courseMenu.getItems().add(deleteCourse);
        
        MenuItem markDone = new MenuItem("Mark done");
        markDone.setOnAction(e -> {
            ImageView checkmarkView = new ImageView(mainController.checkmark);
            getTreeItem().setGraphic(checkmarkView);
            setGraphic(checkmarkView);
            try {
                mainController.appService.markAssignmentDone((Assignment) getTreeItem().getValue());
            } catch (SQLException ex) {
                mainController.error(ex);
            }
        });
        MenuItem deleteAssignment = new MenuItem("Delete");
        deleteAssignment.setOnAction(e -> {
            TreeItem item = (TreeItem) mainController.treeView.getSelectionModel().getSelectedItem();
            item.getParent().getChildren().remove(item);
            try {
                mainController.appService.deleteAssignment((Assignment) item.getValue());
            } catch (SQLException ex) {
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
            } else if (getTreeItem().getParent().getParent().getValue().toString().contentEquals("Root")) {
                setContextMenu(assignmentMenu);
            } else {
                setContextMenu(null);
            }
        }
    }
}
