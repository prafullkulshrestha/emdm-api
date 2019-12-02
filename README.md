# Enterprise Master Data Management API

* The project exposes the APIs for managing the master data for organizations, developers etc.
* The APIs expect a header 'User-Id' in the request without it, it responds with the 400 response message.
* The APIs exposed by the project can be rate limited by seeding the database for a user and the Api he/she is going to access.
* If there are no entries for limiting a user request present in a database for an end point default configurable
rate limit (currently 40) will be applied. 
* The project maintains an in memory cache in a concurrent hash map to manage the logic for rate limiting.
* The cache is queried for a rate limiter object for a user id and the api uri.
* If the object is not found, one is created and put in the cache and a permit counter is created from the configured value for it. 
* This request and the subsequent requests with the same user id and api key decrease the counter permit for the configured time period in minutes for that user and api.
* If the counter permits get exhausted and user makes more requests after that for same api the user get 429 status code in the response depicting a denial of service kind of scenario.
* After the time limit expires user is again allowed to make configured number of requests for that api for the next same period of configured time.
* The application comes up with the YAML configuration file for configuring a few values in it.

## Database Tables
* The project uses postgres 9.6 version running at 5432 port, but you don't need to install it.
* Table 'organizations' keeps the organization data
* Table 'developers' keeps the developers data.
* Table 'user_api_rate_limits' api keeps the user id, the api and the no of requests a user can make on the api.

## Assumptions
* The application is not going to run in a cluster mode.
* One Organization is associated with more than one developers
* One developer is associated with one organization only
* Once configured the minutely rate limit will apply to all the apis uniformly.

## Prerequisite
* Install Docker, I used version = 18.03.1-ce
* Download STS to import and do further development on it
* Install Maven, I used version = 3.5.3
* Build and run project on MAC or Unix based system 

## Import

* Unzip the project source code zip file.
* In spring source tool IDE, click - File -> New -> "Java Project".
* Refer to the root directory of you project to simport it as a new project
* Right click on the project in the IDE, go to "Configure" -> "Convert to maven project"
* Right click on the project in the IDE, go to "Maven" to update the project

## Testcases

* The test cases are the pure unit test cases.
* There are controller class unit test cases
* There are service class unit test cases.

## Build

* Execute the command from the application root directory - 'mvn clean package' command to build the project.

## Docker image

* The application and the database are composed in a docker image.

## Start the application

* Execute the command from the following application root directory.
 'docker-compose up --build --force-recreate -d'. After running the application starts at the port 5555 and with the context root /api

## End points to test

* GET /api/v1/developers (http://localhost:5555/api/v1/developers)
* GET /api/v1/organizations (http://localhost:5555/api/v1/organizations)

## Stop the application

* Execute the following command from the application root directory
 'docker-compose down'

## Exception

* The exception handling mechanism has been set up
* NoDataFoundException is a custom runtime exception created this returns 404 if the data is not present in the tables.
* TooManyException is a custom runtime exception created this returns 404 if the data is not present in the tables.

## API testing using Swagger

* /api/swagger-ui.html# to check and execute the end points available. (e.g. http://localhost:5555/api/swagger-ui.html#)

## Sonar code analysis (Optional)

* Build the project first
* Start the sonar server (I used version 7.8)
* Command for running sonar reports

 mvn sonar:sonar \
  -Dsonar.projectKey=emdm \
  -Dsonar.host.url=http://localhost:9000 \
  -Dsonar.sources=src/main/java \
  -Dsonar.tests=src/test/java \
  -Dsonar.java.binaries=target/classes \
  -Dsonar.java.test.binaries=target/test-classes \
  -Dsonar.java.libraries=target \
  -Dsonar.login=6aa87237be17a252198d84b6225605951b2e9dc1
  
  You would need to use your sonar project key and token

## Enhancements/Possible improvements

* If the application is going to run in a cluster, the cache used should be a cluster wide cache for rate limiting logic.
* We should create separate dockerized (containerized) images for the api and the database project.
* Should use sequence generator instead of identity.
* The entities should have more attributes for example developers should be associated with skills.
* More APIs could be added.
* Different configuration files should be created for different environments or externalize the configuration itself.
* The calls to fetch the data from database static in nature should be cached.
* We should identify the table columns on which we need indexes based upon the search criteria requirements.
* Swagger configuration values should be externalized.

another test2
