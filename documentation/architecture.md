# Architecture description  

## Structure  
The application consists of three parts: deadlinesbegone.dao, deadlinesbegone.domain and deadlinesbegone.ui. The communication between these parts can be seen at the class/package diagram below.  

The dao package handles all of the long term information saving, while the domain package handles the logic behind the application. The ui package consists of a user interface built with FXML/JavaFx.  

## Application logic   
The application's two main objects for representing data are [Assignment](https://github.com/Darake/deadlines-begone/blob/master/DeadlinesBegone/src/main/java/deadlinesbegone/domain/Assignment.java) and [Course](https://github.com/Darake/deadlines-begone/blob/master/DeadlinesBegone/src/main/java/deadlinesbegone/domain/Course.java). While [AbstractNamedObject](https://github.com/Darake/deadlines-begone/blob/master/DeadlinesBegone/src/main/java/deadlinesbegone/domain/AbstractNamedObject.java) helps with the dao and ui implementation. [TreeViewObject](https://github.com/Darake/deadlines-begone/blob/master/DeadlinesBegone/src/main/java/deadlinesbegone/domain/TreeViewObject.java) has a simple job of being an all-around object for JavaFX TreeView, when adding something else than Assignments or Courses to the tree.  

[DeadlinesBegoneService](https://github.com/Darake/deadlines-begone/blob/master/DeadlinesBegone/src/main/java/deadlinesbegone/domain/DeadlinesBegoneService.java) is the facilitator between the ui, domain and dao. It also holds the little logic needed in the app. It talks with both the [SQLAssignmentDao](https://github.com/Darake/deadlines-begone/blob/master/DeadlinesBegone/src/main/java/deadlinesbegone/dao/SQLAssignmentDao.java) and [SQLCourseDao](https://github.com/Darake/deadlines-begone/blob/master/DeadlinesBegone/src/main/java/deadlinesbegone/dao/SQLCourseDao.java), while forwarding the needed data to the ui.  
  
Class- / Package diagram:    
<img src="https://raw.githubusercontent.com/Darake/deadlines-begone/master/documentation/images/a-1.png" width="450">  

## User interface  
The user interface is built with different FXML scenes and their controllers. The main scene consists of a menubar at the top and a splitpane window. Other scenes and content is then placed on these two panes in the splitpane, with the left one mostly being for a TreeView for courses and assignments. The right pane could be thought as the main content window. The content/scene in it differs on what is being done in the application. The scenes at the moment consist of  
* showing undone assignments  
* creating a new period  
* creating a new assignment.  

The changing of scenes happens with methods in the [MainController](https://github.com/Darake/deadlines-begone/blob/master/DeadlinesBegone/src/main/java/deadlinesbegone/ui/MainController.java) class.  

## Data storage  
The application's data is stored in SQlite databases. The [deadlinesbegone.dao](https://github.com/Darake/deadlines-begone/tree/master/DeadlinesBegone/src/main/java/deadlinesbegone/dao) package handles the communication between the databases and the application. The package conists of a [Database](https://github.com/Darake/deadlines-begone/blob/master/DeadlinesBegone/src/main/java/deadlinesbegone/dao/Database.java) class which handles the database connection and creates a new one if needed. On the other end, the classes implementing the [Dao](https://github.com/Darake/deadlines-begone/blob/master/DeadlinesBegone/src/main/java/deadlinesbegone/dao/Dao.java) interface make the needed queries.  

The application reads the active database's path from a config.properties file. One is created if it doesn't exist.  

When a new database is created, it is created with a ".period" ending. The databases themselves have the following schema:  
``` 
CREATE TABLE Course (
 id integer PRIMARY KEY,
 name varchar(50) NOT NULL
 );
CREATE TABLE Assignment (
 id integer PRIMARY KEY,
 name varchar(100) NOT NULL,
 deadline varchar(10) NOT NULL,
 completed boolean NOT NULL,
 course_id integer NOT NULL,
 CONSTRAINT fk_course
   FOREIGN KEY (course_id)
   REFERENCES Course(id)
   ON DELETE CASCADE
 );
```  

## Examples of functionality  
#### Adding a new course  
<img src="https://raw.githubusercontent.com/Darake/deadlines-begone/master/documentation/images/a-2.png" width="800">  
