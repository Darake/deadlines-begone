package deadlinesbegone.ui;

import deadlinesbegone.dao.Database;
import deadlinesbegone.dao.SQLCourseDao;
import deadlinesbegone.domain.DeadlinesBegoneService;
import java.io.FileInputStream;
import java.util.Properties;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import deadlinesbegone.dao.Dao;
import deadlinesbegone.dao.SQLAssignmentDao;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class DeadlinesBegoneApp extends Application {
    
    private Stage stage;
    private DeadlinesBegoneService appService;
    private Scene scene;
    private MainController controller;
    private Database db;
    private Properties properties;
    
    public Database getDb() {
        return this.db;
    }
    
    public Stage getStage() {
        return stage;
    }
    
    private void initializeDatabaseObject() throws IOException, ClassNotFoundException, SQLException {
        new File("config.properties").createNewFile();
        properties = new Properties();
        properties.load(new FileInputStream("config.properties"));
        String dbName = properties.getProperty("database");
        db = new Database();
        if (!(dbName == null)) {
            db.setupDatabase(dbName);
        }
    }
    
    private void initializeAppService() {
        Dao courseDao = new SQLCourseDao(db, "course");
        Dao assignmentDao = new SQLAssignmentDao(db, "assignment", courseDao);
        this.appService = new DeadlinesBegoneService(db, properties, courseDao, assignmentDao);
    }
    
    private void initializeController() {
        controller = new MainController();
        controller.setAppService(this.appService);
        controller.setApp(this);
    }
    
    @Override
    public void init() throws Exception {
        initializeDatabaseObject();
        initializeAppService();
        initializeController();
        
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/Scene.fxml"));
        loader.setController(controller);
        Parent pane = loader.load();
        scene = new Scene(pane);
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
