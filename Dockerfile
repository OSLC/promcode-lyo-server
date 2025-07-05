FROM docker.io/library/maven:3-eclipse-temurin-21-alpine AS build

WORKDIR /src
# Copy pom.xml first for better Docker layer caching
COPY promcode-lyo-server/pom.xml promcode-lyo-server/pom.xml

# Download dependencies (will be cached if pom.xml doesn't change)
RUN mvn -B --no-transfer-progress -f promcode-lyo-server/pom.xml dependency:go-offline

# Copy the rest of the source code
COPY . .

# Build the application
RUN mvn -B --no-transfer-progress -f promcode-lyo-server/pom.xml -DskipTests clean package -am

FROM docker.io/library/jetty:12-jre21-eclipse-temurin

# Add metadata
LABEL org.opencontainers.image.title="PROMCODE Server"
LABEL org.opencontainers.image.description="PROMCODE OSLC Server - A demonstration server for OSLC toolchain management"
LABEL org.opencontainers.image.source="https://github.com/oslc/promcode-lyo-server"
LABEL org.opencontainers.image.licenses="EPL-2.0"

# WARNING DO NOT CHANGE WORKDIR or set it back to what it was before
# $JETTY_BASE must be correct before starting Jetty

COPY --from=build /src/promcode-lyo-server/target/*.war /var/lib/jetty/webapps/ROOT.war

RUN java -jar "$JETTY_HOME/start.jar" --add-modules=ee9-deploy,ee9-jsp,ee9-jstl

EXPOSE 8080
