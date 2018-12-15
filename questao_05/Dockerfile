FROM maven:3.3.9-jdk-8
ADD node0/ /opt/node0
ADD node1/ /opt/node1
ADD node2/ /opt/node2
RUN cd /opt/node0 && mvn compile package -DskipTests=true
RUN cd /opt/node1 && mvn compile package -DskipTests=true
RUN cd /opt/node2 && mvn compile package -DskipTests=true
RUN chmod +x /opt/node0/start.sh
RUN chmod +x /opt/node1/start.sh
RUN chmod +x /opt/node2/start.sh

CMD /opt/app/start.sh