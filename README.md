# public-transport
Real-Time Rail Predictions

Web application is designed to process the request about the next train arrival information from a particular station.
It also has the feature to detect the geo location if permission is granted by the user.

Public API used for fetching real time train data :Washington Metropolitan Area Transit Authority API 
Ref : https://www.wmata.com/
Same API can used for getting real time data of bus services as well details about stops/stations etc.

#3rd party libraries
  log4j.jar :  For logging purpose on console as well as separate file
  org.apache.commons.httpclient : as a web service client 
  gson-2.2.2.jar : For processing API response
  
Framework used is Model View Controller(MVC).Welcome page is index.jsp and on entering the url user is 
  prompted to give permission to share his location details which can be further used for advanced features of the API
  like route display using google maps.List of next train arrival information is displayed via ajax call using jquery. 
PTController class will be processing the request and forwarding the request to necessary action class and sending the 
   response back to the server.
PublicTransportWS.java class is used as web service class for processing the request.

