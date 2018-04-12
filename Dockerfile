# Trellis Cassandra Container Image
#
# Version: 0.1

FROM openjdk:8-jdk
MAINTAINER Greg Jansen <jansen@umd.edu>

ENV JAVA_OPTS="-Xms250m -Xmx1024m" \
TRELLIS_CASSANDRA_NATIVE_TRANSPORT_PORT=9042 \
TRELLIS_CASSANDRA_ADDRESS="cassandra-1"

USER root
RUN mkdir /opt/trellis
COPY target/trellis-cassandra-app-0.0.1-SNAPSHOT.jar /opt/trellis/trellis.jar
COPY src/main/resources/trellis-config.yml /opt/trellis/config.yml

CMD ["java", "--illegal-access=warn", "-jar", "/opt/trellis/trellis.jar", "server", "/opt/trellis/config.yml"]
