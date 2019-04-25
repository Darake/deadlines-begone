
package deadlinesbegone.ui;

import deadlinesbegone.domain.Assignment;
import deadlinesbegone.domain.Course;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;

import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class UndoneController implements Initializable {

    private MainController mainController;
    
    @FXML
    private TableView<Assignment> tableView;

    @FXML
    private TableColumn<Assignment, String> deadlineColumn;

    @FXML
    private TableColumn<Assignment, String> assignmentColumn;

    @FXML
    private TableColumn<Assignment, Course> courseColumn;
    
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            List<Assignment> assignmentList = mainController.appService.getUndoneAssignments();
            ObservableList<Assignment> assignments = FXCollections.observableList(assignmentList);
            deadlineColumn.setCellValueFactory(new PropertyValueFactory<Assignment, String>("deadline"));
            assignmentColumn.setCellValueFactory(new PropertyValueFactory<Assignment, String>("name"));
            courseColumn.setCellValueFactory(new PropertyValueFactory<Assignment, Course>("course")); 
            tableView.setItems(assignments);
            tableView.setStyle("-fx-focus-color: transparent;");
            tableView.getSortOrder().add(deadlineColumn);
            MenuItem markDone = new MenuItem("Mark done");
            markDone.setOnAction(e -> {
                try {
                    Assignment assignment = tableView.getSelectionModel().getSelectedItem();
                    mainController.appService.markAssignmentDone(assignment);
                    tableView.getItems().remove(assignment);
                    mainController.treeView.refresh();
                } catch (SQLException ex) {
                    mainController.error(ex);
                }
            });
            ContextMenu menu = new ContextMenu();
            menu.getItems().add(markDone);
            tableView.setContextMenu(menu);
        } catch (SQLException ex) {
            mainController.error(ex);
        }
    }

}
