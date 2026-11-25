# Image de base : JRE seulement (pas de JDK), Alpine, version epinglee -> petite surface d'attaque
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Utilisateur non-root avec un UID numerique
RUN addgroup -S -g 10001 app && adduser -S -u 10001 -G app app

# Le JAR est construit par Maven AVANT le docker build
COPY target/library-api.jar app.jar

USER 10001

EXPOSE 8080

HEALTHCHECK --interval=30s --timeout=3s --start-period=30s --retries=3 \
  CMD ["wget", "-qO-", "http://localhost:8080/"]

ENTRYPOINT ["java", "-jar", "/app/app.jar"]