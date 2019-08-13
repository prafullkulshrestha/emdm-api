FROM postgres:11.1

MAINTAINER prafull.kulshrestha@gmail.com

RUN chown root /

RUN chmod -R 777 /docker-entrypoint-initdb.d

COPY schema.sql /docker-entrypoint-initdb.d/

COPY data.sql /docker-entrypoint-initdb.d/
