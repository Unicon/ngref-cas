package com.wileyng.cas.auth;

import org.apereo.cas.authentication.AuthenticationEventExecutionPlan;
import org.apereo.cas.authentication.AuthenticationEventExecutionPlanConfigurer;
import org.apereo.cas.authentication.AuthenticationHandler;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration("NgAuthenticationEventExecutionPlanConfiguration")
@EnableConfigurationProperties(CasConfigurationProperties.class)
public class NgAuthenticationEventExecutionPlanConfiguration
        implements AuthenticationEventExecutionPlanConfigurer {
    @Autowired
    private CasConfigurationProperties casProperties;


    @Value("${cas.authn.rest.uri}")
    private String restUri;

    @Value("${cas.authn.rest.timeout:5000}")
    private int restTimeout = 5000;

    @Bean
    public AuthenticationHandler ngAuthenticationHandler() {
        final NgAuthenticationHandler handler = new NgAuthenticationHandler("NgAuthentication", null, null, 1);
        /*
            Configure the handler by invoking various setter methods.
            Note that you also have full access to the collection of resolved CAS settings.
            Note that each authentication handler may optionally qualify for an 'order`
            as well as a unique name.
        */
        handler.setRestUri(restUri);
        handler.setRestTemplate(restTemplate());
        return handler;
    }

    @Override
    public void configureAuthenticationExecutionPlan(final AuthenticationEventExecutionPlan plan) {
       // if (feelingGoodOnAMondayMorning()) {
            plan.registerAuthenticationHandler(ngAuthenticationHandler());
     //   }
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

