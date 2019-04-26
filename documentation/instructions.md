# Instructions  

## Getting started  
### Download

Download the latest version of the .jar file from [here.](https://github.com/Darake/deadlines-begone/releases)  

### Configuration  

The application expects to have a config.properties file in the directory with the following content:
database=database.db  

Where database.db can be replaced with your own database.  

### Run the program  

The application can be started by typing the following in the terminal:  
```java -jar deadlines-begone.jar```   


## Usage  
### Adding a course  

Let's start by adding a course. By clicking the "Add course" button a popup window will appear:  
<img src="https://raw.githubusercontent.com/Darake/deadlines-begone/master/documentation/images/i-1.png" width="450">  
New course can be added by typing the name of the course to the textbox and clicking "OK".  

### Adding an assignment  

Next we can add an assignment to the course we just added. By right clicking the course a context menu appears:  
<img src="https://raw.githubusercontent.com/Darake/deadlines-begone/master/documentation/images/i-2.png" width="450">   
Next click the "Add assignment" option and the following window in the right should change to the following:  
<img src="https://raw.githubusercontent.com/Darake/deadlines-begone/master/documentation/images/i-3.png" width="450">  
Enter the assignments name as well as the deadline and click add. You can now see the added assignment in the main
window and in the left when clicking on the course.  
<img src="https://raw.githubusercontent.com/Darake/deadlines-begone/master/documentation/images/i-4.png" width="450">  

### Marking an assignment done  

Assignments can be marked done by right clicking on it on the left or right side. In both cases a context menu appears:  
<img src="https://raw.githubusercontent.com/Darake/deadlines-begone/master/documentation/images/i-5.png" width="450">
<img src="https://raw.githubusercontent.com/Darake/deadlines-begone/master/documentation/images/i-6.png" width="450">  
Clicking the "Mark done" option marks the assignment done. Removing it from the undone assignment section and adding a
checkmark on the treeview section on the left.  
<img src="https://raw.githubusercontent.com/Darake/deadlines-begone/master/documentation/images/i-7.png" width="450">  

### Deleting assignments or courses  

Deleting courses or assignments is simple. By right clicking a course or an assignment on the treeview a context menu 
appears. Then selecting the "Delete" option removes it from the application.  
image

