FROM openjdk:17-jdk-alpine
COPY target/school-students-0.0.1-SNAPSHOT.jar school-students-app.jar
ENTRYPOINT [ "java", "-jar", "school-students-app.jar" ]