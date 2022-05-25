FROM openjdk:17-jdk-alpine
COPY ./target/sysventas_clientes_ms-*.jar sysventas_clientes_ms.jar
EXPOSE 8099
CMD java -jar sysventas_clientes_ms.jar
