@echo off

@Rem Deletes the self signed cert from your java keystore
@Rem You may need to run this with admin rights



"%JAVA_HOME%/bin/keytool" -delete -alias "selfcert" -keystore "%JAVA_HOME%/jre/lib/security/cacerts"

pause

