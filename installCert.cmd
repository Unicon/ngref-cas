@echo off

@Rem installed the self signed cert in the JVM keystore
@Rem set the CERTDIR variable to the directory that containts self-signed.cert
@Rem You may need to run this with admin rights

set CERTDIR=CHANGEME

"%JAVA_HOME%/bin/keytool" -import -alias "selfcert" -file %CERTDIR%/self-signed.cert -keystore "%JAVA_HOME%/jre/lib/security/cacerts"

pause

