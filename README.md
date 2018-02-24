CAS Overlay For Wiley NextGEN CAS server
============================

 
# Versions

```xml
<cas.version>5.2.x</cas.version>
```

# Requirements

* JDK 1.8+

#
# Wiley Build Instructions
To build and run this on a local workstation, perform the following steps:
(on windows use build.cmd instead of build.sh)
 
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


docker run -d   -p 8888:8080 --name tomcat tomcat:8.0
