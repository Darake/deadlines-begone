
package deadlinesbegone.ui;

import deadlinesbegone.domain.DeadlinesBegoneService;
import javafx.fxml.Initializable;

/**
 * An abstract class for FXML scene controllers under MainController.
 * 
 */
public abstract class SubController implements Initializable {
    
    protected MainController mainController;
    protected DeadlinesBegoneService appService;
    
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
    
    public void setAppService(DeadlinesBegoneService appService) {
        this.appService = appService;
    }
}
