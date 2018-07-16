# SplunkTests
This contains Splunk tests

What are USED : 
1: Coding : Java
2: Rest Client to Test : RestAssured
3: To Drive Test & Assert & Report : TestNg (Default report , But we can custumize)
4: Maven to Build and Dependencies.
5. Property file to keep Endpoints
6: Log4j for logs & Reporter log 


How to Use:

#1:To kick of test you need to run test as TestNg tests , I have added one common and one specific group name to each test which will hepls while execution 
2: All Get call related tests are in GetMovies.Java under(com.splunk.test) package
3: All POST call related tests are in GetMovies.Java under(com.splunk.test)package
4: Class ReusableFunctions.java under (com.splunk.commons)package contains all Reused functions . 
5. Class BaseSetup.java currently dont have much things but at real time we will using it for initializing. Ex: data read from CSV , defining Before Class, Before suite etc..
6: Under com.splunk.sampleproject.dataread poackage i kept utils lile Excel read , Property read 
7: Under resource folder contains Service enpoints , Log Properties 
8: com.splunk.pojos package contains request pojos.

####Test Cases ######
I have added TestCase file Which clearly talks all covered tests and behaviour and results bugs .


Note :POST is not woking , I'mean not 201 created nor any unique Id to validate in reposne . So could add much POSt tests 
      And also due time constraint I did not implement Data passing from CSV/Data Driven , Customized Asserts , Customized logging , Customized Reporting etc ..   
