package es.caib.dp3t.ibcovid.codegen.controller.client.auth;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;

public class IBCovidAuthClientBuilder {
    private final Feign.Builder feignBuilder;

    public IBCovidAuthClientBuilder() {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        feignBuilder = Feign.builder()
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder(mapper));
    }

    public IBCovidAuthClient build(final String endpoint) {
        return feignBuilder.target(IBCovidAuthClient.class, endpoint);
    }

}
