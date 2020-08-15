package es.caib.dp3t.ibcovid.codegen.common.config;

import es.caib.dp3t.ibcovid.codegen.converter.CodeResultToDownloadedAccessCode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;

@Configuration
public class ConversionServiceConfiguration {

    @Bean(name = "conversionService")
    public ConversionService conversionService() {
        DefaultConversionService defaultConversionService = new DefaultConversionService();
        defaultConversionService.addConverter(new CodeResultToDownloadedAccessCode());

        return defaultConversionService;
    }
}
