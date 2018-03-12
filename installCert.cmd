@echo off

@Rem installed the self signed cert in the JVM keystore
@Rem You may need to run this with admin rights



"%JAVA_HOME%/bin/keytool" -import -alias "selfcert" -file c:\etc\cas\selfSigned.cer -keystore "%JAVA_HOME%/jre/lib/security/cacerts"

pause

