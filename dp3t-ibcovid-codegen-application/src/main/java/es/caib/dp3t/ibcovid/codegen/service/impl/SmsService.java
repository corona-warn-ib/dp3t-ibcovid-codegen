package es.caib.dp3t.ibcovid.codegen.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SmsService {

    private final RestTemplate restTemplate;

    @Value("${application.radarcovid.sms-url}")
    private String url;

    @Value("${application.radarcovid.sms-text}")
    private String smsText;

    SmsService(final RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public void sendSms(final String number) {
        restTemplate.getForEntity(url
                        .replace("{numero}", number)
                        .replace("{texto}", smsText),
                Void.class).getBody();
    }
}
