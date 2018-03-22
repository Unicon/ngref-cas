FROM java:8


COPY etc /etc

COPY target/cas.war /cas.war

EXPOSE 8080 8443


CMD ["java", "-jar", "/cas.war"]