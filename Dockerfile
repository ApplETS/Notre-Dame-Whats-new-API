FROM gradle:6.0.1-jdk8
LABEL Name=notre-dame-whats-new-api Version=0.0.1
EXPOSE 8080

COPY . /app
