package es.caib.dp3t.ibcovid.codegen.controller.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.any;
import static springfox.documentation.builders.RequestHandlerSelectors.basePackage;

@Profile("!pro")
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
    private static final String GROUP_NAME = "ibcovid-codegen";
    private static final String BASE_CONTROLLER_PACKAGE = "es.caib.dp3t.ibcovid.codegen";

    @Bean
    public Docket configureApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName(GROUP_NAME)
                .apiInfo(getApiInfo())
                .select()
                .apis(basePackage(BASE_CONTROLLER_PACKAGE))
                .paths(any())
                .build();
    }

    private ApiInfo getApiInfo() {
        return new ApiInfoBuilder()
                .title("IBCovid Codegen API")
                .description("IBCovid Codegen API reference for developers")
                .version("1.0.0")
                .build();
    }

}
