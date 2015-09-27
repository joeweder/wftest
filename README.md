# wftest
Code Test for Williams Forrest

Joe Weder 
09/27/2015

The application is divided into 3 modules:
 1. Model - contains domain model. 
 2. Services - DAO and Service layer.
	- Using myBatis mapping.
	- Using HSQLDB as embedded database for unit-testing.
 3. WebApp - Web application concerns.
	- Using Spring Framework. Java Config. 
	- Using HSQLDB as embedded database for demo.
 
To build, run 'mvn clean install' in model, services, then webapp.

To run, within the webapp folder run 'mvn jetty:run'

To access the form page: 
- http://localhost:8080/mailinglist/html/index.html

To test the REST controller listing method: 
	- http://localhost:8080/mailinglist/person
	- http://localhost:8080/mailinglist/person?name={a last name}
	- http://localhost:8080/mailinglist/person?descending=true|false (defaults to ascending)
	
I used jQuery and some CSS in the Mailing List Form.
	- Requirements specified 
	- "When the user submits this data, a confirmation page should be displayed which tells the user the data was received by the system."
	However, I did not take this statement literally and used AJAX to POST the data and success functions to dynamically update the status.
	
	