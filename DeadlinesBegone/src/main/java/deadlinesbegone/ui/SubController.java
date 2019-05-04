
package deadlinesbegone.ui;

import deadlinesbegone.domain.DeadlinesBegoneService;
import javafx.fxml.Initializable;

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
