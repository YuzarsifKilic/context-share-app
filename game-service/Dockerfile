# Build stage
FROM maven:latest AS build
WORKDIR /build
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the source code and compile the application while skipping tests
COPY src ./src
RUN ARTIFACT_ID=$(mvn -q -DforceStdout help:evaluate -Dexpression=project.artifactId) && \
    JAR_VERSION=$(mvn -q -DforceStdout help:evaluate -Dexpression=project.version) && \
    echo "Building version: $JAR_VERSION" && \
    mvn clean package -DskipTests && \
    cp target/${ARTIFACT_ID}-${JAR_VERSION}.jar /build/app-${JAR_VERSION}.jar

# Runtime stage
FROM amazoncorretto:21
WORKDIR /app

# Copy the jar file to the runtime image
COPY --from=build /build/app-*.jar /app/

EXPOSE 8080

# Run the application with the dynamically determined JAR name
CMD ["sh", "-c", "java -jar /app/app-*.jar"]