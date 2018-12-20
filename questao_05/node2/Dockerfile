FROM maven:3.6.0-jdk-8
ADD . /opt/node2

RUN cd /opt/node2 && mvn compile package -DskipTests=true
RUN chmod +x /opt/node2/start.sh

EXPOSE 11000

CMD touch /opt/shared/diff.txt && /opt/node2/start.sh