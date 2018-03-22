# Reference Implementation for the Wiley NextGEN CAS Server

The reference implementaton demonstrates the use of CAS to fulfil the following requirements:
* CAS as an OpenID Connect Identity Provider for the MyWileyPlus SPA supporting the OpenID Connect Implicit flow
* CAS as an OAUTH2 Server supporting the Client Credentials flow for App to App API calls
* CAS configured to use a Custom Login Provider calling the NextGen user auth service
* Demonstrates customizing CAS using the CAS overlay process




#
# Wiley Build Instructions
(The following instructions will create a c:\etc\cas directory on your workstation)

To build and run this on a local workstation, perform the following steps:


## Package the CAS Server
    build.cmd package
## Install the self signed cert into your JVM keystore (you may need to run this with Admin privs)
    installCert.cmd
 
## Build and run the CAS Server
    build.cmd run    
 
create /etc/cas dir.  Make sure you have permission to add files

Generate a self signed SSL cert for local testing via the following command
```bash
./build.sh gencert
```
build and package the app
```bash
./build.sh package
```
run CAS as a local service
```bash
./build.sh run
```






It will take a little while to start up. Once is does point your browser to https://localhost:8443/cas and sign on with  parker/password
  
# Custom Authentication Handler
A very basic custom authentication handler has been created to demostrate a custom authentication handler that could be created to to call the NG User API for 
actual authentication

The authentication handler was built using the instructions here: https://apereo.github.io/cas/5.2.x/installation/Configuring-Custom-Authentication.html

The custom authentication handler class created is com.wileyng.cas.auth.NgAuthenticationHandler
The registration configuation class is com.wileyng.cas.auth.NgAuthenticationEventExecutionPlanConfiguration
The configuration for the handler is in src/main/resources/META-INF/spring.factories



#OIDC Provider
## Endpoints
* JWKS https://localhost:8443/cas/oidc/jwks
https://localhost:8443/cas/oidc/.well-known


# Configuration Notes
## Custom Login Provider


openssl s_client -connect localhost:8443 2>&1 | sed -ne '/-BEGIN CERTIFICATE-/,/-END CERTIFICATE-/p' > certfile.txt
sudo $JAVA_HOME/bin/keytool -import -alias "selfcert" -file certfile.txt -keystore $JAVA_HOME/jre/lib/security/cacerts


https://apereo.github.io/2018/01/05/cas-deployment-with-proxy/


#Redis
    docker run -it --link some-redis:redis --rm redis redis-cli -h locahost -p 6379