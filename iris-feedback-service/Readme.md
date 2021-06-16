# IRIS Feedback Service
This service provides a post api endpoint for a json feedback data request. This data is used to create a git issue with the Git-API. So every post request a git issue is created in the choosen repository.

## Configure git repository
The configuration of the git repository, which is responsible for saving the git issues, is located in the src/main/resources/application.properties file. There are two main properties providing the information about the used git repository. The property git.user stores the username and git.repoName holds the choosen git repository name.


## Starting service
To start the service the personal access token  has to be passed to the service at startup. With this token the service is able to send post requests to the configured git repository. The following command is used to start the individual Iris-Feedback-Service:
```
mvn spring-boot:run  -Dspring-boot.run.arguments="--git.personalAccessToken=$token" -am -pl iris-feedback-service
```
