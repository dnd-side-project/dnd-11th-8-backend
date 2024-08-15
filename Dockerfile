FROM openjdk:17-slim

ARG PROFILE
ARG DB_URL_PROD
ARG DB_USERNAME_PROD
ARG DB_PASSWORD_PROD
ARG OPEN_API_SERVICE_KEY
ARG KAKAO_CLIENT_ID

COPY build/libs/blooming-0.0.1-SNAPSHOT.jar app.jar

ENV PROFILE=${PROFILE}
ENV DB_URL_PROD=${DB_URL_PROD}
ENV DB_USERNAME_PROD=${DB_USERNAME_PROD}
ENV DB_PASSWORD_PROD=${DB_PASSWORD_PROD}
ENV OPEN_API_SERVICE_KEY=${OPEN_API_SERVICE_KEY}
ENV KAKAO_CLIENT_ID=${KAKAO_CLIENT_ID}

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=${PROFILE}", "/app.jar"]
