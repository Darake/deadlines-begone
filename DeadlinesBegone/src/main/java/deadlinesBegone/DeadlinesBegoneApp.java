package deadlinesBegone;

import deadlinesBegone.dao.Database;
import deadlinesBegone.dao.SQLCourseDao;
import deadlinesBegone.domain.DeadlinesBegoneService;
import java.io.FileInputStream;
import java.util.Properties;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import deadlinesBegone.dao.Dao;
import deadlinesBegone.dao.SQLAssignmentDao;


public class DeadlinesBegoneApp extends Application {
    
    private Stage stage;
    private DeadlinesBegoneService appService;
    private Scene scene;
    
    @Override
    public void init() throws Exception {
        Properties properties = new Properties();
        properties.load(new FileInputStream("config.properties"));
        Database db = new Database(properties.getProperty("database"));
        
        Dao courseDao = new SQLCourseDao(db, "course");
        Dao assignmentDao = new SQLAssignmentDao(db, "assignment", courseDao);
        this.appService = new DeadlinesBegoneService(courseDao, assignmentDao);
        
        MainController controller = new MainController();
        controller.setAppService(this.appService);
        
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/Scene.fxml"));
        loader.setController(controller);
        
        Parent pane = loader.load();
        this.scene = new Scene(pane);
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        
        this.stage.setTitle("Deadlines Begone");
        this.stage.setScene(this.scene);
        this.stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
