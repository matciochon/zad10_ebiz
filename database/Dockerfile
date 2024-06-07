FROM mysql:latest

ENV MYSQL_ALLOW_EMPTY_PASSWORD=true

COPY 01_schema.sql /docker-entrypoint-initdb.d/
COPY 02_data.sql /docker-entrypoint-initdb.d/

EXPOSE 3306
