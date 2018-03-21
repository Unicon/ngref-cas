package com.wileyng.cas.auth;


import org.apereo.cas.authentication.HandlerResult;
import org.apereo.cas.authentication.UsernamePasswordCredential;
import org.apereo.cas.authentication.handler.support.AbstractUsernamePasswordAuthenticationHandler;
import org.apereo.cas.authentication.principal.PrincipalFactory;
import org.apereo.cas.authentication.principal.SimplePrincipal;
import org.apereo.cas.services.ServicesManager;
import org.apereo.cas.util.CollectionUtils;
import org.apereo.cas.util.EncodingUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import javax.security.auth.login.AccountLockedException;
import javax.security.auth.login.FailedLoginException;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;


/*
Example CAS Custom Authentication class that can be used as a starting point for a Custom authentication class that validates
userid/password vie the NGUser API
Currently the only valid credential is parker/password
 */
public class NgAuthenticationHandler extends AbstractUsernamePasswordAuthenticationHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(NgAuthenticationHandler.class);


    // @Value("${cas.authn.rest.uri}")
    private String restUri;

    private RestTemplate restTemplate;


    public NgAuthenticationHandler(String name, ServicesManager servicesManager, PrincipalFactory principalFactory, Integer order) {
        super(name, servicesManager, principalFactory, order);


    }

    protected HandlerResult authenticateUsernamePasswordInternal(final UsernamePasswordCredential credential,
                                                                 final String originalPassword) throws GeneralSecurityException {
        LOGGER.debug("authenticating user {}", credential.getUsername() + " to URI " + restUri);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("login_name", credential.getUsername());
        map.add("password", credential.getPassword());

        final HttpEntity<SimplePrincipal> entity = new HttpEntity<>(createHeaders(credential));


        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

        ResponseEntity<String> response = null;
        try {
            response = restTemplate.postForEntity(restUri, request, String.class);
            LOGGER.debug("Auth Success: " + response.getBody());
            return createHandlerResult(credential,
                    this.principalFactory.createPrincipal(response.getBody(), null), null);
        } catch (HttpStatusCodeException e) {
            LOGGER.debug("Auth failure.  Return code: " + e.getStatusCode().value() + " reason: " + e.getMessage());

            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                LOGGER.debug("throwing FailedLoginException");
                throw new FailedLoginException("Could not authenticate account for " + credential.getUsername());


            } else if (e.getStatusCode() == HttpStatus.LOCKED) {
                LOGGER.debug("throwing AccountLockedException");
                throw new AccountLockedException("Could not authenticate locked account for " + credential.getUsername());

            } else {
                LOGGER.debug("throwing FailedLoginException for unknown reason");
                throw new FailedLoginException("Rest endpoint returned an unknown status code "
                        + response.getStatusCode() + " for " + credential.getUsername());

            }

        }


//
//        // SimplePrincipal simplePrincipal= restTemplate.postForObject(restUri, entity, SimplePrincipal.class);
//        AuthResult authResult = null;
//        try {
//            authResult = restTemplate.postForObject(restUri, entity, AuthResult.class);
//        } catch (RestClientException e) {
//            LOGGER.warn(e.getMessage());
//
//        }

        //     LOGGER.debug("got" + response.getStatusCode() + " " + response.getBody());

        //  if (response.getStatusCode() == HttpStatus.OK) {
//        return createHandlerResult(credential,
//                this.principalFactory.createPrincipal(response.getBody(), null));
//             //   this.principalFactory.createPrincipal(response.getBody(), null), null);

//        return createHandlerResult(credential,
//                   this.principalFactory.createPrincipal(response.getBody(), null), null);
//
//    } else if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
//        throw new FailedLoginException("Could not authenticate account for " + credential.getUsername());
//
//
//    } else if (response.getStatusCode() == HttpStatus.LOCKED) {
//        throw new AccountLockedException("Could not authenticate locked account for " + credential.getUsername());
//
//    } else {
//        throw new FailedLoginException("Rest endpoint returned an unknown status code "
//                + response.getStatusCode() + " for " + credential.getUsername());
//
//    }

//        // this can be replaced with a call to the NGUser API
//        if ("parker".equalsIgnoreCase(credential.getUsername()) && "password".equalsIgnoreCase(credential.getPassword())) {
//            Map<String, Object> attrs = new HashMap<>();
//            attrs.put("userId", "parker");
//            attrs.put("firstName", "Parker");
//            attrs.put("lastName", "Neff");
//            attrs.put("roles", "student");
//
//            return createHandlerResult(credential,
//                    this.principalFactory.createPrincipal(credential.getUsername(), attrs), null);
//        } else {
//           throw new FailedLoginException("Sorry, you are a failure!");
//
//        }
//
//
    }

    /**
     * Create authorization http headers.
     *
     * @param c the credentials
     * @return the http headers
     */
    public static HttpHeaders createHeaders(final UsernamePasswordCredential c) {
        final HttpHeaders acceptHeaders = new HttpHeaders();
        acceptHeaders.setAccept(CollectionUtils.wrap(MediaType.APPLICATION_JSON));
        final String authorization = c.getUsername() + ':' + c.getPassword();

        final String basic = EncodingUtils.encodeBase64(authorization.getBytes(Charset.forName("US-ASCII")));
        acceptHeaders.set("Authorization", "Basic " + basic);
        return acceptHeaders;
    }

    public void setRestUri(String restUri) {
        this.restUri = restUri;
    }

    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

}
