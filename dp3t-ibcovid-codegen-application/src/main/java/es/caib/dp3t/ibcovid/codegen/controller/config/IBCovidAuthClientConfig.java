package es.caib.dp3t.ibcovid.codegen.controller.config;

import es.caib.dp3t.ibcovid.codegen.controller.client.auth.IBCovidAuthClient;
import es.caib.dp3t.ibcovid.codegen.controller.client.auth.IBCovidAuthClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IBCovidAuthClientConfig {

    @Bean
    public IBCovidAuthClient getAuthClient(@Value("${dp3t.ibcovid.codegen.clients.auth}") final String endpoint) {
        return new IBCovidAuthClientBuilder().build(endpoint);
    }

}
