# Architecture description  

## Structure  
The application consists of three parts: deadlinesbegone.dao, deadlinesbegone.domain and deadlinesbegone.ui. The communication between these parts can be seen at the class/package diagram below.  

The dao package handles all of the long term information saving, while the domain package handles the logic behind the application. The ui package consists of a user interface built with FXML/JavaFx.  

## Application logic   
The application's two main objects for representing data are [Assignment](https://github.com/Darake/deadlines-begone/blob/master/DeadlinesBegone/src/main/java/deadlinesbegone/domain/Assignment.java) and [Course](https://github.com/Darake/deadlines-begone/blob/master/DeadlinesBegone/src/main/java/deadlinesbegone/domain/Course.java). While [AbstractNamedObject](https://github.com/Darake/deadlines-begone/blob/master/DeadlinesBegone/src/main/java/deadlinesbegone/domain/AbstractNamedObject.java) helps with the dao and ui implementation. [TreeViewObject](https://github.com/Darake/deadlines-begone/blob/master/DeadlinesBegone/src/main/java/deadlinesbegone/domain/TreeViewObject.java) has a simple job of being an all-around object for JavaFX TreeView, when adding something else than Assignments or Courses to the tree.  

[DeadlinesBegoneService](https://github.com/Darake/deadlines-begone/blob/master/DeadlinesBegone/src/main/java/deadlinesbegone/domain/DeadlinesBegoneService.java) is the facilitator between the ui, domain and dao. It also holds the little logic needed in the app. It talks with both the [SQLAssignmentDao](https://github.com/Darake/deadlines-begone/blob/master/DeadlinesBegone/src/main/java/deadlinesbegone/dao/SQLAssignmentDao.java) and [SQLCourseDao](https://github.com/Darake/deadlines-begone/blob/master/DeadlinesBegone/src/main/java/deadlinesbegone/dao/SQLCourseDao.java), while forwarding the needed data to the ui.  
  
Class- / Package diagram:    
<img src="https://raw.githubusercontent.com/Darake/deadlines-begone/master/documentation/images/a-1.png" width="450">  

## Main functionality  
#### Adding a new course  
<img src="https://raw.githubusercontent.com/Darake/deadlines-begone/master/documentation/images/a-2.png" width="800">  
