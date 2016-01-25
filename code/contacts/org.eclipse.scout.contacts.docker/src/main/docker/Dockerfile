FROM java:8-jre
WORKDIR /
ADD org.eclipse.scout.contacts.docker-5.2.0-SNAPSHOT.war /contacts.war
ADD jetty-runner.jar /jetty-runner.jar
CMD ["java","-cp","jetty-runner.jar","org.eclipse.jetty.runner.Runner","contacts.war"]
