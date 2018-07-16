# SplunkTests
This contains Splunk tests

What are USED : 
1: Coding : Java
2: Rest Client to Test : RestAssured 
3: To Drive Test & Assert & Report : TestNg (Default report , But we can customize)
4: Maven to Build and Dependencies.
5. Property file to keep Endpoints
6: Log4j for logs & Reporter log 


How to Use:

#1:To kick of test you need to run test as TestNg tests , I have added one common and one specific group name to each test which will helps while execution 
2: All Get call related tests are in GetMovies.Java under(com.splunk.test) package
3: All POST call related tests are in GetMovies.Java under(com.splunk.test)package
4: Class ReusableFunctions.java under (com.splunk.commons)package contains all Reused functions . 
5. Class BaseSetup.java currently don't have much lines but at real time we will using it for initializing. Ex: data read from CSV , defining Before Class, Before suite etc..
6: Under com.splunk.sampleproject.dataread package i kept utils file like Excel read , Property read 
7: Under resource folder contains Service endpoints , Log Properties 
8: com.splunk.pojos package contains request pojos.
9: Here I'm running classes in parallel , However we can run them at different levels . 
10: We can setup Jenkins to Run these tests


####Test Cases ######

I have added TestCase file with name : Splunk_Tests.xlsx Which clearly talks all covered tests and behaviour and results bugs .


Note :

#1 : POST is not working , I'mean not 201 created nor any unique Id to validate in reponse . So could add much POST tests
#2 : Optional Parameters not working . 
#3: As I have some of questions unanswered questions which are not added as tests . 

Things which can be done : 

#1 :And also due time constraint  
   I did not implement Data passing from CSV/Data Driven , Customized Asserts , Customized logging , Customized Reporting etc.. 
   #2: As Post request is small ,i did not use design pattern. 
#3: We have other best practice which will help as and when test count increase and to reduce execution time ,Reduce debugging time , Fancy reporting etc , Sure Fire Report , Multiple instance etc... 
