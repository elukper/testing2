package com.testing.two.application.service;

import com.testing.two.application.dto.JacksonDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Service that acts as a REST client, using multiple RestTemplates (see RootConfig)
 */
@Service
public class BaseRestClientService {

    @Autowired
    @Qualifier("basicRestTemplate")
    RestTemplate restTemplate;

    @Autowired
    @Qualifier("anotherBasicRestTemplate")
    RestTemplate anotherRestTemplate;

    public void sendGet() {

        restTemplate.getForEntity("someURL", JacksonDto.class);
    }

    public void sendGet(String username, String password) {
        for(ClientHttpRequestInterceptor interceptor : restTemplate.getInterceptors()) {
            if(interceptor instanceof  BasicAuthorizationInterceptor) {
                restTemplate.getInterceptors().remove(interceptor);
                break;
            }
        }

        restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(username,password));
    }

    public void sendAnotherGet() {
        restTemplate.getForEntity("anotherUrl", JacksonDto.class);
    }
}
