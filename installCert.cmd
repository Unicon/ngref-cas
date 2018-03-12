@echo off

@Rem installed the self signed cert in the JVM keystore
@Rem You may need to run this with admin rights
@Rem to delete the cert run
@Rem "%JAVA_HOME%/bin/keytool" -delete -alias "selfcert" -keystore "%JAVA_HOME%/jre/lib/security/cacerts"


"%JAVA_HOME%/bin/keytool" -import -alias "selfcert" -file c:\etc\cas\cas.cer -keystore "%JAVA_HOME%/jre/lib/security/cacerts"

pause

