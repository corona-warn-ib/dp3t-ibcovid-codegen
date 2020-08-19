package es.caib.dp3t.ibcovid.codegen.common.config.security;

import es.caib.dp3t.ibcovid.codegen.common.config.security.filter.JWTAuthorizationFilter;
import es.caib.dp3t.ibcovid.codegen.controller.client.auth.IBCovidAuthClient;
import es.caib.dp3t.ibcovid.codegen.controller.config.RouteConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@Configuration
@Profile("pre | pro")
public class ProWebConfig extends BaseWebConfig {
    @Value("${keycloak.tokenSign}")
    private String tokenSign;

    public ProWebConfig() {
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.antMatcher("/v1/admin/**").authorizeRequests()
                .anyRequest().permitAll()
                .and()
                //.addFilterBefore(new JWTAuthorizationFilter(this.tokenSign), UsernamePasswordAuthenticationFilter.class)
                .cors().and().csrf().disable();
    }

}
