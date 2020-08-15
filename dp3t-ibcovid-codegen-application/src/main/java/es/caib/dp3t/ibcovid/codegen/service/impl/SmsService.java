package es.caib.dp3t.ibcovid.codegen.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.Objects;

@Service
public class SmsService {

    private final RestTemplate restTemplate;

    @Value("${application.radarcovid.sms-url}")
    private String url;
    @Value("${application.radarcovid.sms-text}")
    private String smsText;
    @Value("${application.radarcovid.sms-user}")
    private String user;
    @Value("${application.radarcovid.sms-password}")
    private String password;


    SmsService(final RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public void sendSms(final String number) {
        restTemplate.exchange(url
                        .replace("{number}", number)
                        .replace("{texto}", smsText),
                HttpMethod.GET,
                this.setHeader(),
                Void.class)
                .getBody();
    }

    public HttpEntity setHeader() {
        HttpHeaders headers = new HttpHeaders();
        final String credentials = new StringBuilder(this.user).append(":").append(this.password).toString();
        final String base64Credentials = Base64.getEncoder().encodeToString(Objects.requireNonNull(credentials).getBytes());
        headers.add(HttpHeaders.AUTHORIZATION, "Basic " + base64Credentials);
        return new HttpEntity(headers);
    }
}
