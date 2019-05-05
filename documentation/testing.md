# Testing  
The application has unit tests for dao and domain classes done with JUnit. In addition, the user interface and the overall
workability has been tested manually.  

## JUnit tests  

### DAO  
Application's DAO classes and the [class for database setup and connection handling](https://github.com/Darake/deadlines-begone/blob/master/DeadlinesBegone/src/main/java/deadlinesbegone/dao/Database.java)
have tests built for them. The JUnit tests use the TemporaryFolder rule to create a temporary testing database for the tests.  

### Domain  
The [domain package](https://github.com/Darake/deadlines-begone/tree/master/DeadlinesBegone/src/main/java/deadlinesbegone/domain)
has [DeadlinesBegoneServiceTests](https://github.com/Darake/deadlines-begone/blob/master/DeadlinesBegone/src/test/java/deadlinesbegone/domain/DeadlinesBegoneServiceTest.java)
as a testing class, which also makes use of the TemporaryFolder rule. For help, the [domain test package](https://github.com/Darake/deadlines-begone/tree/master/DeadlinesBegone/src/test/java/deadlinesbegone/domain)
also contains two fake DAOs.  

### Coverage  
The application's test coverage (excluding user interface) is 75% for instructions and 81% for branches.  
image  

## Manual testing  
The application was tested on a Linux environment. All of the promised functionality in the [requirements spec](https://github.com/Darake/deadlines-begone/blob/master/documentation/Software%20requirements%20specification.md)
was tested for. In addition, the cohersion of the application was tested (i.e if the assignment is marked done in the undone view
will a checkmark appear in the treeview).  

## Known problems    
The application does not validate all the user input, which means that some errors appear in the console.
