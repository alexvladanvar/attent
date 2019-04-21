FROM maven:3.5-jdk-8-alpine as build
WORKDIR /app
COPY . /app
RUN mvn clean package

FROM tomcat as deploy
WORKDIR /usr/local/tomcat
RUN rm -rf /usr/local/tomcat/webapps && mkdir /usr/local/tomcat/webapps
COPY --from=build /app/target/MySpringMVC-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war
EXPOSE 8080