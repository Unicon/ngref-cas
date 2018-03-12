package com.wileyng.cas.auth;


import org.apereo.cas.authentication.HandlerResult;
import org.apereo.cas.authentication.UsernamePasswordCredential;
import org.apereo.cas.authentication.handler.support.AbstractUsernamePasswordAuthenticationHandler;
import org.apereo.cas.authentication.principal.PrincipalFactory;
import org.apereo.cas.services.ServicesManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.login.FailedLoginException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;

/*
Example CAS Custom Authentication class that can be used as a starting point for a Custom authentication class that validates
userid/password vie the NGUser API
Currently the only valid credential is parker/password
 */
public class NgAuthenticationHandler extends AbstractUsernamePasswordAuthenticationHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(NgAuthenticationHandler.class);


    public NgAuthenticationHandler(String name, ServicesManager servicesManager, PrincipalFactory principalFactory, Integer order) {
        super(name, servicesManager, principalFactory, order);
    }

    protected HandlerResult authenticateUsernamePasswordInternal(final UsernamePasswordCredential credential,
                                                                 final String originalPassword) throws GeneralSecurityException {
        LOGGER.info("authenticating user {}", credential.getUsername());
    // this can be replaced with a call to the NGUser API
        if ("parker".equalsIgnoreCase(credential.getUsername()) && "password".equalsIgnoreCase(credential.getPassword())) {
            Map<String, Object> attrs = new HashMap<>();
            attrs.put("userId", "parker");
            attrs.put("firstName", "Parker");
            attrs.put("lastName", "Neff");
            attrs.put("roles", "student");

            return createHandlerResult(credential,
                    this.principalFactory.createPrincipal(credential.getUsername(), attrs), null);
        } else {
           throw new FailedLoginException("Sorry, you are a failure!");

        }


    }

}
