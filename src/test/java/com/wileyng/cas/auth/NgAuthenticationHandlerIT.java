package com.wileyng.cas.auth;

import org.apereo.cas.authentication.HandlerResult;
import org.apereo.cas.authentication.UsernamePasswordCredential;
import org.junit.Test;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.security.auth.login.AccountLockedException;
import javax.security.auth.login.FailedLoginException;
import java.security.GeneralSecurityException;
import static org.junit.Assert.*;


/*
This is an integration test to test the Custom Authentication Handler

 */
public class NgAuthenticationHandlerIT {


    @Test
    public void testAuthHandlerGoodPassword() {
        NgAuthenticationHandler ah = new NgAuthenticationHandler("parker", null, null, null);
        ah.setRestUri("https://localhost:8444/auth");
        ah.setRestTemplate(restTemplate());

        UsernamePasswordCredential cred = new UsernamePasswordCredential();
        cred.setUsername("user1");
        cred.setPassword("password");

        try {
            HandlerResult result =  ah.authenticateUsernamePasswordInternal(cred, "hello");
            assertEquals("wileyuser1", result.getPrincipal().getId());
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }

    }

    @Test
    public void testAuthHandlerBadPassword() {
        NgAuthenticationHandler ah = new NgAuthenticationHandler("parker", null, null, null);
        ah.setRestUri("https://localhost:8444/auth");
        ah.setRestTemplate(restTemplate());

        UsernamePasswordCredential cred = new UsernamePasswordCredential();
        cred.setUsername("user2");
        cred.setPassword("password1");

        try {
            HandlerResult result =  ah.authenticateUsernamePasswordInternal(cred, "hello");
         fail("Should have failed");
        } catch (GeneralSecurityException e) {
            assertTrue("got " + e.getMessage(), e instanceof  FailedLoginException);

                System.out.println(e.getMessage());

        }

    }
    @Test
    public void testAuthHandlerInvalidAttempts() {
        NgAuthenticationHandler ah = new NgAuthenticationHandler("parker", null, null, null);
        ah.setRestUri("https://localhost:8444/auth");
        ah.setRestTemplate(restTemplate());

        UsernamePasswordCredential cred = new UsernamePasswordCredential();
        cred.setUsername("disabledUser");
        cred.setPassword("password");

        try {
            HandlerResult result =  ah.authenticateUsernamePasswordInternal(cred, "hello");
            fail("Should have failed");
        } catch (GeneralSecurityException e) {
            assertTrue(e instanceof AccountLockedException);


        }

    }

    private RestTemplate restTemplate() {
        return new RestTemplate(getClientHttpRequestFactory());

    }
    private ClientHttpRequestFactory getClientHttpRequestFactory() {
        int timeout = 5000;
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory
                = new HttpComponentsClientHttpRequestFactory();
        clientHttpRequestFactory.setConnectTimeout(timeout);
        return clientHttpRequestFactory;
    }
}
