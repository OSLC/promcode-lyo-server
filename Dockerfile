FROM docker.io/library/maven:3-eclipse-temurin-21-alpine AS build

COPY . /src
WORKDIR /src
RUN mvn -B --no-transfer-progress -f promcode-lyo-server/pom.xml -DskipTests clean package -am

FROM docker.io/library/jetty:12-jre21-eclipse-temurin
# WARNING DO NOT CHANGE WORKDIR or set it back to what it was before
# $JETTY_BASE must be correct before starting Jetty

COPY --from=build /src/promcode-lyo-server/target/*.war /var/lib/jetty/webapps/ROOT.war

RUN java -jar "$JETTY_HOME/start.jar" --add-modules=ee9-deploy,ee9-jsp,ee9-jstl

EXPOSE 8080
