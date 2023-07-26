# DATALOG TRANSFORMER
___
This application transforms datalogs stored in a MongoDB database from an old version to the current version.


### Usage
___

#### Running locally

* Clone the repository: `git clone https://github.com/ryzingTitan/datalog-transformer.git`
* Navigate to the folder where the repository has been cloned: `cd datalog-transformer`
* Configure a MongoDB instance for the application to store the datalogs
  * Run a MongoDB Docker container: `docker run -d -p 27017:27017 --name mongo mongo:6.0.3-focal`
  * Install MongoDB locally on your workstation with these [instructions](https://www.mongodb.com/docs/manual/installation/)
* Update the `production` profile in the [configuration file](./src/main/resources/application.yaml)
  * Update the MongoDB connection information to match the instance of MongoDB you will be using
* Build the JAR file: `./gradlew clean build`
  * This will place a ZIP file with the application in the `./build/distributions` folder
* Copy the ZIP file to the location where you want to run the application and unzip the folder
* Run the application 
  * `java -jar datalog-transformer.jar`

### Integration Tests
___

The Cucumber integration tests for this application require a connection to an active MongoDB instance.
By default, the integration test profile (the cucumber profile) is configured to use a local instance of MongoDB
running on the default port (27017). The tests will create a database named `cucumberTest` to use during testing 
to avoid data conflicts. If you prefer to use an instance of MongoDB that is not running locally or not using the 
default port, you will need to update the integration test profile with the connection information of the MongoDB
instance you want to use.

### Acknowledgements
___

All the hard work done by the developers of the [Torque](https://torque-bhp.com/) application has enabled me to create this project.
