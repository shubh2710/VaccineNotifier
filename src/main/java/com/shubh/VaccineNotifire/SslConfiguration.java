package com.shubh.VaccineNotifire;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

@Configuration
public class SslConfiguration {
    @Value("${proxy}")
    boolean proxyNeeded;
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder)
            throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        RestTemplate restTemplate;
        if(proxyNeeded){
            SimpleClientHttpRequestFactory requestFactory =
                    new SimpleClientHttpRequestFactory();
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("genproxy", 8080));
            requestFactory.setProxy(proxy);
             restTemplate = new RestTemplate(requestFactory);
        }else{
             restTemplate = new RestTemplate();
        }
        return restTemplate;
        //return restTemplate;
    }
}