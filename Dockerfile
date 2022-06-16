FROM eclipse-temurin:11-alpine
WORKDIR /
ADD target/blockchainlab1-1.0-SNAPSHOT.jar app.jar
RUN addgroup -S appgroup && adduser -S appuser -G appgroup
USER appuser
EXPOSE 8090
CMD java -jar -Dspring.profiles.active=prod app.jar