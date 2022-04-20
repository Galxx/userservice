FROM alpine

RUN apk add openjdk8
ENV JAVA_HOME /usr/lib/jvm/java-1.8-openjdk
ENV PATH $PATH:$JAVA_HOME/bin


WORKDIR /root/testDocker
COPY ./build/libs/userservice-0.0.1-SNAPSHOT.jar /root/testDocker/app.jar

CMD ["java", "-jar", "/root/testDocker/app.jar"]