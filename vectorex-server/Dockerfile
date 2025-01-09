# 使用官方 OpenJDK 17 镜像作为基础镜像
FROM openjdk:8-jre-slim

# 设置工作目录
WORKDIR /app

# 复制 JAR 文件和配置文件到容器中
COPY target/vectorex-server-0.0.1-SNAPSHOT.jar app.jar
COPY config/application.yml application.yml

# 暴露默认端口（根据 application.yml 中的配置）
EXPOSE 8382

# 设置启动命令，指定配置文件路径
ENTRYPOINT ["java", "-jar", "app.jar", "--spring.config.location=file:/app/application.yml"]