FROM maven:3.6.0-jdk-8
ADD . /opt/node1

RUN cd /opt/node1 && mvn compile package -DskipTests=true
RUN chmod +x /opt/node1/start.sh

EXPOSE 10000

CMD touch /opt/shared/sum.txt && /opt/node1/start.sh