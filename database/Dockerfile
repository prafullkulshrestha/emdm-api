FROM postgres:11.1

MAINTAINER prafull.kulshrestha@gmail.com

RUN chown root /

RUN chmod -R 777 /docker-entrypoint-initdb.d

COPY init.sql /docker-entrypoint-initdb.d/

