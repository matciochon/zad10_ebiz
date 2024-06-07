FROM ubuntu:20.04

RUN apt-get update

RUN apt-get install -y openjdk-21-jdk

RUN apt-get install -y unzip

RUN apt-get install -y zip

RUN apt-get install -y curl

RUN curl -s "https://get.sdkman.io" | bash

RUN bash -c "source /root/.sdkman/bin/sdkman-init.sh && sdk install gradle 8.6"

RUN mkdir /root/backend

COPY . /root/backend

WORKDIR /root/backend

RUN ./gradlew build

EXPOSE 8080

CMD [ "./gradlew", "bootRun" ]
