FROM openjdk:11
EXPOSE 9090
ADD ./target/spring-cashier-1.0.jar /srv/spring-cashier-1.0.jar
CMD java -jar /srv/spring-cashier-1.0.jar


#FROM redis:4.0
#COPY redis.conf /usr/local/etc/redis/redis.conf
#CMD [ "redis-server", "/usr/local/etc/redis/redis.conf" ]
