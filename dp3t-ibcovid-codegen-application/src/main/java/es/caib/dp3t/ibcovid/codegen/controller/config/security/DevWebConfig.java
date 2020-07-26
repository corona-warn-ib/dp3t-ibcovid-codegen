package es.caib.dp3t.ibcovid.codegen.controller.config.security;

import es.caib.dp3t.ibcovid.codegen.controller.client.auth.IBCovidAuthClient;
import es.caib.dp3t.ibcovid.codegen.controller.config.RouteConstants;
import es.caib.dp3t.ibcovid.codegen.controller.filter.JWTAuthorizationFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@Configuration
@Profile("dev")
public class DevWebConfig extends BaseWebConfig {

    // TODO For now the client code is included in every repo.
    // Needs to be deleted and add the maven dependency for the client project, when an artifactory is available
    private final IBCovidAuthClient ibCovidAuthClient;

    public DevWebConfig(final IBCovidAuthClient ibCovidAuthClient) {
        this.ibCovidAuthClient = ibCovidAuthClient;
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(antPatternUrlStartsWith(RouteConstants.ADMIN_PATH)).hasAnyRole("DP3T_ACCESS_CODE")
                .antMatchers(antPatternUrlStartsWith(RouteConstants.BASE_PATH),
                        antPatternUrlStartsWith(RouteConstants.ACTUATOR_BASE_PATH),
                        antPatternUrlStartsWith(RouteConstants.H2_CONSOLE_PATH),
                        RouteConstants.LOGIN_PATH)
                .permitAll()
                .anyRequest().denyAll().and()
                .addFilter(new JWTAuthorizationFilter(authenticationManager(), ibCovidAuthClient))
                .cors().and()
                .csrf().disable()
                .headers().frameOptions().disable().and()
                .formLogin().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

}
