FROM openjdk:11
ENV LANG en_US.UTF-8
ENV LANGUAGE zh_CN:zh
ENV TZ Asia/Shanghai
COPY build/libs/vertx-demo-*.jar /opt/vertx-demo.jar
CMD ["java","-Dfile.encoding=UTF-8","-jar","/opt/vertx-demo.jar"]