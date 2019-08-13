FROM alpine:edge

MAINTAINER prafull.kulshrestha@gmail.com

RUN apk add --no-cache openjdk8

EXPOSE 8888

COPY . /tmp

RUN ls -la /tmp

COPY target/*.jar app/emdm-api.jar

COPY ./wait-for-db.sh app/wait-for-db.sh

# Define working directory.
WORKDIR /app


RUN chmod +x wait-for-db.sh 

CMD ./wait-for-db.sh

CMD [ "java","-jar","emdm-api.jar" ]
