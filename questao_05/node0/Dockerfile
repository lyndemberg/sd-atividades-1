FROM maven:3.6.0-jdk-8
ADD . /opt/node0

RUN cd /opt/node0 && mvn compile package -DskipTests=true
RUN chmod +x /opt/node0/start.sh

CMD /opt/node0/start.sh