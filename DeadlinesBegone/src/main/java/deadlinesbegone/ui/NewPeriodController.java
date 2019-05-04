
package deadlinesbegone.ui;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * Controller for NewPeriod FXML scene
 * 
 */
public class NewPeriodController extends SubController {

    @FXML
    private TextField name;
    
    @FXML
    private Button cancel;
    
    @FXML
    public void handleAddPeriod(ActionEvent event) throws ClassNotFoundException, SQLException, IOException {
        appService.newDatabase(name.getText());
        mainController.showDefaultView();
    }

    @FXML
    public void handleCancel(ActionEvent event) throws IOException {
        mainController.showUndone();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (!appService.databaseExists()) {
            cancel.setVisible(false);
        }
    }

}
