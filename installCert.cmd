@echo off

@Rem installed the self signed cert in the JVM keystore
@Rem You man need to run this with admin rights

"%JAVA_HOME%/bin/keytool" -import -alias "selfcert" -file certfile.txt -keystore "%JAVA_HOME%/jre/lib/security/cacerts"

pause

